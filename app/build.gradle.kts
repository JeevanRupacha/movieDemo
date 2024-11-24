import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.parcelize.get().pluginId)
}

android {
    namespace = "com.demo.moviedemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.demo.moviedemo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "TMDB_AUTH_KEY", "\"${properties.getProperty("TMDB_AUTH_KEY")}\"")
        }
        debug {
            buildConfigField("String", "TMDB_AUTH_KEY", "\"${properties.getProperty("TMDB_AUTH_KEY")}\"")
        }
    }

    packagingOptions {
        resources {
            merges.add("META-INF/LICENSE.md") // Merge all LICENSE.md files into one
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
        buildConfig = true 
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Testing
    testImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.arch.core)
    androidTestImplementation(libs.androidx.arch.core)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.uiAutomater)
    androidTestImplementation(libs.expresso.web)
    testImplementation(libs.turbine)
    // Testing

    implementation(libs.hilt)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.material.icon)
    implementation(libs.datastore)
    implementation(libs.retrofit)
    implementation(libs.retrofit.scalar)
    implementation(libs.google.fonts)
    implementation(libs.work)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.immutable.list)
    implementation(libs.jackson.converter)
    implementation(libs.jackson.module)
    implementation(libs.jackson.datatype)
    implementation(libs.gson)
}