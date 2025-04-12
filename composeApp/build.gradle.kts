import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.composeHotReload)
  alias(libs.plugins.sqldelight)
  alias(libs.plugins.wire)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  jvm("desktop")

  sourceSets {
    val desktopMain by getting

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)
      implementation(libs.sqldelight.runtime)
      implementation(libs.sqldelight.coroutines.extensions)
      implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
      // why did I need the alpha version??? I think stable version did not support KMP desktop
      implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
      implementation(libs.wire)
      // https://github.com/eygraber/uri-kmp
      implementation("com.eygraber:uri-kmp:0.0.19")
      // https://github.com/05nelsonm/encoding
      implementation("io.matthewnelson.encoding:base32:2.4.0")
      implementation(libs.kotlinx.serialization.json)
      // https://github.com/psuzn/multiplatform-paths
      implementation("me.sujanpoudel.multiplatform.utils:multiplatform-paths:0.2.2")
      // https://github.com/marcelkliemannel/kotlin-onetimepassword
      implementation("dev.turingcomplete:kotlin-onetimepassword:2.4.1")
    }
    desktopMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.swing)
      implementation(libs.sqldelight.jvm.driver)
    }
  }
}


compose.desktop {
  application {
    mainClass = "dev.tberghuis.twofactor.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "dev.tberghuis.twofactor"
      packageVersion = "1.0.0"


      modules("java.sql")
    }

    buildTypes.release.proguard {
      isEnabled = false
      obfuscate = false
      configurationFiles.from("rules.pro")
    }


  }
}

composeCompiler {
  featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}

tasks.register<ComposeHotRun>("runHot") {
  mainClass.set("dev.tberghuis.twofactor.MainKt")
}

sqldelight {
  databases {
    create("AppDatabase") {
      packageName.set("dev.tberghuis.twofactor.sqldelight")
    }
  }
}

wire {
  kotlin {}
}