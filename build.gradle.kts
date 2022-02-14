import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.compose") version "1.0.1"
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "fuurineditor"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)

    implementation("org.springframework:spring-context:5.3.13")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    //Test
    testImplementation("org.springframework:spring-test:5.3.13")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "fuurineditor.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "fuurin-editor-s"
            packageVersion = "1.0.0"
        }
    }
}

javafx {
    version = "11.0.2"
    modules = ArrayList(listOf("javafx.web", "javafx.swing"))
}