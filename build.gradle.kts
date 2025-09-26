plugins {
    id("com.android.application") version "8.4.2"
    kotlin("android") version "1.9.24"
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"   // ⬅️ ajoute KSP
}

android {
    namespace = "fr.dawanAndroidMap"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.dawanAndroidMap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures { viewBinding = true }
    kotlin { jvmToolchain(17) }
}

dependencies {
    // Kotlin/Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("com.google.android.material:material:1.12.0")

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room (SQLite)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.test:core-ktx:1.7.0")
    implementation("androidx.test.ext:junit-ktx:1.3.0")
    implementation("androidx.test.espresso:espresso-contrib:3.7.0")
    ksp("androidx.room:room-compiler:2.6.1")

    // UI
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Tests (optionnel)
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1") // RecyclerViewActions
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
}