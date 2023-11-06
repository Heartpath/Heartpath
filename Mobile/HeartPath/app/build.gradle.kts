import java.util.Properties

// ktlint-disable no-wildcard-imports

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())


android {
    namespace = "com.zootopia.heartpath"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zootopia.heartpath"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        
        buildConfigField("String", "NAVER_MAP_CLIENT_ID", "\"" + properties["naver_map_client_id"] + "\"")
        addManifestPlaceholders(mutableMapOf("NAVER_MAP_CLIENT_ID" to localProperties["naver_map_client_id"]!!))
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", localProperties.getProperty("kakao_native_app_key"))
        addManifestPlaceholders(mutableMapOf("KAKAO_NATIVE_APP_KEY" to localProperties["kakao_native_app_key"]!!))

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    
    // Hilt
    val dagger_version = "2.45"
    val hilt_version = "1.0.0"
    implementation("com.google.dagger:hilt-android:$dagger_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_version")

    // retrofit & okhttp
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.3.0")

    // kakao
    implementation("com.kakao.sdk:v2-all:2.14.0")
    
    // multidex
    implementation("com.android.support:multidex:1.0.3")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")
}
