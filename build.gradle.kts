plugins {
  alias(libs.plugins.kotlin).apply(false)
  alias(libs.plugins.ktlint).apply(false)
  alias(libs.plugins.dependencyGuard)
  `maven-publish`
}

dependencyGuard {
  configuration("classpath")
}
