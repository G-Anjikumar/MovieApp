// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.hilt) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.service) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    ("com.android.tools.build:gradle:8.1.0")
}