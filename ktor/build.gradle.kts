plugins {
    application
    kotlin("jvm")
}

repositories {
    mavenCentral()
    jcenter()
}

application {
    mainClassName = "MainKt"
}

//TODO move version to different files
val ktorVersion = "1.3.2"
val logbackVersion = "1.2.3"
dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
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