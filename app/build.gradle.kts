plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") // Room ke liye fast processing
    id("kotlin-kapt") // Hilt ke liye
}

android {
    namespace = "com.example.expensetrackerai"
    // SDK 35 use karein kyunki aapki libraries latest hain
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expensetrackerai"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true // Agar aap XML bhi use kar rahe hain
    }

    composeOptions {
        // Kotlin 1.9.x ke liye ye version stable hai
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/arg-attributes.properties"
        }
    }
}

dependencies {
    // Core Android - Latest stable versions
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // JETPACK COMPOSE (UI)
    val composeBom = platform("androidx.compose:compose-bom:2025.01.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // HILT (Dependency Injection)
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // ROOM DATABASE
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // PERMISSIONS & CHARTS
    implementation("com.google.accompanist:accompanist-permissions:0.36.0")
    implementation("com.patrykandpatrick.vico:compose:1.15.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.15.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.01.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    
    // Gemini AI SDK
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
}

// Hilt error fix
kapt {
    correctErrorTypes = true
}