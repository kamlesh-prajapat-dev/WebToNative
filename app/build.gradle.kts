plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // Hilt and Ksp Plugin
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)

    // Kotlin Serialization
    kotlin("plugin.serialization") version "2.3.21"

    // Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.webtonative"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.webtonative"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "WEB_CLIENT_ID",
            "${project.findProperty("WEB_CLIENT_ID")}"
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Material Icons Extended
    implementation(libs.androidx.compose.material.icons.extended)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt and Ksp
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Hilt View Model
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)

    // Glide
    implementation(libs.glide)

    // Timber
    implementation(libs.timber)

    // Firebase Services
    implementation(platform(libs.firebase.bom)) // Firebase Bom
    implementation(libs.firebase.auth) // Firebase auth

    // dependencies for the Credential Manager libraries and specify their versions
    //noinspection LoginCredentials
    implementation(libs.androidx.credentials)
    //noinspection LoginCredentials
    implementation(libs.androidx.credentials.play.services.auth)
    //noinspection LoginCredentials
    implementation(libs.googleid)

    // Room Database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Worker and Hilt Compiler
    implementation(libs.androidx.work.runtime.ktx)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.work)
}