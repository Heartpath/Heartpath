// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0" apply false // https://github.com/jlleitschuh/ktlint-gradle
}