package com.idz.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.gridlayout.widget.GridLayout
import com.idz.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    private var gridLayout: GridLayout? = null
    private var playersTurn: TextView? = null
    private var winner: TextView? = null
    private var current_player: Char = 'X'
    private var play_again_button: Button? = null

    private var board = Array(3) { CharArray(3 ) { ' ' } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        gridLayout = findViewById<GridLayout>(R.id.grid_layout)
        playersTurn = findViewById<TextView>(R.id.players_turn_text)
        winner = findViewById<TextView>(R.id.winner_text)
        play_again_button = findViewById<Button>(R.id.play_again_button)

        play_again_button?.isEnabled = false
        playersTurn?.text = "It's ${current_player}'s turn"
        setBoard()
    }

    fun setBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val id = resources.getIdentifier(
                    "button_${i}_${j}",
                    "id",
                    packageName
                )
                val currentButton: Button = findViewById<Button>(id)
                currentButton.setOnClickListener {
                    playATurn(currentButton, i, j)
                }
            }
        }
    }

    fun playATurn(currentButton: Button, row: Int, col: Int) {
        currentButton.text = "${current_player}"
        board[row][col] = current_player

        if (checkForAWin(row, col)) {
            winner?.text = "${current_player} wins!"
            disableBoard()
            play_again_button?.isEnabled = true
            play_again_button?.setOnClickListener {
                startANewGame()
            }
        } else {
            if (checkForADraw()) {
                winner?.text = "It's a draw!"
                disableBoard()
                play_again_button?.isEnabled = true
                play_again_button?.setOnClickListener {
                    startANewGame()
                }
            } else {
                if (current_player == 'X') {
                    current_player = 'O'
                } else {
                    current_player = 'X'
                }
                playersTurn?.text = "It's ${current_player}'s turn"
            }
        }
    }

    fun checkForAWin(row: Int, col: Int): Boolean {
        if ((0..2).all { board[row][it] == current_player }) return true
        if ((0..2).all { board[it][col] == current_player }) return true

        if (row == col && (0..2).all { board[it][it] == current_player }) return true
        if (row + col == 2 && (0..2).all { board[it][2 - it] == current_player }) return true

        return false
    }

    fun checkForADraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                val id = resources.getIdentifier(
                    "button_${i}_${j}",
                    "id",
                    packageName
                )
                val currentButton: Button = findViewById<Button>(id)
                if (currentButton.text.isNullOrEmpty()) {
                    return false
                }
            }
        }

        return true
    }

    fun disableBoard(){
        for (i in 0..2) {
            for (j in 0..2) {
                val id = resources.getIdentifier(
                    "button_${i}_${j}",
                    "id",
                    packageName
                )
                val currentButton: Button = findViewById<Button>(id)
                currentButton.isEnabled = false
            }
        }
    }

    fun cleanBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val id = resources.getIdentifier(
                    "button_${i}_${j}",
                    "id",
                    packageName
                )
                val currentButton: Button = findViewById<Button>(id)
                currentButton.isEnabled = true
                currentButton.text = ""
                board[i][j] = ' '
            }
        }
    }

    fun startANewGame() {
        cleanBoard()
        play_again_button?.isEnabled = false
        current_player = 'X'
        winner?.text = ""
        playersTurn?.text = "It's ${current_player}'s turn"
        setBoard()
    }
}