plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.zeljkolucic.core.database"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(projects.core.domain)
}