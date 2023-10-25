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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.robots.R

/**
 * Adapter for the [RecyclerView] in [MainActivity].
 */
class BoardAdapter :
    RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private val items: Array<Array<Int>> = Array(7) { Array(7) { 0 } } // Keep track of item images

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById(R.id.boardPosition)
    }

    override fun getItemCount(): Int {
        return items.size * items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        // You can load images into the ImageViews here based on the position.
        // For simplicity, I'll use placeholder images.
        val row = position / 7
        val col = position % 7

        // Set the appropriate image for the item
        when (items[row][col]) {
            1 -> holder.imageView.setImageResource(R.drawable.ic_blue_circle)
            2 -> holder.imageView.setImageResource(R.drawable.ic_red_circle)
            3 -> holder.imageView.setImageResource(R.drawable.ic_yellow_circle)
            else -> holder.imageView.setImageResource(R.drawable.ic_gray_circle)
        }
    }

    fun updateItem(row: Int, col: Int, robot: Int) {
        items[row][col] = robot
        notifyItemChanged(row * 7 + col)
    }

    fun clear() {
        for (i in items.indices) {
            for (j in items[i].indices) {
                if (items[i][j] != 0) {
                    items[i][j] = 0
                    notifyItemChanged(i * 7 + j)
                }
            }
        }
    }
}