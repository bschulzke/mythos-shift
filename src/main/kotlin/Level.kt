class Level(x: Int, y: Int, z: Int, w: Int, level: Int) {

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

