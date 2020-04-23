plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    jcenter()
}

//TODO move version to different files
val ktorVersion = "1.3.2"
val logbackVersion = "1.2.3"
dependencies {
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