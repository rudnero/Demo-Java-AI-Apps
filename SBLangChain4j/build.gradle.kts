plugins {
    id("java")
}

group = "com.otus.java.advanced"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("dev.langchain4j:langchain4j-open-ai-spring-boot-starter:1.1.0-beta7")
    implementation("dev.langchain4j:langchain4j-spring-boot-starter:1.1.0-beta7")
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.3")
    implementation("org.jsoup:jsoup:1.21.1")
}

tasks.test {
    useJUnitPlatform()
}