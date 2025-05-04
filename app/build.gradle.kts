plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.justweddingpro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.justweddingpro"
        minSdk = 23
        targetSdk = 34
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

    flavorDimensions += "flavor"
    productFlavors {
        create("admin") {
            dimension = "flavor"
            applicationId = "com.example.justweddingpro"
            resValue("string", "app_name", "JustWeddingPro")
            applicationIdSuffix = ".admin"
        }

        create("ManagerAndCaptain") {
            dimension = "flavor"
            applicationId = "com.managerandcaptain.justweddingpro"
            versionCode = 1
            versionName = "1.0"
            resValue("string", "app_name", "JustWeddingProManager")
            applicationIdSuffix = ".ManagerAndCaptain"
        }

        create("client") {
            dimension = "flavor"
            applicationId = "com.client.justweddingpro"
            versionCode = 1
            versionName = "1.0"
            resValue("string", "app_name", "JustWedding")
            applicationIdSuffix = ".client"
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
        dataBinding = true
        viewBinding = true
    }
    sourceSets {
        getByName("ManagerAndCaptain") {
            java {
                srcDirs("src/ManagerAndCaptain/java")
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.material:material:1.2.1-alpha1")

    implementation("com.pusher:pusher-java-client:2.4.2")

    /*Share Preference Security*/
    implementation("androidx.security:security-crypto:1.1.0-alpha02")
    //SDP
    implementation("com.intuit.sdp:sdp-android:1.0.4")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    //OKHTTP or SVG
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation("com.squareup.okhttp3:okhttp:4.7.2")
    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val coroutinesVersion = "1.7.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.greenrobot:eventbus:3.3.1")
    /*Multidex depedancy*/
    implementation("androidx.multidex:multidex:2.0.1")
    /*Firebased*/
//    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
//    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.github.sundeepk:compact-calendar-view:3.0.0")

    //RX java and RX android
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    /*Flrxible Layout*/
    implementation("com.google.android.flexbox:flexbox:3.0.0")

//    implementation("org.naishadhparmar.zcustomcalendar:zcustomcalendar:1.0.1")

    //Firebase Crashlytics
//    implementation("com.google.firebase:firebase-crashlytics:18.1.0")
//    implementation("com.google.firebase:firebase-analytics:19.0.0")

//    implementation("com.google.android.gms:play-services-gcm:17.0.0")
//    implementation("com.google.android.gms:play-services-location:21.3.0")
}