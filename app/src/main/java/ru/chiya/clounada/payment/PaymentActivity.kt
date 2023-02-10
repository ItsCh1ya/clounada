package ru.chiya.clounada.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.action.ActionActivity
import ru.chiya.clounada.ui.theme.ClounadaTheme
import ru.chiya.clounada.utils.BruhData

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
                    val bruhData = BruhData(context)
                    val action = theatreName?.let { bruhData.getActionByIndex(it, index) }

                    val theatre = bruhData.getEntireJson().jsonObject[theatreName]
                    SeatChoose(
                        theatre as JsonObject,
                        bruhData,
                        index,
                        action as JsonObject,
                        theatreName
                    )
                }
            }
        }
    }
}

@Composable
fun SeatChoose(
    theatre: JsonObject,
    db: BruhData,
    index: Int,
    action: JsonObject,
    theatreName: String
) {
    val context = LocalContext.current
    val address = db.getValue(theatre, "address")
    val row = remember { mutableStateOf("") }
    val seat = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    Column {
        PaymentTopAppBar(theatreName, address, context, index)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
        ) {
            AuditoriumIage(db, theatre)
            PaymentTextFields(row, seat)
            Button(onClick = {
                openDialog.value = true
            }) {
                Text(text = "Забронировать")
            }
            DrawModal(openDialog, action, row, seat, theatre)
        }
    }
}


@Composable
private fun PaymentTextFields(
    row: MutableState<String>,
    seat: MutableState<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        PaymentSeatsTextField(row, "Ряд")
        PaymentSeatsTextField(seat, "Место")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PaymentSeatsTextField(row: MutableState<String>, name: String) {
    OutlinedTextField(
        value = row.value,
        label = {
            Text(text = name)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { row.value = it },
        modifier = Modifier
            .width(100.dp)
    )
}

@Composable
fun AuditoriumIage(db: BruhData, theatre: JsonObject) {
    val context = LocalContext.current
    val drawableResourceId: Int = context.resources.getIdentifier(
        db.getValue(theatre, "scheme"),
        "drawable",
        context.packageName
    )
    Image(
        painter = painterResource(id = drawableResourceId),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
    )
}

