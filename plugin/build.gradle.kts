plugins {
  id("shared")
  id("java-gradle-plugin")
  `maven-publish`
  alias(libs.plugins.buildconfig)
  alias(libs.plugins.dependencyGuard)
}

val versionName = properties["VERSION_NAME"]?.toString() ?: error("No version found")

gradlePlugin {
  plugins {
    create("mavenPublishPlugin") {
      id = "dev.jonpoulton.maven.publish"
      implementationClass = "com.vanniktech.maven.publish.MavenPublishPlugin"
      displayName = "Gradle Maven Publish Plugin"
      description = "Gradle plugin that configures publish tasks to automatically upload all of your Java, Kotlin, " +
        "Gradle, or Android libraries to any Maven instance."
      version = versionName
    }
    create("mavenPublishBasePlugin") {
      id = "dev.jonpoulton.maven.publish.base"
      implementationClass = "com.vanniktech.maven.publish.MavenPublishBasePlugin"
      displayName = "Gradle Maven Publish Base Plugin"
      description = "Gradle plugin that configures publish tasks to automatically upload all of your Java, Kotlin, " +
        "Gradle, or Android libraries to any Maven instance."
      version = versionName
    }
  }
}

buildConfig {
  packageName("com.vanniktech.maven.publish")
  buildConfigField("String", "NAME", "\"com.vanniktech.maven.publish\"")
  buildConfigField("String", "VERSION", "\"${project.findProperty("VERSION_NAME") ?: "dev"}\"")
}

dependencies {
  api(gradleApi())
  api(libs.kotlin.stdlib)

  compileOnly(libs.dokka)
  compileOnly(libs.kotlin.plugin)
  compileOnly(libs.android.plugin)

  implementation(projects.centralPortal)
  implementation(projects.nexus)

  testImplementation(gradleTestKit())
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.testParameterInjector)
  testImplementation(libs.truth)
  testImplementation(libs.truth.java8)
  testImplementation(libs.truth.testKit)
  testImplementation(libs.maven.model)
}
