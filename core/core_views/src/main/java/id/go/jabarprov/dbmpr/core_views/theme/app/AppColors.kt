package id.go.jabarprov.dbmpr.core_views.theme.app

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import id.go.jabarprov.dbmpr.core_views.theme.*

data class AppColors(
    val tertiary: Color,
    val tertiaryVariant: Color,
    val onTertiary: Color,
    val active: Color,
    val info: Color,
    val warning: Color,
    val headerTextColor: Color,
    val bodyTextColor: Color,
    val disabledTextColor: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        tertiary = Color.Unspecified,
        tertiaryVariant = Color.Unspecified,
        onTertiary = Color.Unspecified,
        active = Color.Unspecified,
        info = Color.Unspecified,
        warning = Color.Unspecified,
        headerTextColor = Color.Unspecified,
        bodyTextColor = Color.Unspecified,
        disabledTextColor = Color.Unspecified
    )
}