/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.5.31"
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "dev.spikeysanju.einsen"
        minSdk = 21
        targetSdk = 30
        versionCode = 4
        versionName = "v1.0.0-alpha04"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = true
    }
    buildTypes {

        debug {
            versionNameSuffix = ".dev"
            isDebuggable = true
        }

        release {
            isDebuggable = false
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
        kotlinOptions.freeCompilerArgs += "-Xopt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes += "/META-INF/AL2.0"
        resources.excludes += "/META-INF/LGPL2.1"
    }
}
dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["ktxCoreVersion"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["materialVersion"]}")
    implementation("com.google.android.material:material:${rootProject.extra["materialVersion"]}")
    implementation("androidx.compose.ui:ui:${rootProject.extra["composeVersion"]}")
    implementation("androidx.compose.animation:animation-graphics:${rootProject.extra["composeAnimation"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["composeVersion"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["composeVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifeCycleVersion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["composeActivityVersion"]}")
    implementation("androidx.hilt:hilt-work:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra["expressoVersion"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["composeVersion"]}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // compose navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["composeNavigationVersion"]}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra["hiltComposeNavVersion"]}")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:${rootProject.extra["dataStoreVersion"]}")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    kapt("org.xerial:sqlite-jdbc:${rootProject.extra["jdbcVersion"]}")
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
    implementation("com.google.accompanist:accompanist-navigation-material:${rootProject.extra["navigationAnimation"]}")

    // Lottie
    implementation("com.airbnb.android:${rootProject.extra["lottieAnimation"]}")

    // Square Logcat
    implementation("com.squareup.logcat:logcat:${rootProject.extra["logcatVersion"]}")

    // Worker + Coroutine
    implementation("androidx.work:work-runtime-ktx:${rootProject.extra["workerVersion"]}")
}

