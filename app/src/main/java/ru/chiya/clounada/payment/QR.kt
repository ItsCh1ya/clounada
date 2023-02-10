package ru.chiya.clounada.payment

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


@Throws(WriterException::class)
@Composable
fun encodeAsBitmap(str: String?): Bitmap? {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400)
    val w = bitMatrix.width
    val h = bitMatrix.height
    val pixels = IntArray(w * h)
    for (y in 0 until h) {
        for (x in 0 until w) {
            pixels[y * w + x] = if (bitMatrix[x, y]) MaterialTheme.colorScheme.surface.toArgb() else MaterialTheme.colorScheme.onSurface.toArgb()
        }
    }
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
    return bitmap
}