import java.lang.RuntimeException

enum class validationError {
    None,
    TopBelow10,
    TopLessThanBottom,
    ObjectOnSameLine,
    TopGte99
}
//Consideration: top > bottom
fun isOnSameLine(top: Int, bottom: Int): Boolean = (top%10 == 0).let { lastColumn ->
    (if(lastColumn) top-1 else top).let{ newTop ->
        return (newTop/10 == bottom/10)
    }
}

abstract class BoardObject(val top: Int, val bottom: Int) {
    init {
        this.validate().let {
            when(it) {
                validationError.TopBelow10 -> throw RuntimeException("Invalid Object, as top should be greater than 10")
                validationError.TopLessThanBottom -> throw RuntimeException("Invalid Object, top should be greater than bottom")
                validationError.ObjectOnSameLine -> throw RuntimeException("Invalid Object, top and bottom should be on same line")
                validationError.TopGte99 -> throw RuntimeException("Ladder top or snack mouth should not be more than 99")
                validationError.None -> println("Object has been created successfully")
            }
        }
    }

    open fun validate(): validationError {
        if(top <= 10) return validationError.TopBelow10
        if(top <= bottom) return validationError.TopLessThanBottom
        if(top == 100) return validationError.TopGte99
        if(isOnSameLine(top, bottom)) return validationError.ObjectOnSameLine
        return validationError.None
    }
    abstract fun move(): Int
    abstract fun showMove(): String
}

class Snake(top: Int, bottom: Int) : BoardObject(top, bottom) {
    override fun move(): Int = bottom
    override fun showMove(): String = "Oo!! Snack is Present, You are moving from Place $top to $bottom"
}

class Ladder(top: Int, bottom: Int) : BoardObject(top, bottom) {
    override fun move(): Int = top
    override fun showMove(): String = "Woh!! Ladder is Present, You are moving from Place $bottom to $top"
}