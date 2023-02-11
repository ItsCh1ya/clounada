package ru.chiya.clounada.payment

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import ru.chiya.clounada.utils.Booking
import ru.chiya.clounada.utils.BruhData
import ru.chiya.clounada.utils.Database


@Composable
fun PaymentTextFields(
    act: String,
    row: MutableState<String>,
    seat: MutableState<String>,
    openDialog: MutableState<Boolean>,
    openPlaceDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    Column(Modifier.padding(bottom = 16.dp)) {
        PaymentSeatsTextField(row, "Ряд")
        PaymentSeatsTextField(seat, "Место")
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)) {
            FilledTonalButton(onClick = {
                openPlaceDialog.value = true
            }) {
                Text(text = "Выбрать место")
            }
            Button(onClick = {
                if(act.length > 0 &&
                    openPlaceDialog.toString().length > 0 &&
                    row.toString().length > 0 &&
                    seat.toString().length > 0) {


                    var booking = Booking(
                        act,
                        openPlaceDialog.toString(),
                        row.value.toInt(),
                        seat.value.toInt()
                    )
                    var db = Database(context)
                    db.insertData(booking)
                    openDialog.value = true
                } else{
                    Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Забронировать")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PaymentSeatsTextField(row: MutableState<String>, name: String) {
    OutlinedTextField(
        value = row.value,
        label = {
            Text(text = name)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { row.value = it },
        modifier = Modifier.fillMaxWidth()
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
        modifier = Modifier
            .fillMaxWidth()
    )
}