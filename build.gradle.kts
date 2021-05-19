// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val compose_version by extra("1.0.0-beta06")
    val composeNavigationVersion by extra("1.0.0-alpha10")
    val hiltVersion by extra("2.33-beta")
    val hiltAndroidXVersion by extra("1.0.0-alpha03")
    val roomVersion by extra("2.3.0")
    val dataStoreVersion by extra("1.0.0-beta01")
    val coroutinesVersion by extra("1.4.3")
    val hiltComposeVersion by extra("1.0.0-alpha01")
    val hiltCompilerVersion by extra("1.0.0")
    val kotlinVersion by extra("1.4.32")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}