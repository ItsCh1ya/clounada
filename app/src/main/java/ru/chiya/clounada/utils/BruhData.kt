package ru.chiya.clounada.utils

import android.content.Context
import kotlinx.serialization.json.*
import ru.chiya.clounada.R
import java.io.*

fun getListOfPlaces(theatre: String): List<String> {
    if (theatre == "TYZ") {
        return listOf<String>("Балкон", "Партер")
    } else if (theatre == "TD"){
        return listOf<String>("Балкон, центр", "Правый балкон", "Левый балкон", "Бенуар, центр", "Правый бенуар", "Левый бенуар", "Бельэтаж, центр", "Правый бельэтаж ", "Левый бельэтаж", "Партер" )
    } else if (theatre == "Circus"){
        return listOf<String>("Правая сторона, 1 сектор", "Правая сторона, 2 сектор", "Правая сторона, 3 сектор","Правая сторона, 4 сектор","Левая сторона, 1 сектор","Левая сторона, 2 сектор", "Левая сторона, 3 сектор","Левая сторона, 4 сектор")
    }
    return listOf<String>("")
}

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
