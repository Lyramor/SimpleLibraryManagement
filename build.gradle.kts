// root build.gradle.kts

// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Applying plugins here as 'false' means they are available for modules to apply.
plugins {
    // Apply the Android Application plugin at the project level (false means it's not applied to the root project itself)
    alias(libs.plugins.android.application) apply false
    // Apply the Kotlin Android plugin
    alias(libs.plugins.kotlin.android) apply false
    // Apply the Kotlin Compose plugin
    alias(libs.plugins.kotlin.compose) apply false
    // Apply the Hilt Android plugin - This is the correct way to make it available
    alias(libs.plugins.hilt.android) apply false
    // Apply the KSP (Kotlin Symbol Processing) plugin
    alias(libs.plugins.ksp) apply false
    // Apply the Kotlin Serialization plugin
    alias(libs.plugins.kotlin.serialization) apply false
}

// If you had other configurations in your root build.gradle.kts (e.g., `allprojects` or `subprojects` blocks,
// or actual plugins applied to the root project with `apply true`), they would remain here.
// The key is that the Hilt plugin classpath from a `buildscript` block is removed.