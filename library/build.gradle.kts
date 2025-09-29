plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "simple.library.xxpermissions"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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
    //
//    implementation("com.android.support:support-fragment:24.2.0")
//    implementation(libs.device.compat)
    implementation("androidx.fragment:fragment:1.8.9")
    implementation(libs.xxpermissions)
}