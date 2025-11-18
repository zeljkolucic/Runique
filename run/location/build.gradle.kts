plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.zeljkolucic.run.location"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}