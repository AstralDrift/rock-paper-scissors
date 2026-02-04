plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}
