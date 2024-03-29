package ru.chiya.clounada.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.R
import ru.chiya.clounada.action.ActionActivity

class TheatreTab(private val dataJson: JsonElement?) {
    @SuppressLint("DiscouragedApi")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Cards(theatreName: String) {
        TabScreen {
            val context = LocalContext.current
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    val actions =
                        dataJson!!.jsonObject[theatreName]!!.jsonObject["actions"] // List of all actions of theatre
                    val actionsSize = actions!!.jsonArray.size // Length of list

                    items(actionsSize) { index ->
                        val action = actions.jsonArray[index]
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.medium),
                            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                            onClick = {
                                val intent = Intent(context, ActionActivity::class.java)
                                intent.putExtra("theatreName", theatreName)
                                intent.putExtra("actionIndex", index)
                                context.startActivity(intent)
                            },
                            content = {
                                CardContent(action, context)
                            }
                        )
                    }
                }
            )
        }
    }

    @Composable
    private fun CardContent(action: JsonElement, context: Context) {
        Column {
            val title = action.jsonObject["title"]!!.jsonPrimitive.content // bruh
            val price = action.jsonObject["price"]!!.jsonPrimitive.content // bruh №2
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
                    .clip(MaterialTheme.shapes.medium)
                    .height(80.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.7f),
                    style = MaterialTheme.typography.labelLarge,
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
    }
}