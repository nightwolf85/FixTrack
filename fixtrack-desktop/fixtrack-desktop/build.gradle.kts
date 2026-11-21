plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    kotlin("jvm")
}

group = "com.fixtrack.desktop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
    implementation("com.github.librepdf:openpdf:2.0.3")
}

application {
    mainClass.set("com.fixtrack.desktop.Launcher")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Main-Class" to "com.fixtrack.desktop.Launcher"
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}