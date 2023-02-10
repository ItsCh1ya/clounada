package ru.chiya.clounada

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.chiya.clounada.ui.theme.ClounadaTheme
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.ResourceBundle.getBundle

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
                    var s:String? = null
                    var n:Int? = null
                    s = bundle!!.getString("theatreName", "")
                    n = bundle!!.getInt("actionIndex")


                    val context = LocalContext.current
                    val json = BruhData(LocalContext.current).getActionByIndex(s, n)
                    ActInfo(json, context)


                }
            }
        }
    }
}



@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActInfo(action: JsonElement, context: Context) {
    Column() {
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
        Image(
            painter = painterResource(id = drawableResourceId),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)

        )
            Text(
                text = title,
                fontSize=30.sp
            )
            Text(
                text = description,
                fontSize=22.sp,
                color = Color.Black

            )
            Text(
                text = date,
                modifier = Modifier.padding(15.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = price + "₽",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }



