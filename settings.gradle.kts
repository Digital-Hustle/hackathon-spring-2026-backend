rootProject.name = "tourist-helper"

file("services").listFiles()?.forEach { serviceDir ->
    if (serviceDir.isDirectory && file("services/${serviceDir.name}/build.gradle.kts").exists()) {
        include(":services:${serviceDir.name}")
        project(":services:${serviceDir.name}").projectDir = file("services/${serviceDir.name}")
    }
}