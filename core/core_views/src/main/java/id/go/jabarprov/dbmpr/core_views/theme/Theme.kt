package id.go.jabarprov.dbmpr.core_views.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import id.go.jabarprov.dbmpr.core_views.theme.app.AppColors
import id.go.jabarprov.dbmpr.core_views.theme.app.LocalAppColors

private val LighColors = lightColors(
    primary = Green600,
    primaryVariant = Green800,
    secondary = Yellow400,
    secondaryVariant = Yellow600,
    error = Red700,
    onSecondary = Color.White,
)

private val AppColors = AppColors(
    tertiary = Blue600,
    tertiaryVariant = Blue800,
    onTertiary = Color.White,
    active = Green700,
    info = Blue700,
    warning = Yellow700,
    headerTextColor = Gray900,
    bodyTextColor = Gray800,
    disabledTextColor = Gray600
)

@Composable
fun TemanJabarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides AppColors
    ) {
        MaterialTheme(
            colors = LighColors,
            typography = AppTypography
        ) {
            content()
        }
    }
}