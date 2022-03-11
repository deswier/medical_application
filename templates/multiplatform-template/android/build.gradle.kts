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

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}