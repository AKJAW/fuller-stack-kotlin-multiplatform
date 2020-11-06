import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

application {
    mainClassName = "server.MainKt"
}

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(KtorLibs.SERVER_CORE)
    implementation(KtorLibs.SERVER_NETTY)
    implementation(KtorLibs.JWT)
    implementation(KtorLibs.LOG_BACK_CLASSIC)

    // dependency injection
    implementation(SharedLibs.KODEIN_DI)
    implementation(KtorLibs.KODEIN_DI_FRAMEWORK_KTOR_SERVER)

    // date
    implementation(SharedLibs.KLOCK)

    // serialization
    implementation(SharedLibs.COROUTINES_CORE)
    implementation(KtorLibs.KTOR_SERIALIZATION)

    // database
    implementation(KtorLibs.EXPOSED_CORE)
    implementation(KtorLibs.EXPOSED_DAO)
    implementation(KtorLibs.EXPOSED_JDBC)
    implementation(KtorLibs.HIKARI_CP)
    if (System.getenv("JDBC_DATABASE_URL") == null) {
        implementation(KtorLibs.H2)
    } else {
        implementation(KtorLibs.POSTGRE_SQL)
    }
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

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("fuller-stack-ktor")
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to "server.MainKt",
                    "Class-Path" to sourceSets["main"].runtimeClasspath
                )
            )
        }
    }
}
