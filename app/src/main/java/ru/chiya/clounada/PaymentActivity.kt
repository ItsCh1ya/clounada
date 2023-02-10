package ru.chiya.clounada

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import ru.chiya.clounada.payment.SeatChoose
import ru.chiya.clounada.ui.theme.ClounadaTheme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            ClounadaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val index = intent.getIntExtra("actionIndex", 0)
                    val theatreName = intent.getStringExtra("theatreName")

                    val obj = BruhData(context).getEntireJson()
                    val theatre = obj!!.jsonObject[theatreName]
                    SeatChoose(theatre as JsonObject, index).SeatChooser()
                }
            }
        }
    }
}
