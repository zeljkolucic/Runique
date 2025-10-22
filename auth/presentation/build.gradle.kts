plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.zeljkolucic.auth.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}