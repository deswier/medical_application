plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation(compose.desktop.currentOs)
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.postgresql:postgresql:42.3.2")
    implementation("junit:junit:4.13.2")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation("androidx.activity:activity-ktx:1.2.0-rc01")

    implementation("dev.chrisbanes.accompanist:accompanist-glide:0.4.2")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation(
        "androidx.compose.ui:ui:1.0.1"
    )
    implementation(
        "androidx.compose.material:material:1.0.1"
    )
    implementation(
        "androidx.compose.ui:ui-tooling:1.0.1")
                implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha06")
                testImplementation ("junit:junit:4.+")
                androidTestImplementation ("androidx.test.ext:junit:1.1.2")
                androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
}