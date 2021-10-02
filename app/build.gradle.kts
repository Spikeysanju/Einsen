plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.4.0"
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "dev.spikeysanju.einsen"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    lint {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
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
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"

    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}
dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["ktxCoreVersion"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["materialVersion"]}")
    implementation("com.google.android.material:material:${rootProject.extra["materialVersion"]}")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.animation:animation-graphics:${rootProject.extra["composeAnimation"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifeCycleVersion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["composeActivityVersion"]}")
    implementation("androidx.hilt:hilt-work:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["expressoVersion"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

    // compose navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["composeNavigationVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltComposeNavVersion"]}")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:${rootProject.extra["dataStoreVersion"]}")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    kapt("org.xerial:sqlite-jdbc:3.34.0")
    kapt("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")

    // System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:${rootProject.extra["systemUIControllerVersion"]}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutinesVersion"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutinesVersion"]}")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${rootProject.extra["hiltAndroidXVersion"]}")
    kapt("androidx.hilt:hilt-compiler:${rootProject.extra["hiltCompilerVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltComposeVersion"]}")
    implementation("androidx.hilt:hilt-common:${rootProject.extra["hiltCompilerVersion"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")

    // KotlinX Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${rootProject.extra["kotlinSerializationVersion"]}")

    // Compose Navigation Animation
    implementation("com.google.accompanist:accompanist-navigation-animation:${rootProject.extra["navigationAnimation"]}")

    // Lottie
    implementation("com.airbnb.android:${rootProject.extra["lottieAnimation"]}")

    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.6.0")

}
