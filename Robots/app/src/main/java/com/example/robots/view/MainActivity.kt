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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private var drawCount: Int = 0

    private var currentPlayer: Robot = robot1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.score1.text = getString(R.string.robot_1_score, scoreRobot1.toString())
        binding.score2.text = getString(R.string.robot_2_score, scoreRobot2.toString())
        binding.draws.text = getString(R.string.draw_count, drawCount.toString())

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
        adapter.updateItem(robot1.x, robot1.y, 1)
        adapter.updateItem(robot2.x, robot2.y, 2)
        currentPlayer = robot1
        startRound()
    }

    private fun clearBoard() {
        adapter.clear()
    }

    private fun placePrize() {
        prizeX = Random.nextInt(7)
        prizeY = Random.nextInt(7)
        adapter.updateItem(prizeX, prizeY, 3)
    }

    private fun startRound() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val opponent = if (currentPlayer == robot1) robot2 else robot1

                delay(500) // Half-second interval
                val move = currentPlayer.randomMove(opponent.visitedCells)
                if (move != null) {
                    withContext(Dispatchers.Main) {
                        updateBoard(move.first, move.second)
                    }
                    if (currentPlayer.wins(prizeX, prizeY)) {
                        if (currentPlayer == robot1) {
                            scoreRobot1++
                        } else {
                            scoreRobot2++
                        }
                        withContext(Dispatchers.Main) {
                            restartGame()
                        }
                        break
                    }
                }

                currentPlayer = opponent

                if (move == null && !currentPlayer.canMove(opponent.visitedCells)) { // No robot can move - Draw
                    withContext(Dispatchers.Main) {
                        drawCount++
                        restartGame()
                    }
                    break
                }
            }
        }
    }

    private fun restartGame() {
        updateScore()
        clearBoard()
        startGame()
    }

    private fun updateBoard(row: Int, col: Int) {
        // UI update operations go here
        if (currentPlayer == robot1) {
            adapter.updateItem(row, col, 1)
        } else {
            adapter.updateItem(row, col, 2)
        }
    }

    private fun updateScore() {
        binding.draws.text = getString(R.string.draw_count, drawCount.toString())

        if (currentPlayer == robot1) {
            binding.score1.text = getString(R.string.robot_1_score, scoreRobot1.toString())
        } else {
            binding.score2.text = getString(R.string.robot_2_score, scoreRobot2.toString())
        }
    }
}