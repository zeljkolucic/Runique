import androidx.room.gradle.RoomExtension
import com.android.build.gradle.LibraryExtension
import com.zeljkolucic.convention.ExtensionType
import com.zeljkolucic.convention.configureAndroidCompose
import com.zeljkolucic.convention.configureBuildTypes
import com.zeljkolucic.convention.configureKotlinAndroid
import com.zeljkolucic.convention.configureKotlinJvm
import com.zeljkolucic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class JvmLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlinJvm()
        }
    }
}