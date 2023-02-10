package ru.chiya.clounada

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
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
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.chiya.clounada.ui.theme.ClounadaTheme

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

                    val theatre = bruhData.getEntireJson()!!.jsonObject[theatreName]
                    SeatChoose(theatre as JsonObject, bruhData, index, action as JsonObject, theatreName)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun SeatChoose(theatre: JsonObject, db: BruhData, index: Int, action: JsonObject, theatreName: String) {
    val context = LocalContext.current
    val theatreNameB = db.getValue(theatre, "name")
    val address = db.getValue(theatre, "address")
    val row = remember { mutableStateOf("") }
    val seat = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

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
                openDialog.value = true
            }) {
                Text(text = "Забронировать")
            }
            DrawModal(openDialog, action, row, seat, theatre)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawModal(openDialog: MutableState<Boolean>, action: JsonObject, row: MutableState<String>, seat: MutableState<String>, theatre: JsonObject){
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = action.jsonObject["title"]!!.jsonPrimitive.content, style = MaterialTheme.typography.headlineMedium)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Ряд: ${row.value}")
                        Text(text = "Место: ${seat.value}")
                    }
                    Barcode(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(250.dp)
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.medium),
                        resolutionFactor = 10, // Optionally, increase the resolution of the generated image
                        type = BarcodeType.QR_CODE, // pick the type of barcode you want to render
                        value = "http://example.com/api/brone/${row.value}:${seat.value}" // The textual representation of this code
                    )
                    Text(modifier = Modifier.padding(top = 16.dp), text = theatre["address"]!!.jsonPrimitive.content)
                    Text(text = action["date"]!!.jsonPrimitive.content)
                }
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