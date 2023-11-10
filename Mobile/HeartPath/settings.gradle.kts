pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven("https://naver.jfrog.io/artifactory/maven/")
        maven("https://jitpack.io" )
    }
}

rootProject.name = "HeartPath"
include(":app")
include(":presentation")
include(":data")
include(":domain")
