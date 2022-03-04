apply {
    from("$rootDir/common-android-library.gradle")
}

val implementation by configurations

dependencies {
    implementation(Libs.androidCore)
    implementation(Libs.materialDesign)
}