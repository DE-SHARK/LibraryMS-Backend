dependencies {
    implementation(project(":interfaces"))
    implementation(project(":infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}

springBoot {
    mainClass.set("me.deshark.lms.LmsApplication")
}