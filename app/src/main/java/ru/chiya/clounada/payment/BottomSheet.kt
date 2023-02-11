@file:OptIn(ExperimentalMaterialApi::class)

package ru.chiya.clounada.payment

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
    Button(onClick = {
        if (act.length > 0 &&
            openPlaceDialog.toString().length > 0 &&
            row.toString().length > 0 &&
            seat.toString().length > 0
        ) {


            val booking = Booking(
                act,
                openPlaceDialog.toString(),
                row.value.toInt(),
                seat.value.toInt()
            )
            val db = Database(context)
            db.insertData(booking)
            coroutineScope.launch { modalSheetState.hide() }
            openDialog.value = true
        } else {
            Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Оплатить")
    }
}