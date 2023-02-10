package ru.chiya.clounada.payment

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import ru.chiya.clounada.action.ActionActivity
import ru.chiya.clounada.main.MainActivity

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PaymentTopAppBar(
    theatreName: String,
    address: String,
    context: Context,
    index: Int
) {
    TopAppBar(
        title = {
            Column {
                Text(text = theatreName, style = MaterialTheme.typography.labelLarge)
                Text(text = address, style = MaterialTheme.typography.bodySmall)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                val intent = Intent(context, ActionActivity::class.java)
                intent.putExtra("theatreName", theatreName)
                intent.putExtra("actionIndex", index)
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