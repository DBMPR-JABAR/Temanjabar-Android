package id.go.jabarprov.dbmpr.core_views.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.go.jabarprov.dbmpr.core_views.R

private val Roboto = FontFamily(
    Font(R.font.roboto_thin, weight = FontWeight.Thin),
    Font(R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.roboto_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.roboto_black, weight = FontWeight.Black),
    Font(R.font.roboto_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
)

private val Lato = FontFamily(
    Font(R.font.lato_thin, weight = FontWeight.Thin),
    Font(R.font.lato_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.lato_light, weight = FontWeight.Light),
    Font(R.font.lato_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.lato_black, weight = FontWeight.Black),
    Font(R.font.lato_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
)

private val Lora = FontFamily(
    Font(R.font.lora_regular, weight = FontWeight.Normal),
    Font(R.font.lora_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.lora_medium, weight = FontWeight.Medium),
    Font(R.font.lora_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.lora_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.lora_semi_bold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.lora_bold, weight = FontWeight.Bold),
    Font(R.font.lora_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
)

private val Intro = FontFamily(
    Font(R.font.intro_thin, weight = FontWeight.Thin),
    Font(R.font.intro_light, weight = FontWeight.Light),
    Font(R.font.intro_regular, weight = FontWeight.Normal),
    Font(R.font.intro_bold, weight = FontWeight.Bold),
    Font(R.font.intro_black, weight = FontWeight.Black),
)

val AppTypography = Typography(
    defaultFontFamily = Lato,
    h4 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    caption = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
