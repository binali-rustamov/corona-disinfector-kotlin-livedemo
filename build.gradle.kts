plugins {
    application
    kotlin("jvm") version "1.3.72"
}

group = "me.rustamov"
version = "1.0-SNAPSHOT"
application.mainClassName = "me.rustamov.Main"
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("cglib", "cglib", "3.3.0")
    implementation("org.reflections", "reflections", "0.9.12")
    implementation("javax.annotation", "jsr250-api", "1.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    jar {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })

    }
}
sourceSets {
    main {
        resources {
            srcDirs("src/main/resources", "src/main/kotlin")
        }
    }
}