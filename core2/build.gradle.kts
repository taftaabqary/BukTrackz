plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.unsoed.buktrackz.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    api(libs.androidx.activity)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)

    testApi(libs.junit)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)
    debugApi(libs.squareup.leakcanary.android)

    testApi(libs.mockito.core)
    testApi(libs.mockito.inline)

    api(libs.material)
    api(libs.rxjava)
    api(libs.rxbinding)
    implementation(libs.glide)
    api(libs.lottie)

    api(libs.androidx.constraintlayout)

    api(libs.androidx.recyclerview)
    api(libs.io.insert.koin.koin.core)
    api(libs.insert.koin.koin.android)

    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)
    api(libs.androidx.paging3.ktx)

    api(libs.scottyab.rootbeer.lib)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.coroutines.android)

    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}