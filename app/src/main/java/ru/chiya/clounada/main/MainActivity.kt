package ru.chiya.clounada.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.json.JsonElement
import ru.chiya.clounada.ui.theme.ClounadaTheme
import ru.chiya.clounada.utils.BruhData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClounadaTheme {
                val systemUiController = rememberSystemUiController()
                val surface = MaterialTheme.colorScheme.surface
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = surface
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val json = BruhData(LocalContext.current).getEntireJson()
                    Tabs(json)
                }
            }
        }
    }

}

@Composable
fun Tabs(json: JsonElement) {
    val tabRowItems = listOf(
        TabRowItem(title = "Театр юного зрителя", screen = { TheatreTab(json).Cards("TYZ") }),
        TabRowItem(title = "Театр драмы", screen = { TheatreTab(json).Cards("TD") }),
        TabRowItem(title = "Цирк", screen = { TheatreTab(json).CircusBanner() })
    )
    ShowTabs(tabRowItems)
}