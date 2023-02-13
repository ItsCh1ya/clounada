package ru.chiya.clounada.utils

class Booking {
    var act: String = ""
    var location: String = ""
    var row: Int = 0
    var seats: Int = 0

    constructor(act: String, location: String, row: Int, seats: Int) {
        this.act = act
        this.location = location
        this.row = row
        this.seats = seats
    }

}