plugins {
    id("java")
    id("io.quarkus") version "3.24.2"
}

group = "com.otus.java.advanced"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(platform("io.quarkus.platform:quarkus-bom:3.24.2"))

    implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-openai:1.1.0.CR2")
    implementation("io.quarkus:quarkus-rest:3.24.2")
    implementation("io.quarkus:quarkus-rest-jackson:3.24.2")

    implementation("io.quarkus:quarkus-smallrye-fault-tolerance:3.24.2")

    implementation("org.jsoup:jsoup:1.21.1")
}

tasks.test {
    useJUnitPlatform()
}