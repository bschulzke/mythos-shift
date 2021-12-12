class Moveables(internal val moveables: List<Moveable>, ) {

    fun contains(coordinates: Coordinates): Boolean {
        for (moveable in moveables) {
            if (moveable.isInRoom(coordinates)) {
                return true
            }
        }
        return false
    }

    fun find(coordinates: Coordinates): Moveable? {
        for (moveable in moveables) {
            if (moveable.isInRoom(coordinates)) {
                return moveable
            }
        }
        return null
    }

}