import java.sql.DriverManager

plugins {
	java
	id("org.springframework.boot") version "3.5.12"
	id("io.spring.dependency-management") version "1.1.7"

    id("org.liquibase.gradle") version "3.1.0"
    id("checkstyle")
}

group = "com.hustle"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Digital-Hustle/exception-starter")
        credentials {
            username = project.findProperty("gpr.user")?.toString() ?: System.getenv("USERNAME_GITHUB")
            password = project.findProperty("gpr.key")?.toString() ?: System.getenv("TOKEN_GITHUB")
        }
    }
}

extra["springCloudVersion"] = "2025.0.0"

fun loadEnv(envFile: File): Map<String, String> {
    val env = mutableMapOf<String, String>()
    if (envFile.exists()) {
        envFile.forEachLine { line ->
            if (line.isNotBlank() && line.contains("=") && !line.trim().startsWith("#")) {
                val parts = line.split("=", limit = 2)
                if (parts.size == 2) {
                    env[parts[0].trim()] = parts[1].trim()
                }
            }
        }
    }
    return env
}

fun getEnv(key: String, defaultValue: String = ""): String {
    return envVars[key] ?: System.getenv(key) ?: defaultValue
}

val envFile = file(".env")
val envVars = loadEnv(envFile)

// versions
val postgresVersion = "42.6.0"
val minioVersion = "8.5.7"
val jakartaPersistenceVersion = "3.2.0"
val digitalHustleExceptionVersion = "0.0.1-SNAPSHOT"
val jooqVersion = "3.19.13"
val liquibaseVersion = "4.33.0"
val mapstructVersion = "1.6.3"
val apacheCommonsIoVersion = "2.21.0"
val lombokMapstructBindingVersion = "0.2.0"

//
val springProfile = getEnv("SPRING_PROFILES_ACTIVE", "")

// db connection
val dbHost = getEnv("DB_HOST", "localhost")
val dbPort = getEnv("DB_PORT", "5432")
val dbUser = getEnv("DB_USERNAME", "postgres")
val dbPassword = getEnv("DB_PASSWORD", "root")
val dbName = getEnv("DB_NAME", "rag_workspace_db")
val dbSchema = getEnv("DB_SCHEMA", "rag")
val dbDriver = getEnv("DB_DRIVER", "org.postgresql.Driver")

val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

// paths
val checkstylePath = "../../checkstyle"
val changelogMasterPath = "src/main/resources/db/changelog"

// fileNames
val changelogFileName = when (springProfile) {
    "dev" -> "db.changelog-master.dev.yml"
    else -> "db.changelog-master.yml" // неизвестный профиль
}

dependencies {
    // starters
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

    // cloud starters
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.0-M6")

    // db
	runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    // minio
    implementation("io.minio:minio:${minioVersion}")

    // liquibase
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.liquibase:liquibase-commercial:$liquibaseVersion")
    liquibaseRuntime("info.picocli:picocli:4.7.5")

    // utils
    implementation("org.apache.pdfbox:pdfbox:2.0.30")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.apache.poi:poi-ooxml:5.4.1")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1") // помогает с JSON, но для vector нужен кастомный тип
    implementation("org.hibernate.orm:hibernate-community-dialects:6.4.4.Final")

    // mapper
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // own starters
    implementation("ru.digital-hustle:exceptions-starter:$digitalHustleExceptionVersion")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.6.0") // для создания схемы перед liquibase
        classpath("org.liquibase:liquibase-core:4.33.0") // для liquibase плагина
    }
}


// plugins config
liquibase {
    activities {
        create("main") {
            this.arguments = mapOf(
                "changelogFile" to "$changelogMasterPath/$changelogFileName",
                "url" to dbUrl,
                "username" to dbUser,
                "password" to dbPassword,
                "driver" to dbDriver,
                "defaultSchemaName" to dbSchema,
                "liquibaseSchemaName" to dbSchema
            )
        }
    }
    runList = "main"
}

checkstyle {
    toolVersion = "13.0.0"
    configFile = file("$checkstylePath/checkstyle.xml")
    configDirectory = file(checkstylePath)

    configProperties = mapOf(
        "checkstyle.dir" to file(checkstylePath).absolutePath,
        "checkstyle.cache.file" to layout.buildDirectory.file("checkstyle/cache.properties").get().asFile.absolutePath
    )

    isIgnoreFailures = false
}

// tasks config
tasks.register("ensureSchemaExists") {
    group = "database"

    doLast {
        val jdbcUrl = dbUrl

        DriverManager.getConnection(
            jdbcUrl,
            dbUser,
            dbPassword
        ).use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("CREATE SCHEMA IF NOT EXISTS $dbSchema")
            }
        }
    }
}

tasks.register("createCheckstyleCache") {
    val cacheFileProvider = layout.buildDirectory.file("checkstyle/cache.properties")

    doLast {
        val cacheFile = cacheFileProvider.get().asFile
        cacheFile.parentFile.mkdirs()
        if (!cacheFile.exists()) {
            cacheFile.writeText("# Checkstyle cache\n")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    dependsOn("createCheckstyleCache")
}

tasks.named<Checkstyle>("checkstyleMain") {
    source = sourceSets.main.get().allJava
    classpath = configurations.compileClasspath.get()
}

tasks.named<Checkstyle>("checkstyleTest") {
    enabled = false
}

tasks.named("compileJava") {
    dependsOn("update")
}

tasks.named("check") {
    dependsOn("checkstyleMain")
}

tasks.named("build") {
    dependsOn("check", "compileJava")
}

tasks.named("update") {
    dependsOn("ensureSchemaExists")
}

tasks.named("compileJava") {
    mustRunAfter("checkstyleMain")
}
