package ru.chiya.clounada.action

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.payment.PaymentActivity
import ru.chiya.clounada.ui.theme.ClounadaTheme
import ru.chiya.clounada.utils.BruhData

class ActionActivity : ComponentActivity() {

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
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val bundle = intent.extras
                    val theatreName = bundle!!.getString("theatreName", "")
                    val actionIndex = bundle.getInt("actionIndex")

                    ActInfo(theatreName, actionIndex)

                }
            }
        }
    }
}


@Composable
fun ActInfo(theatreName: String, actionIndex: Int) {
    val context = LocalContext.current
    val action = BruhData(LocalContext.current).getActionByIndex(theatreName, actionIndex)

    val title = action.jsonObject["title"]!!.jsonPrimitive.content
    val date = action.jsonObject["date"]!!.jsonPrimitive.content
    val price = action.jsonObject["price"]!!.jsonPrimitive.content

    val description = action.jsonObject["description"]!!.jsonPrimitive.content
    val resourceName = action.jsonObject["preview"]!!.jsonPrimitive.content
    val drawableResourceId: Int = context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName
    )
    Column {
        TobAppBatTitle(title, date, price, context)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.padding(bottom = 16.dp)) {
                ActionContent(drawableResourceId, description)
            }
            Button(onClick = {
                val intent = Intent(context, PaymentActivity::class.java)
                intent.putExtra("theatreName", theatreName)
                intent.putExtra("actionIndex", actionIndex)
                context.startActivity(intent)
            }) {
                Text(
                    "Забронировать",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }
}





