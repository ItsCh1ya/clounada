package ru.chiya.clounada.payment

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import ru.chiya.clounada.utils.getListOfPlaces


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrawPlacesModal(openPlacesDialog: MutableState<Boolean>, theatreName: String) {
    if (openPlacesDialog.value) {
        AlertDialog(
            onDismissRequest = { openPlacesDialog.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                val listPlaces = getListOfPlaces(theatreName)
                PlacesModalContent(listPlaces, openPlacesDialog)
            }
        }
    }
}

@Composable
fun PlacesModalContent(listPlaces: List<String>, openPlacesDialog: MutableState<Boolean>) {
    LazyColumn(){
        items(listPlaces.size){ i ->
            TextButton(onClick = {
                openPlacesDialog.value = false
            }) {
                Text(text = listPlaces[i])
            }
        }
    }
}