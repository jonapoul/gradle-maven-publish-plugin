import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libs = the<LibrariesForLibs>()

plugins {
  id("java-library")
  id("kotlin")
  id("kotlin-kapt")
  `maven-publish`
  id("org.jlleitschuh.gradle.ktlint")
  id("com.dropbox.dependency-guard")
}

repositories {
  mavenCentral()
  google()
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType(KotlinCompile::class.java) {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_11)
  }
}

configurations.all {
  // Pin the kotlin version
  resolutionStrategy {
    force(libs.kotlin.stdlib)
    force(libs.kotlin.stdlib.jdk8)
    force(libs.kotlin.reflect)
  }
}

dependencyGuard {
  configuration("runtimeClasspath")
  configuration("compileClasspath")
}

fun string(key: String): String = properties[key]?.toString() ?: error("No key $key")

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])

      groupId = string(key = "GROUP")
      artifactId = string(key = "POM_ARTIFACT_ID")
      version = string(key = "VERSION_NAME")

      pom {
        name = string(key = "POM_NAME")
        description = string(key = "POM_DESCRIPTION")
        url = string(key = "POM_URL")

        licenses {
          license {
            name = string(key = "POM_LICENCE_NAME")
            url = string(key = "POM_LICENCE_URL")
            distribution = string(key = "POM_LICENCE_DIST")
          }
        }

        developers {
          developer {
            id = string(key = "POM_DEVELOPER_ID")
            name = string(key = "POM_DEVELOPER_NAME")
            email = string(key = "POM_DEVELOPER_URL")
          }
        }

        scm {
          connection = string(key = "POM_SCM_CONNECTION")
          developerConnection = string(key = "POM_SCM_DEV_CONNECTION")
          url = string(key = "POM_SCM_URL")
        }
      }
    }
  }

  repositories {
    mavenCentral()
    mavenLocal()
  }
}
