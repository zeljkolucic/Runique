plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.zeljkolucic.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}