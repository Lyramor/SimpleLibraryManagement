// settings.gradle.kts

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // You might add other repositories like jcenter() if needed by specific dependencies,
        // but it's generally being deprecated.
    }
}

rootProject.name = "SimpleLibrarymanagement"
include(":app")

// The 'buildscript {}' block that was here, specifically its Hilt dependency,
// was causing the conflict and was misplaced.
// If it had other essential classpath dependencies for the settings script itself (unlikely for Hilt),
// those would be evaluated differently. But for project plugins, this is not the place.