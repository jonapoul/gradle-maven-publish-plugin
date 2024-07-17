plugins {
  id("shared")
  id("java-gradle-plugin")
  alias(libs.plugins.buildconfig)
  alias(libs.plugins.dependencyGuard)
}

gradlePlugin {
  plugins {
    create("mavenPublishPlugin") {
      id = "com.vanniktech.maven.publish"
      implementationClass = "com.vanniktech.maven.publish.MavenPublishPlugin"
      displayName = "Gradle Maven Publish Plugin"
      description = "Gradle plugin that configures publish tasks to automatically upload all of your Java, Kotlin, " +
        "Gradle, or Android libraries to any Maven instance."
    }
    create("mavenPublishBasePlugin") {
      id = "com.vanniktech.maven.publish.base"
      implementationClass = "com.vanniktech.maven.publish.MavenPublishBasePlugin"
      displayName = "Gradle Maven Publish Base Plugin"
      description = "Gradle plugin that configures publish tasks to automatically upload all of your Java, Kotlin, " +
        "Gradle, or Android libraries to any Maven instance."
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
