// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val compose_version by extra("1.0.0-beta09")
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
    val kotlinVersion by extra("1.5.10")
    val moshiVersion by extra(":1.11.0")
    val systemUIControllerVersion by extra("0.12.0")
    val expressoVersion by extra("3.3.0")
    val kotlinSerializationVersion by extra("1.2.1")
    val navigationAnimation by extra("0.16.0")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha02")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}