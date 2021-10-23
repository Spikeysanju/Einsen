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

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val composeVersion by extra("1.0.2")
    val composeActivityVersion by extra("1.3.0-beta02")
    val ktxCoreVersion by extra("1.5.0")
    val lifeCycleVersion by extra("2.3.1")
    val materialVersion by extra("1.3.0")
    val composeNavigationVersion by extra("2.4.0-alpha03")
    val hiltComposeNavVersion by extra("1.0.0-alpha03")
    val hiltVersion by extra("2.37")
    val hiltAndroidXVersion by extra("1.0.0-alpha03")
    val roomVersion by extra("2.3.0")
    val dataStoreVersion by extra("1.0.0-beta02")
    val coroutinesVersion by extra("1.5.0-native-mt")
    val hiltComposeVersion by extra("1.0.0-alpha03")
    val hiltCompilerVersion by extra("1.0.0")
    val jdbcVersion by extra("3.36.0.3")
    val kotlinVersion by extra("1.5.21")
    val systemUIControllerVersion by extra("0.17.0")
    val expressoVersion by extra("3.3.0")
    val kotlinSerializationVersion by extra("1.2.1")
    val navigationAnimation by extra("0.19.0")
    val composeAnimation by extra("1.1.0-alpha02")
    val lottieAnimation by extra("lottie-compose:1.0.0-rc02-1")
    val navigationMaterialVersion by extra("0 19.0")
    val logcatVersion by extra("0.1")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-beta01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")
    }
}

plugins {
    id("com.diffplug.spotless") version ("5.14.0")
}

allprojects {

    apply {
        plugin("com.diffplug.spotless")
    }

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint("0.41.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
        }
    }
}
