fun main() {
    var parkingLot = ParkingLot(0)

    while (true) {
        val input = readLine()!!.split(" ")
        when {
            input.first() == "exit" -> break
            input.first() == "create" -> parkingLot = ParkingLot(input[1].toInt())
            parkingLot.size == 0 -> println("Sorry, a parking lot has not been created.")
            input.first() == "park" -> parkingLot.park(input[1], input[2])
            input.first() == "leave" -> parkingLot.leave(input[1].toInt())
            input.first() == "status" -> println(parkingLot)
            input.first() == "reg_by_color" -> parkingLot.regByColor(input[1])
            input.first() == "spot_by_color" -> parkingLot.spotByColor(input[1])
            input.first() == "spot_by_reg" -> parkingLot.spotByReg(input[1])
        }
    }
}

class ParkingLot(val size: Int) {
    private val listOfParkingSpot = MutableList(size) { ParkingSpot(it + 1) }

    data class Car(val plate: String, val color: String) {
        override fun toString(): String {
            return "$plate $color"
        }
    }

    data class ParkingSpot(val spotNum: Int, var isOccupied: Boolean = false, var car: Car? = null) {
        override fun toString(): String {
            return "$spotNum $car"
        }
    }

    override fun toString(): String {
        val string = listOfParkingSpot
            .filter { it.isOccupied }
            .joinToString("\n") { it.toString() }

        return when {
            listOfParkingSpot.size == 0 -> "Sorry, a parking lot has not been created."
            string.isEmpty() -> "Parking lot is empty."
            else -> string
        }
    }

    init {
        if (size != 0) println("Created a parking lot with $size spots.")
    }

    fun park(plate: String, color: String) {
        val car = Car(plate, color)

        listOfParkingSpot.firstOrNull { !it.isOccupied }.let {
            if (it == null) {
                println("Sorry, the parking lot is full.")
            } else {
                it.isOccupied = true
                it.car = car
                println("$color car parked in spot ${it.spotNum}.")
            }
        }
    }

    fun leave(spot: Int) {
        val parkingSpot = listOfParkingSpot[spot - 1]
        if (parkingSpot.isOccupied) {
            parkingSpot.isOccupied = false
            parkingSpot.car = null

            println("Spot $spot is free.")
        } else {
            println("There is no car in spot $spot.")
        }
    }

    fun regByColor(color: String) {
        println(
            listOfParkingSpot
                .filter { it.car?.color.equals(color, true) }
                .joinToString(", ") { it.car!!.plate }
                .ifEmpty { "No cars with color $color were found." }
        )
    }

    fun spotByColor(color: String) {
        println(
            listOfParkingSpot
                .filter { it.car?.color.equals(color, true) }
                .map { it.spotNum }
                .joinToString(", ")
                .ifEmpty { "No cars with color $color were found." }
        )
    }

    fun spotByReg(plate: String) {
        println(
            listOfParkingSpot
                .filter { it.car?.plate.equals(plate, true) }
                .map { it.spotNum }
                .joinToString(", ")
                .ifEmpty { "No cars with registration number $plate were found." }
        )
    }
}
