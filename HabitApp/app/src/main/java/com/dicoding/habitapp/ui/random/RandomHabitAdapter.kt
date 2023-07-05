package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view

        fun bind(pageType: PageType, pageData: Habit) {
            val title = itemView.findViewById<TextView>(R.id.tv_random_title)
            val timeStart = itemView.findViewById<TextView>(R.id.tv_random_time_start)
            val imgLabel = itemView.findViewById<ImageView>(R.id.imgPriority)
            val timeDuration = itemView.findViewById<TextView>(R.id.tv_random_minutes)
            val btnStart = itemView.findViewById<TextView>(R.id.btn_random_cd)

            title.text = pageData.title
            timeStart.text = pageData.startTime

            when (pageType) {
                PageType.HIGH -> {
                    imgLabel.setImageResource(R.drawable.ic_priority_high)
                }
                PageType.MEDIUM -> {
                    imgLabel.setImageResource(R.drawable.ic_priority_medium)
                }
                else -> {
                    imgLabel.setImageResource(R.drawable.ic_priority_low)
                }
            }

            timeDuration.text = pageData.minutesFocus.toString()
            btnStart.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
