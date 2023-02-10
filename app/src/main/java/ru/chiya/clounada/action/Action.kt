package ru.chiya.clounada.action

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.MainActivity

class Action(theatre: JsonObject, private val actionIndex: Int) {
    private val theatreName = theatre["name"]!!.jsonPrimitive.content
    private val action = theatre.jsonObject["actions"]!!.jsonArray[actionIndex]

    @Composable
    fun ShowAction() {
        val context = LocalContext.current
        val scroll = rememberScrollState(0)
        Column() {
            ActionTopBar(context = context)
            Column(
                Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                HeaderImage()
//                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn() {
                    item {
                        Text(
                            text = action.jsonObject["date"]!!.jsonPrimitive.content,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Text(
                    text = action.jsonObject["description"]!!.jsonPrimitive.content
                )
            }
            Button(contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                onClick = {

                }
            ) {
                Text(text = "Забронировать")
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun ActionTopBar(context: Context) {
        TopAppBar(
            title = {
                Column() {
                    Text(
                        text = action.jsonObject["title"]!!.jsonPrimitive.content,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(text = theatreName, style = MaterialTheme.typography.titleSmall)
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    fuckGoBack(context)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }
        )
    }

    @Composable
    fun HeaderImage() {
        val context = LocalContext.current
        val resourceName = action.jsonObject["preview"]!!.jsonPrimitive.content
        val imageShape = RoundedCornerShape(32.dp)

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
                .aspectRatio(16f / 9f)
                .clip(imageShape)
        )
    }

    private fun fuckGoBack(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("theatreName", theatreName)
        intent.putExtra("actionIndex", actionIndex)
        context.startActivity(intent)
    }
}
