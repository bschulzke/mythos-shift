class Boxes(val boxes: List<Box>, ) {

    fun contains(coordinates: Coordinates): Boolean {
        for (box in boxes) {
            if (box.coordinates == coordinates) {
                return true
            }
        }
        return false
    }

    fun find(coordinates: Coordinates): Box? {
        for (box in boxes) {
            if (box.coordinates == coordinates) {
                return box
            }
        }
        return null
    }

}