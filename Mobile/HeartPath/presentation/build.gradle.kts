plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    // Safe arg navigation for kotlin
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.zootopia.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Hilt
    var dagger_version = "2.45"
    var hilt_version = "1.0.0"
    implementation("com.google.dagger:hilt-android:$dagger_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_version")

    // retrofit & okhttp
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.1.0")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // framework ktx dependency 추가
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    // SDP
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.17.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // google location
    implementation("com.google.android.gms:play-services-location:21.0.1")
    
    // multidex
    implementation("com.android.support:multidex:1.0.3")

    // Kakao
    implementation("com.kakao.sdk:v2-all:2.14.0")

    // SSP
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    
    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // coordinatorlayout
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    
    // arcore - sceneview library
    implementation("io.github.sceneview:arsceneview:1.2.2")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // scale image view
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
}
