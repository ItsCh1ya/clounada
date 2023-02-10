package ru.chiya.clounada.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SeatChoose(val theatre: JsonObject, val actionIndex: Int) {

    @Composable
    fun SeatChooser() {
        Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            AuditoriumScheme()
            InputFields()
        }
    }
    
    @Composable
    fun InputFields(){
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
        ) {
            InputField(label = "Ряд")
            InputField(label = "Место")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InputField(label: String) {
        var text by remember { mutableStateOf("") }
        val keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

        OutlinedTextField(
            modifier = Modifier.width(100.dp),
            value = text,
            label = { Text(text = label) },
            keyboardOptions = keyboardOptions,
            onValueChange = { text = it }
        )
    }

    @Composable
    fun AuditoriumScheme() {
        val imageClipShape = RoundedCornerShape(50.dp)
        val context = LocalContext.current
        val drawableResourceId: Int = context.resources.getIdentifier(
            theatre["scheme"]!!.jsonPrimitive.content,
            "drawable",
            context.packageName
        )
        Image(
            painter = painterResource(id = drawableResourceId),
            contentDescription = "AuditoriumScheme",
            modifier = Modifier
                .clip(imageClipShape)
                .border(2.dp, Color.Green)
        )
    }
}