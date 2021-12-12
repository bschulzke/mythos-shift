class Level(x: Int, y: Int, z: Int, w: Int, level: Int, start: Coordinates, boxes: Moveables? = null, tesseracts: Moveables? = null) {

    val start = start

    val boxes = boxes

    val tesseracts = tesseracts

    val rooms = Array(x) {
        Array(y) {
            Array(z) {
                Array<MazeRoom?>(w) {
                    null
                }
            }
        }
    }

}

