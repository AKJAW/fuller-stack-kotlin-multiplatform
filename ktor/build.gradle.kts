plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

application {
    mainClassName = "MainKt"
}

// TODO move version to different files
dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(KtorLibs.SERVER_CORE)
    implementation(KtorLibs.SERVER_NETTY)
    implementation(KtorLibs.LOG_BACK_CLASSIC)

    // dependency injection
    implementation(SharedLibs.KODEIN_DI)
    implementation(KtorLibs.KODEIN_DI_FRAMEWORK_KTOR_SERVER)

    // serialization
    implementation(SharedLibs.SERIALIZATION_RUNTIME_COMMON)
    implementation(KtorLibs.KTOR_SERIALIZATION)

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks {
    val ktorRun by registering(JavaExec::class) {
        group = "custom"
        main = "server.MainKt"
        classpath = sourceSets["main"].runtimeClasspath

        println("running on http://localhost:9000/")
    }
}
