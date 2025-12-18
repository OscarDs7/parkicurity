plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Google Services (Firebase)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.parkicurity_system"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.parkicurity_system"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 🔹 Firebase BoM centraliza las versiones
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // 🔹 Firebase core services
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0") // Gráficas


    // AndroidX y Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}