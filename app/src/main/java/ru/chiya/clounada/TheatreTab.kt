package ru.chiya.clounada

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

class TheatreTab {
    val imageShape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CircusCards(){
        TabScreen(){
            val context = LocalContext.current
            LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
            content = {
                items(5){ i ->
                    Card(modifier = Modifier.padding(8.dp), backgroundColor = MaterialTheme.colorScheme.surfaceVariant, onClick = {
                        val navigate = Intent(context, ActionActivity::class.java)
                        val opts = Bundle()
                        opts.putString("Title", "КороЛЕВство")

                        startActivity(context, navigate, opts)
                    }, content = {
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