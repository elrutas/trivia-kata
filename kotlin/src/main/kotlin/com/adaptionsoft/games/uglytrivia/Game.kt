package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.trivia.runner.Printer
import java.util.*

class Game {
    var players = ArrayList<Any>()
    var places = IntArray(6)
    var purses = IntArray(6)
    var inPenaltyBox = BooleanArray(6)

    var popQuestions = LinkedList<Any>()
    var scienceQuestions = LinkedList<Any>()
    var sportsQuestions = LinkedList<Any>()
    var rockQuestions = LinkedList<Any>()

    var currentPlayer = 0
    var isGettingOutOfPenaltyBox: Boolean = false
    
    init {
        for (i in 0..49) {
            popQuestions.addLast(createPopQuestion(i))
            scienceQuestions.addLast(createScienceQuestion(i))
            sportsQuestions.addLast(createSportsQuestion(i))
            rockQuestions.addLast(createRockQuestion(i))
        }
    }

    private fun createPopQuestion(index: Int) = "Pop Question " + index

    private fun createScienceQuestion(index: Int) = "Science Question " + index

    private fun createSportsQuestion(index: Int) = "Sports Question " + index

    private fun createRockQuestion(index: Int) = "Rock Question " + index

    fun isPlayable(): Boolean {
        return howManyPlayers() >= 2
    }

    fun add(playerName: String): Boolean {

        players.add(playerName)
        places[howManyPlayers()] = 0
        purses[howManyPlayers()] = 0
        inPenaltyBox[howManyPlayers()] = false

        Printer.println(playerName + " was added")
        Printer.println("They are player number " + players.size)
        return true
    }

    fun howManyPlayers(): Int {
        return players.size
    }

    fun roll(roll: Int) {
        Printer.println(players[currentPlayer].toString() + " is the current player")
        Printer.println("They have rolled a " + roll)

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true

                Printer.println(players[currentPlayer].toString() + " is getting out of the penalty box")
                places[currentPlayer] = places[currentPlayer] + roll
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12

                Printer.println(players[currentPlayer].toString()
                        + "'s new location is "
                        + places[currentPlayer])
                Printer.println("The category is " + currentCategory())
                askQuestion()
            } else {
                Printer.println(players[currentPlayer].toString() + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12

            Printer.println(players[currentPlayer].toString()
                    + "'s new location is "
                    + places[currentPlayer])
            Printer.println("The category is " + currentCategory())
            askQuestion()
        }

    }

    private fun askQuestion() {
        when (currentCategory()) {
            "Pop" -> popQuestions.removeFirst()
            "Science" -> scienceQuestions.removeFirst()
            "Sports" -> sportsQuestions.removeFirst()
            "Rock" -> rockQuestions.removeFirst()
        }
    }

    private fun currentCategory(): String {
        if (places[currentPlayer] == 0) return "Pop"
        if (places[currentPlayer] == 4) return "Pop"
        if (places[currentPlayer] == 8) return "Pop"
        if (places[currentPlayer] == 1) return "Science"
        if (places[currentPlayer] == 5) return "Science"
        if (places[currentPlayer] == 9) return "Science"
        if (places[currentPlayer] == 2) return "Sports"
        if (places[currentPlayer] == 6) return "Sports"
        if (places[currentPlayer] == 10) return "Sports"
        return "Rock"
    }

    fun wasCorrectlyAnswered(): Boolean {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                Printer.println("Answer was correct!!!!")
                purses[currentPlayer]++
                Printer.println(players[currentPlayer].toString()
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.")

                val winner = didPlayerWin()

                moveTurnToNextPlayer()

                return winner
            } else {
                moveTurnToNextPlayer()
                return true
            }


        } else {

            Printer.println("Answer was correct!!!!")
            purses[currentPlayer]++
            Printer.println(players[currentPlayer].toString()
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.")

            val winner = didPlayerWin()
            moveTurnToNextPlayer()

            return winner
        }
    }

    fun wrongAnswer(): Boolean {
        Printer.println("Question was incorrectly answered")
        Printer.println(players[currentPlayer].toString() + " was sent to the penalty box")
        inPenaltyBox[currentPlayer] = true

        moveTurnToNextPlayer()
        return true
    }

    private fun moveTurnToNextPlayer() {
        currentPlayer++
        if (currentPlayer == players.size) currentPlayer = 0
    }

    private fun didPlayerWin(): Boolean {
        return purses[currentPlayer] != 6
    }
}
