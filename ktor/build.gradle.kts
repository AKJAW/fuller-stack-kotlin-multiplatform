plugins {
    application
    kotlin("jvm")
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
    implementation(KtorLibs.KODEIN_DI_ERASED_JVM)
    implementation(KtorLibs.KODEIN_DI_FRAMEWORK_KTOR_SERVER)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

task("devServer", JavaExec::class) {
    group = "custom"
    main = "server.MainKt"
    classpath = sourceSets["main"].runtimeClasspath
}
