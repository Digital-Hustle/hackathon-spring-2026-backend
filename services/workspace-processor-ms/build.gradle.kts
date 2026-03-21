plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"

//	id("org.liquibase.gradle") version "3.1.0"
}

group = "ru.digital-hustle.obCIdian"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"

// versions
val postgresVersion = "42.6.0"
val minioVersion = "8.5.7"
val jakartaPersistenceVersion = "3.2.0"
val jooqVersion = "3.19.13"
val liquibaseVersion = "4.33.0"
val mapstructVersion = "1.6.3"
val apacheCommonsIoVersion = "2.21.0"
val lombokMapstructBindingVersion = "0.2.0"

dependencies {
	// boot starters
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// cloud starters
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// spring
	implementation("org.springframework.data:spring-data-commons")

	// persist
	implementation("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceVersion")

	// minio
	implementation("io.minio:minio:$minioVersion")

	// db
	runtimeOnly("org.postgresql:postgresql:$postgresVersion")

	// liquibase
//	liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
//	liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
//	liquibaseRuntime("org.liquibase:liquibase-commercial:$liquibaseVersion")
//	liquibaseRuntime("info.picocli:picocli:4.7.5")

	// mapper
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

	// utils
	implementation("commons-io:commons-io:$apacheCommonsIoVersion")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

	// lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

	// tests
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}
