package com.adaptionsoft.games.uglytrivia

data class Player(val name: String) {

    var inPenaltyBox = false

    override fun toString(): String {
        return name
    }
}