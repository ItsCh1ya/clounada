package ru.chiya.clounada

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
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
                    val bruhData = BruhData(context)
                    val action = theatreName?.let { bruhData.getActionByIndex(it, index) }

                    val theatre = bruhData.getEntireJson()!!.jsonObject[theatreName]
                    SeatChoose(theatre as JsonObject, bruhData, index)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun SeatChoose(theatre: JsonObject, db: BruhData, index: Int) {
    val context = LocalContext.current
    val theatreName = db.getValue(theatre, "name")
    val address = db.getValue(theatre, "address")
    val showSheet = { mutableStateOf(false) }
    val row = remember { mutableStateOf("") }
    val seat = remember { mutableStateOf("") }

    Column{
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
                // TODO: ОПЛАТА И ГЕНЕРАЦИЯ QR
                Toast.makeText(context, "pizda", Toast.LENGTH_SHORT)
            }) {
                Text(text = "Забронировать")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun PaymentSheetCardInput() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
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
        modifier = Modifier.width(100.dp)
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PaymentTopAppBar(
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
        }
    )
}