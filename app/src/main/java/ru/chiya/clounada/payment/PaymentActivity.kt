package ru.chiya.clounada.payment

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
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
                    SeatChoose(index, theatreName!!)
                }
            }
        }
    }
}

@Composable
fun SeatChoose(
    index: Int,
    theatreName: String,
) {
    val context = LocalContext.current

    val db = BruhData(context)
    val action = theatreName?.let { db.getActionByIndex(it, index) }

    val theatre = db.getEntireJson().jsonObject[theatreName]
    val theatreTitle = db.getValue(theatre as JsonObject, "name")

    val address = db.getValue(theatre, "address")
    val row = remember { mutableStateOf("") }
    val seat = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val openPlaceDialog = remember { mutableStateOf(false) }

    Column {
        PaymentTopAppBar(theatreName, theatreTitle, address, context, index)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
        ) {
            AuditoriumIage(db, theatre)
            PaymentTextFields(row, seat, openDialog, openPlaceDialog)
            DrawModal(openDialog, action as JsonObject, row, seat, theatre)
            DrawPlacesModal(openPlacesDialog = openPlaceDialog, theatreName = theatreName)
        }
    }
}

