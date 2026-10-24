plugins {
    kotlin("jvm") version "2.0.21"
    application
}

dependencies {
    implementation("dev.apartejs:privacy:1.0.0")
}

application {
    mainClass.set("MainKt")
}
