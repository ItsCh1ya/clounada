package ru.chiya.clounada

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class TheatreTab {
    val imageShape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CircusCards(){
        TabScreen(){
            LazyVerticalGrid(columns = GridCells.Adaptive(200.dp),
            content = {
                items(16){ i ->
                    Card(modifier = Modifier.padding(8.dp), backgroundColor = MaterialTheme.colorScheme.surfaceVariant, onClick = { /*TODO*/ }, content = {
                        Column() {
                            Image(painter = painterResource(id = R.drawable.preview_card_image), contentDescription = "", Modifier.clip(imageShape))
                            Text(text = "КороЛЕВство",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    })
                }
            })
        }
    }
}