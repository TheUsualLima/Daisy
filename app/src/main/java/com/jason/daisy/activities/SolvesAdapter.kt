package com.jason.daisy.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.daisy.R

class SolvesAdapter : RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount() = 9

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.solve_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }


}

class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}