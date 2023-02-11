@file:OptIn(ExperimentalMaterialApi::class)

package ru.chiya.clounada.payment

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.chiya.clounada.utils.Booking
import ru.chiya.clounada.utils.Database

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PayButton(
    act: String,
    openPlaceDialog: MutableState<Boolean>,
    row: MutableState<String>,
    seat: MutableState<String>,
    context: Context,
    openDialog: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState
) {
    OutlinedButton(modifier = Modifier.padding(8.dp), onClick = {
        if (act.length > 0 && openPlaceDialog.toString().length > 0 && row.toString().length > 0 && seat.toString().length > 0) {
            val booking = Booking(
                act, openPlaceDialog.toString(), row.value.toInt(), seat.value.toInt()
            )
            val db = Database(context)
            db.insertData(booking)
            coroutineScope.launch { modalSheetState.hide() }
            openDialog.value = true
        } else {
            Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT).show()
        }
    }) {
        Icon(Icons.Filled.ShoppingCart, "cartIcon")
        Text(text = "Оплатить")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FakeCardInputFields() {
    Text(text = "—", style = MaterialTheme.typography.headlineLarge)
    Box {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Номер карты") },
                    modifier = Modifier.padding(8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "CVC") },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp)
                    )
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Дата") },
                        modifier = Modifier
                            .width(150.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}