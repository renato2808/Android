/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.robots.view

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.robots.R
import com.example.robots.databinding.ActivityMainBinding
import com.example.robots.model.Robot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
open class MainActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BoardAdapter
    private lateinit var binding: ActivityMainBinding

    private var robot1: Robot = Robot(0, 0)
    private var robot2: Robot = Robot(6, 6)
    private var prizeX: Int = 0
    private var prizeY: Int = 0
    private var scoreRobot1: Int = 0
    private var scoreRobot2: Int = 0

    private var currentPlayer: Robot = robot1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.score1.text = getString(R.string.robot_1, scoreRobot1.toString())
        binding.score2.text = getString(R.string.robot_2, scoreRobot2.toString())

        recyclerView = binding.recyclerView
        // Sets the LinearLayoutManager of the recyclerview
        adapter = BoardAdapter()
        recyclerView.adapter = adapter
        startGame()
    }

    private fun startGame() {
        placePrize()
        robot1 = Robot(0, 0)
        robot2 = Robot(6, 6)
        currentPlayer = robot1
        updateScore()
        startRound()
    }

    private fun clearBoard() {
        adapter.clear()
    }

    private fun placePrize() {
        prizeX = Random.nextInt(7)
        prizeY = Random.nextInt(7)
        binding.prizePosition.text = getString(R.string.prize, "$prizeX $prizeY")
    }

    private fun startRound() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                delay(500) // Half-second interval
                if (currentPlayer.canMove()) {
                    val move = currentPlayer.randomMove()
                    updateBoard(move.first, move.second)
                    if (currentPlayer.wins(prizeX, prizeY)) {
                        restartGame()
                        break
                    }
                    currentPlayer = if (currentPlayer == robot1) robot2 else robot1
                }
            }
        }
    }

    private fun restartGame() {
        GlobalScope.launch(Dispatchers.Main) {
            clearBoard()
            startGame()
        }
    }

    private fun updateBoard(row: Int, col: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            // UI update operations go here
            if (currentPlayer == robot1) {
                adapter.updateItem(row, col, 1)
            } else {
                adapter.updateItem(row, col, 2)
            }
        }
    }

    private fun updateScore() {
        if (currentPlayer == robot1) {
            scoreRobot1++
            binding.score1.text = getString(R.string.robot_1, scoreRobot1.toString())
        } else {
            scoreRobot2++
            binding.score2.text = getString(R.string.robot_2, scoreRobot2.toString())
        }
    }
}