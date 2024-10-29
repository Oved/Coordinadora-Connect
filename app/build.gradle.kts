plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    alias(libs.plugins.google.firebase.appdistribution)
}

firebaseAppDistribution {
    appId = "candidato-ovedrincon"
    groups = "testers"
}

android {
    namespace = "com.ovedev.coordinadoraconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ovedev.coordinadoraconnect"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // ktx
    implementation (libs.androidx.activity.ktx)
    implementation (libs.androidx.fragment.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation (libs.firebase.analytics.ktx)
    implementation (libs.firebase.crashlytics)
    implementation (libs.places)
    implementation (libs.firebase.messaging.ktx)
    implementation (libs.play.services.maps)
    implementation (libs.play.services.location)

    // Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    //Volley
    implementation(libs.volley)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.rxjava2)
    implementation (libs.androidx.room.guava)
    testImplementation (libs.androidx.room.testing)
    implementation (libs.android.database.sqlcipher)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation (libs.androidx.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}