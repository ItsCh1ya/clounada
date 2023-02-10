package ru.chiya.clounada

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.ui.theme.ClounadaTheme

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
                    var s: String? = null
                    var n: Int? = null
                    s = bundle!!.getString("theatreName", "")
                    n = bundle!!.getInt("actionIndex")


                    val context = LocalContext.current
                    val json = BruhData(LocalContext.current).getActionByIndex(s, n)
                    ActInfo(json, context, s, n)


                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActInfo(action: JsonElement, context: Context, theatreName: String, actionIndex: Int) {
    val title = action.jsonObject["title"]!!.jsonPrimitive.content // bruh
    val price = action.jsonObject["price"]!!.jsonPrimitive.content // bruh №2
    val description = action.jsonObject["description"]!!.jsonPrimitive.content
    val date = action.jsonObject["date"]!!.jsonPrimitive.content
    val resourceName = action.jsonObject["preview"]!!.jsonPrimitive.content
    val drawableResourceId: Int = context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName

    )
    Column() {
        TopAppBar(
            title = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(text = title, style = MaterialTheme.typography.labelLarge)
                        Text(text = date, style = MaterialTheme.typography.bodySmall)
                    }
                    Text(modifier = Modifier.padding(end = 8.dp), text = price + "₽")
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            }
        )
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = drawableResourceId),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.extraLarge)

            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(onClick = {
                val intent = Intent(context, PaymentActivity::class.java)
                intent.putExtra("theatreName", theatreName)
                intent.putExtra("actionIndex", actionIndex)
                context.startActivity(intent)
            }) {
                Text(
                    "Купить",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }
}



