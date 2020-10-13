import java.lang.RuntimeException
import java.util.*

enum class boardErrors {
    PlayerSize,
    LadderBottomAndSnakeMouthAtSamePlaceNone,
    SnakeIsPresentAtLadderTop,
    LadderIsPresentAtSnakeTail,
    MaxObject,
    CrookedConfig,
    None
}

fun dice(crooked: Boolean = false): Int {
    var diceVal = Random().nextInt(7)
    while(diceVal == 0 || (crooked && diceVal%2 != 0)) {
        diceVal = Random().nextInt(7)
    }
    return diceVal
}

class Board(val configuration: Configuration) {

    private val boardObjectMap: MutableMap<Int, BoardObject> = mutableMapOf()

    init {
        initialiseMap().let {
            when(it) {
                boardErrors.PlayerSize -> throw RuntimeException("Player range should be 1 to 6")
                boardErrors.MaxObject -> throw RuntimeException("Number of ladders or snakes should not be greater than 10")
                boardErrors.LadderBottomAndSnakeMouthAtSamePlaceNone -> throw RuntimeException("Two Objects can not be start at same place")
                boardErrors.SnakeIsPresentAtLadderTop -> throw RuntimeException("Snake is present at ladder top")
                boardErrors.LadderIsPresentAtSnakeTail -> throw RuntimeException("Ladder is present at snake tail")
                boardErrors.CrookedConfig -> throw RuntimeException("Objects top bottom places should be even number as board configuration is set to crooked dice")
                boardErrors.None -> println("Board has been initialise Successfully")
            }
        }
    }

    private fun initialiseMap(): boardErrors {
        println(configuration)
        if(configuration.players <= 0 || configuration.players > 6) return boardErrors.PlayerSize
        if(configuration.ladders.size > 10 || configuration.snakes.size > 10) return boardErrors.MaxObject
        configuration.ladders.forEach { ladder ->
            println("Processing Ladder: Top ${ladder.top}, Bottom ${ladder.bottom} ")
            if(boardObjectMap.containsKey(ladder.bottom)) return boardErrors.LadderBottomAndSnakeMouthAtSamePlaceNone
            else if(boardObjectMap.containsKey(ladder.top)) return boardErrors.SnakeIsPresentAtLadderTop
            else if(configuration.crookedDice && (ladder.top%2 != 0 || ladder.bottom% 2 != 0)) return boardErrors.CrookedConfig
            else boardObjectMap[ladder.bottom] = Ladder(ladder.top, ladder.bottom)
        }
        configuration.snakes.forEach { snake ->
            println("Processing Snake: Mouth ${snake.top}, Tail ${snake.bottom} ")
            if(boardObjectMap.containsKey(snake.top)) return boardErrors.LadderBottomAndSnakeMouthAtSamePlaceNone
            else if(boardObjectMap.containsKey(snake.bottom)) return boardErrors.LadderIsPresentAtSnakeTail
            else if(configuration.crookedDice && (snake.top%2 != 0 || snake.bottom%2 != 0)) return boardErrors.CrookedConfig
            else boardObjectMap[snake.top] = Snake(snake.top, snake.bottom)
        }
        return boardErrors.None
    }

    private fun getPlayerPositionMap(): MutableMap<Int, Int> {
        val playerPositionMap = mutableMapOf<Int, Int>()
        for (i in 0 until configuration.players) {
            playerPositionMap[i] = 0
        }
        return playerPositionMap
    }

    fun play(): Unit {
        var win = false
        val playerPositionMap = getPlayerPositionMap()
        println(if(configuration.crookedDice) "Board Configuration is set to Crooked Dice" else "")
        while(!win) {
            var i = 0
            while(!win && i < configuration.players) {
                println()
                val diceVal = dice(configuration.crookedDice)
                playerPositionMap[i] = move(i+1, playerPositionMap[i]!!, diceVal)
                if(playerPositionMap[i] == 100) {
                    win = true
                    println("####### Player${i+1} won ############")
                }
                i++
            }
        }
    }

    private fun move(player: Int, oldPosition: Int, diceVal: Int): Int {
        println("Playing Player${player}, is at $oldPosition")
        println("Dice: $diceVal")
        val newPos: Int = oldPosition + diceVal
        if(newPos > 100) {
            println("To win, Player $player required ${100-oldPosition}, Please Try latter")
            return oldPosition
        }
        println("Moving Player$player, from $oldPosition to $newPos")
        if(boardObjectMap.containsKey(newPos)) {
            boardObjectMap[newPos]?.let {
                println(it.showMove())
                return it.move()
            }
        }
        return newPos
    }
}