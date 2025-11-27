plugins {
    alias(libs.plugins.runique.android.feature.ui)

}

android {
    namespace = "com.zeljkolucic.run.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.timber)
    implementation(libs.google.maps.android.compose)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}