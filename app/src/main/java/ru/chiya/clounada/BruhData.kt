package ru.chiya.clounada

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

fun readRawTextFile(ctx: Context, resId: Int): String? {
    val inputStream: InputStream = ctx.getResources().openRawResource(resId)
    val byteArrayOutputStream = ByteArrayOutputStream()
    var i: Int
    try {
        i = inputStream.read()
        while (i != -1) {
            byteArrayOutputStream.write(i)
            i = inputStream.read()
        }
        inputStream.close()
    } catch (e: IOException) {
        return null
    }
    return byteArrayOutputStream.toString()
}
class BruhData(ctx: Context) {
    private val fileContent = readRawTextFile(ctx = ctx, resId = R.raw.preset)
    private val obj = Json.parseToJsonElement(fileContent.toString())
    fun getEntireJson(): JsonElement? {
        return obj
    }
}