package net.ifmain.hwanul_toktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import net.ifmain.hwanul_toktok.presentation.view.AppNavigation
import net.ifmain.hwanul_toktok.ui.theme.HwanulTokTokTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HwanulTokTokTheme {
                AppNavigation()
            }
        }
    }
}