package ru.chiya.clounada.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawModal(
    openDialog: MutableState<Boolean>,
    action: JsonObject,
    row: MutableState<String>,
    seat: MutableState<String>,
    theatre: JsonObject
) {
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
                ModalContent(action, row, seat, theatre)
            }
        }
    }

}

@Composable
private fun ModalContent(
    action: JsonObject,
    row: MutableState<String>,
    seat: MutableState<String>,
    theatre: JsonObject
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = action.jsonObject["title"]!!.jsonPrimitive.content,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Ряд: ${row.value}")
            Text(text = "Место: ${seat.value}")
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            val encodedBitmapQR = encodeAsBitmap(str = "https://example.com/${row.value}:${seat.value}")!!.asImageBitmap()
            Image(
                bitmap = encodedBitmapQR,
                contentDescription = "zalupa",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(MaterialTheme.shapes.extraLarge).fillMaxWidth().aspectRatio(1f)
            )
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = theatre["address"]!!.jsonPrimitive.content
        )
        Text(text = action["date"]!!.jsonPrimitive.content)
    }
}