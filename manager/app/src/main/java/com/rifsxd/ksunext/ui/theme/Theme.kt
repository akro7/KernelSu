package com.rifsxd.ksunext.ui.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

// ── AKRO Alpha Dark Scheme (Crimson) ──────────────────────────────
private val AlphaDarkColorScheme = darkColorScheme(
    primary                  = CRIMSON_PRIMARY,
    onPrimary                = ON_CRIMSON,
    primaryContainer         = CRIMSON_CONTAINER,
    onPrimaryContainer       = CRIMSON_LIGHT,

    secondary                = GOLD_ACCENT,
    onSecondary              = AMOLED_BLACK,
    secondaryContainer       = Color(0xFF3D2800),
    onSecondaryContainer     = GOLD_ACCENT,

    tertiary                 = SAPPHIRE_ACCENT,
    onTertiary               = ON_CRIMSON,

    background               = AMOLED_BLACK,
    onBackground             = ON_DARK,
    surface                  = DARK_BG,
    onSurface                = ON_DARK,
    surfaceVariant           = CARD_BG,
    onSurfaceVariant         = MUTED_TEXT,
    surfaceContainer         = CARD_BG,
    surfaceContainerLow      = CARD_BG_2,
    surfaceContainerHigh     = CRIMSON_SURFACE,
    surfaceContainerHighest  = CRIMSON_SURFACE,

    error                    = STATUS_INACTIVE,
    onError                  = ON_CRIMSON,
    outline                  = CRIMSON_DARK,
    outlineVariant           = Color(0xFF3A0010),
)

// ── AKRO Alpha Light Scheme ────────────────────────────────────────
private val AlphaLightColorScheme = lightColorScheme(
    primary                  = CRIMSON_PRIMARY,
    onPrimary                = ON_CRIMSON,
    primaryContainer         = Color(0xFFFFDAD9),
    onPrimaryContainer       = CRIMSON_DARK,

    secondary                = CRIMSON_DARK,
    onSecondary              = ON_CRIMSON,

    background               = Color(0xFFFFF8F7),
    surface                  = Color(0xFFFFF8F7),
    surfaceVariant           = Color(0xFFFFEDEB),
    surfaceContainer         = Color(0xFFFFF0EE),
)

fun Color.blend(other: Color, ratio: Float): Color {
    val inverse = 1f - ratio
    return Color(
        red   = red   * inverse + other.red   * ratio,
        green = green * inverse + other.green * ratio,
        blue  = blue  * inverse + other.blue  * ratio,
        alpha = alpha
    )
}

@Composable
fun KernelSUTheme(
    darkTheme: Boolean    = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,          // disabled – use Alpha crimson
    amoledMode: Boolean   = true,           // AMOLED on by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        amoledMode && darkTheme -> AlphaDarkColorScheme.copy(
            background               = AMOLED_BLACK,
            surface                  = AMOLED_BLACK,
            surfaceVariant           = CARD_BG,
            surfaceContainer         = CARD_BG,
            surfaceContainerLow      = CARD_BG_2,
            surfaceContainerLowest   = AMOLED_BLACK,
            surfaceContainerHigh     = CRIMSON_SURFACE,
            surfaceContainerHighest  = CRIMSON_SURFACE,
        )
        darkTheme -> AlphaDarkColorScheme
        else      -> AlphaLightColorScheme
    }

    SystemBarStyle(darkMode = darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}

@Composable
private fun SystemBarStyle(
    darkMode: Boolean,
    statusBarScrim: Color     = Color.Transparent,
    navigationBarScrim: Color = Color.Transparent,
) {
    val activity = LocalContext.current as ComponentActivity
    SideEffect {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                statusBarScrim.toArgb(), statusBarScrim.toArgb()
            ) { darkMode },
            navigationBarStyle = when {
                darkMode -> SystemBarStyle.dark(navigationBarScrim.toArgb())
                else     -> SystemBarStyle.light(
                    navigationBarScrim.toArgb(), navigationBarScrim.toArgb()
                )
            }
        )
    }
}
