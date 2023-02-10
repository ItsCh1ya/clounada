package ru.chiya.clounada.action

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.chiya.clounada.main.MainActivity


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TobAppBatTitle(
    title: String,
    date: String,
    price: String,
    context: Context
) {
    TopAppBar(
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
        },
        actions = {
            IconButton(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.Home, "homeIcon")
            }
        }
    )
}