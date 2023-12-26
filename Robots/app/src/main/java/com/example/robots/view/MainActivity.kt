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
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.robots.R
import com.example.robots.databinding.ActivityMainBinding
import com.example.robots.model.Robot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

open class MainActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BoardAdapter
    private lateinit var binding: ActivityMainBinding
    private var secondsCounter = 0
    private var moveRobotsJob: Job? = null

    private var robot1: Robot = Robot(0, 0)
    private var robot2: Robot = Robot(6, 6)
    private var prizeX: Int = 0
    private var prizeY: Int = 0
    private var scoreRobot1: Int = 0
    private var scoreRobot2: Int = 0
    private var drawCount: Int = 0

    private var currentPlayer: Robot = robot1
    private var startedWithRobot1: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView

        val handler = Handler(Looper.getMainLooper())
        val timerRunnable = object : Runnable {
            override fun run() {
                val totalSeconds = secondsCounter
                val hours = totalSeconds / 3600
                val minutes = (totalSeconds % 3600) / 60
                val seconds = totalSeconds % 60
                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                secondsCounter++
                binding.timer.text = formattedTime
                handler.postDelayed(this, 1000) // Update every second (1000 milliseconds)
            }
        }
        adapter = BoardAdapter()
        recyclerView.adapter = adapter

        resetScores()
        startGame()
        handler.postDelayed(timerRunnable, 1000)
    }

    private fun resetScores() {
        binding.score1.text = getString(R.string.robot_1_score, "0")
        binding.score2.text = getString(R.string.robot_2_score, "0")
        binding.drawsCounter.text = getString(R.string.draw_count, "0")
    }

    private fun startGame() {
        placeRobots()
        currentPlayer = if (startedWithRobot1) robot2 else robot1
        startedWithRobot1 = currentPlayer == robot1
        placePrize()
        startRound()
    }

    private fun clearBoard() {
        adapter.clear()
    }

    private fun placeRobots() {
        robot1 = Robot(0, 0)
        robot2 = Robot(6, 6)
        adapter.moveRobot1(robot1.x, robot1.y)
        adapter.moveRobot2(robot2.x, robot2.y)
    }

    private fun placePrize() {
        prizeX = Random.nextInt(7) // Cannot place prize on (0,0) or (6, 6), initial robots positions
        prizeY = when (prizeX) {
            6 -> {
                Random.nextInt(6)
            }
            0 -> {
                Random.nextInt(1, 7)
            }
            else -> {
                Random.nextInt(7)
            }
        }
        adapter.placePrize(prizeX, prizeY)
    }

    private fun startRound() {
        moveRobotsJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(500) // Half-second interval

                val otherRobot = if (currentPlayer == robot1) robot2 else robot1
                val move = currentPlayer.randomMove(otherRobot.visitedCells)

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
                            restartRound()
                        }
                        break
                    }

                    if (otherRobot.canMove(currentPlayer.visitedCells)) {
                        currentPlayer = otherRobot
                    }
                } else if (!otherRobot.canMove(currentPlayer.visitedCells)) { // No robot can move - Draw
                    withContext(Dispatchers.Main) {
                        drawCount++
                        restartRound()
                    }
                    break
                } else {
                    currentPlayer = otherRobot
                }
            }
        }
    }

    private fun restartRound() {
        moveRobotsJob?.cancel()
        updateScore()
        clearBoard()
        startGame()
    }

    private fun updateBoard(row: Int, col: Int) {
        if (currentPlayer == robot1) {
            adapter.moveRobot1(row, col)
        } else {
            adapter.moveRobot2(row, col)
        }
    }

    private fun updateScore() {
        binding.drawsCounter.text = getString(R.string.draw_count, drawCount.toString())

        if (currentPlayer == robot1) {
            binding.score1.text = getString(R.string.robot_1_score, scoreRobot1.toString())
        } else {
            binding.score2.text = getString(R.string.robot_2_score, scoreRobot2.toString())
        }
    }

    fun restartGame(view: View) {
        secondsCounter = 0
        scoreRobot1 = 0
        scoreRobot2 = 0
        drawCount = 0
        resetScores()
        restartRound()
    }
}