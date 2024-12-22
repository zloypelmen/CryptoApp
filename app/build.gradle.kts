plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.leonid.crupto"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.leonid.crupto"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.picasso)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.reactivestreams)
    implementation(libs.lifecycle.livedata)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.retrofit.rx.adapter)
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.rxjava3.rxandroid)
    implementation(libs.rxjava3.rxjava)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    kapt(libs.lifecycle.compiler)
    kapt(libs.room.compiler)
}