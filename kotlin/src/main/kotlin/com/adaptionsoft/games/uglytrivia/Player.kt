package com.adaptionsoft.games.uglytrivia

data class Player(val name: String) {

    var inPenaltyBox = false
    var goldCoins = 0

    override fun toString(): String {
        return name
    }
}