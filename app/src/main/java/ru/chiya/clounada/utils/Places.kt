package ru.chiya.clounada.utils

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


