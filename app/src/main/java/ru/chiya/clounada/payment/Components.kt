package ru.chiya.clounada.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import ru.chiya.clounada.utils.BruhData


@Composable
fun PaymentTextFields(
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
fun PaymentSeatsTextField(row: MutableState<String>, name: String) {
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