plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)

    `maven-publish`
    signing
    idea
}

// recommended to define this in ~/.gradle/gradle.properties or pass through -P
val ossrhUsername: String? by project
val ossrhPassword: String? by project

group = "com.iamincendium.common"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

detekt {
    ignoreFailures = false

    config.from("${rootDir}/.detekt/config.yml")
    buildUponDefaultConfig = true

    baseline = file("${rootDir}/.detekt/baseline.xml")
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

dependencies {
    implementation(kotlin("reflect"))

    testImplementation(libs.bundles.commonTest)
}

tasks {
    test {
        useJUnitPlatform()
    }

    val javadocJar by registering(Jar::class) {
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml.flatMap { it.outputDirectory })
    }
}

kotlin {
    jvmToolchain(17)
    explicitApi()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications.create<MavenPublication>("enumUtils") {
        from(components["java"])

        pom {
            name.set("enum-utils")
            description.set("A utility for working with Enums and Enum-like Sealed Interface/Classes in Kotlin.")
            url.set("https://github.com/incendium/enum-utils")

            licenses {
                license {
                    name.set("ISC License")
                    url.set("https://spdx.org/licenses/ISC.html")
                }
            }

            developers {
                developer {
                    id.set("incendium")
                    name.set("Matthew Gast")
                    email.set("incendium@gmail.com")
                }
            }

            scm {
                url.set("https://github.com/incendium/enum-utils")
                connection.set("scm:git:https://github.com/incendium/enum-utils.git")
                developerConnection.set("scm:git:https://github.com/incendium/enum-utils.git")
            }
        }
    }

    repositories {
        maven {
            name = "ossrhSnapshots"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")

            credentials {
                username = ossrhUsername ?: System.getenv("OSSRH_USERNAME")
                password = ossrhPassword ?: System.getenv("OSSRH_PASSWORD")
            }
        }

        maven {
            name = "ossrhStaging"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = ossrhUsername ?: System.getenv("OSSRH_USERNAME")
                password = ossrhPassword ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    if (System.getenv("SIGNING_KEY_PATH") != null) {
        useInMemoryPgpKeys(System.getenv("SIGNING_KEY_PATH"), System.getenv("SIGNING_KEY_PASSWORD"))
    } else {
        useGpgCmd()
    }
    publishing.publications.forEach { sign(it) }
}
