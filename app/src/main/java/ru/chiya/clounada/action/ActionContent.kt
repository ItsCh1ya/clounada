package ru.chiya.clounada.action

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.chiya.clounada.payment.PaymentActivity

@Composable
fun ActionContent(
    drawableResourceId: Int,
    description: String,
    context: Context,
    theatreName: String,
    actionIndex: Int
) {
    Image(
        painter = painterResource(id = drawableResourceId),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .clip(MaterialTheme.shapes.extraLarge)

    )
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 16.dp)
    )
    Button(onClick = {
        val intent = Intent(context, PaymentActivity::class.java)
        intent.putExtra("theatreName", theatreName)
        intent.putExtra("actionIndex", actionIndex)
        context.startActivity(intent)
    }) {
        Text(
            "Купить",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
