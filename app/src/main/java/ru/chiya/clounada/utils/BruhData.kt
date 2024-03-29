package ru.chiya.clounada.utils

import android.content.Context
import kotlinx.serialization.json.*
import ru.chiya.clounada.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

fun readRawTextFile(ctx: Context, resId: Int): String? {
    val inputStream: InputStream = ctx.resources.openRawResource(resId)
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

open class BruhData(ctx: Context) {
    private val fileContent = readRawTextFile(ctx = ctx, resId = R.raw.preset)
    private val obj = Json.parseToJsonElement(fileContent.toString())
    fun getEntireJson(): JsonElement {
        return obj
    }

    fun getActionByIndex(theatreName: String, index: Int): JsonElement {
        return obj.jsonObject[theatreName]!!.jsonObject["actions"]!!.jsonArray[index]
    }

    fun getValue(jsonObject: JsonObject, key: String): String {
        return jsonObject[key]!!.jsonPrimitive.content
    }
}
