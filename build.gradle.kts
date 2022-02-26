buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.5.0")
        classpath("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    }
}

//Define Plugins
plugins {
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "1.5.0"
}

//Define Variables for all Projects
allprojects {
    //Define Repositorys
    repositories {
        mavenCentral()
        for (field in Repositories::class.java.declaredFields) {
            if (field.name != "INSTANCE") {
                println("Added Repository: " + field.get(null))
                maven(field.get(null))
            }
        }
    }

    //Define Version and Group
    this.group = Properties.group
    this.version = Properties.version

}

//Default configuration for each module
subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    //Define Dependencies for all Modules
    dependencies {
        compileOnly(dependency("kotlin", "stdlib")) // Already Provided by SimpleCloud
        implementation(dependency("kotlin", "serialization"))
        implementation(dependency("kotlinx", "coroutines-core"))
        implementation(dependency("google", "gson"))
        compileOnly(dependency("simplecloud", "api"))

        if (project.name != "pxl-api") {
            implementation(project(":pxl-api"))
        }


        testImplementation("org.jetbrains.kotlin:kotlin-test")
    }

    java {
        withSourcesJar()
    }

    apply(plugin = "maven-publish")
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "NSGRepository"
                url = uri("https://repo.neverstopgaming.net/snapshots")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("publishName")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("publishPassword")
                }
            }
            publications {
                register<MavenPublication>("gpr") {
                    from(components["java"])
                }
            }
        }
    }

    tasks {


        test {
            useJUnitPlatform()
        }

        //Set the Name of the Sources Jar
        kotlinSourcesJar {
            archiveFileName.set("${project.name}-${Properties.version}-${getCommitHash()}-sources.jar")
            doFirst {
                //Set Manifest
                manifest {
                    attributes["Implementation-Title"] = project.name
                    attributes["Implementation-Version"] = Properties.version
                    attributes["Specification-Version"] = Properties.version
                    attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                    attributes["Built-By"] = System.getProperty("user.name")
                    attributes["Build-Jdk"] = System.getProperty("java.version")
                    attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                    attributes["Commit-Hash"] = getCommitHash()
                    attributes["Launcher-Agent-Class"] = "eu.vironlab.vextension.dependency.DependencyAgent"
                }
            }
        }
        shadowJar {
            //configurations = mutableListOf(project.configurations["shaded"])
            //Set the Name of the Output File
            archiveFileName.set("${project.name}-${Properties.version}-${getCommitHash()}-full.jar")
            //if (project.name == "nsg-server") // Fixed SimpleCloud-Plugin already importing Kotlin, causing a LinkageError
            //    exclude("kotlin/**")
            exclude("META-INF/**")
            exclude { file ->
                file.path.contains("kotlin/") && (!file.path.contains("kotlin/reflect") || project.name == "nsg-proxy" || project.name == "nsg-server")
            }
            doFirst {
                //Set Manifest
                manifest {
                    attributes["Implementation-Title"] = project.name
                    attributes["Implementation-Version"] = Properties.version
                    attributes["Specification-Version"] = Properties.version
                    attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                    attributes["Built-By"] = System.getProperty("user.name")
                    attributes["Build-Jdk"] = System.getProperty("java.version")
                    attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                    attributes["Commit-Hash"] = getCommitHash()
                    attributes["Launcher-Agent-Class"] = "eu.vironlab.vextension.dependency.DependencyAgent"
                }
            }
        }

        jar {
            archiveFileName.set("${project.name}-${Properties.version}-${getCommitHash()}.jar")
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            /*from(
                configurations.runtimeClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } }
            )*/
            doFirst {
                //Set Manifest
                manifest {
                    attributes["Implementation-Title"] = project.name
                    attributes["Implementation-Version"] = Properties.version
                    attributes["Specification-Version"] = Properties.version
                    attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                    attributes["Built-By"] = System.getProperty("user.name")
                    attributes["Build-Jdk"] = System.getProperty("java.version")
                    attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                    attributes["Commit-Hash"] = getCommitHash()
                    attributes["Launcher-Agent-Class"] = "eu.vironlab.vextension.dependency.DependencyAgent"
                }
            }
        }


        compileKotlin {
            kotlinOptions.jvmTarget = "16"
        }

        withType<JavaCompile> {
            this.options.encoding = "UTF-8"
        }
    }
}