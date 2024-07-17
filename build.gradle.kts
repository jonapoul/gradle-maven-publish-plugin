plugins {
  alias(libs.plugins.kotlin).apply(false)
  alias(libs.plugins.mavenPublish).apply(false)
  alias(libs.plugins.ktlint).apply(false)
  alias(libs.plugins.dependencyGuard)
}

dependencyGuard {
  configuration("classpath")
}
