plugins {
    id("java")
}

group = "com.otus.java.advanced"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("dev.langchain4j:langchain4j-open-ai:1.1.0")
    implementation ("dev.langchain4j:langchain4j:1.1.0")
    implementation("org.jsoup:jsoup:1.21.1")
    implementation("org.mapdb:mapdb:3.1.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}