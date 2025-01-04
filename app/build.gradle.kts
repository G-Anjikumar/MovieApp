plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "1.3.71"
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.llyod.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.llyod.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "dagger.hilt.android.testing.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":Features"))
    implementation(project(":RemoteSDK"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // system UI Controller
    implementation(libs.accompanist.systemuicontroller)
    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.android.compiler)

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    // For instrumentation tests
    androidTestImplementation ("org.mockito:mockito-android:5.4.0")
//    androidTestImplementation ("org.mockito:mockito-inline:5.4.0")
    androidTestImplementation ("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-core:5.5.0")

    // Mockito Kotlin extension
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("app.cash.turbine:turbine:0.12.1")
    androidTestImplementation("app.cash.turbine:turbine:0.12.1")
    testImplementation ("org.mockito:mockito-inline:4.8.0")
    testImplementation ("com.google.truth:truth:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.0")
    androidTestImplementation ("androidx.test:rules:1.5.0")

}