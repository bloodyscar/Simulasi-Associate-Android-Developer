package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import java.util.*

class AddActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var startTime: TextView
    private lateinit var endTime: TextView
    private var selectedDayNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val fact = ListViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, fact)[AddCourseViewModel::class.java]

        val nameCourse = findViewById<EditText>(R.id.nameCourse)
        val nameNote = findViewById<EditText>(R.id.nameNote)
        startTime = findViewById<TextView>(R.id.startTime)
        endTime = findViewById<TextView>(R.id.endTime)
        val lecture = findViewById<EditText>(R.id.nameLecture)
        val hari = findViewById<Spinner>(R.id.hari)
        val timeStart = findViewById<ImageButton>(R.id.timeStart)
        val timeEnd = findViewById<ImageButton>(R.id.timeEnd)

        val adapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hari.adapter = adapter
        hari.onItemSelectedListener = this


        timeStart.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, "timePickerStart")
        }

        timeEnd.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, "timePickerEnd")
        }

        var btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            viewModel.insertCourse(
                nameCourse.text.toString(),
                selectedDayNumber,
                startTime.text.toString(),
                endTime.text.toString(),
                lecture.text.toString(),
                nameNote.text.toString()
            )
            this.onBackPressed()

        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        when (tag) {
            "timePickerStart" -> {
                val selectedStartTime = String.format("%02d:%02d", hour, minute)
                startTime.text = selectedStartTime
                // Perform any further actions with the selected start time
            }
            "timePickerEnd" -> {
                val selectedEndTime = String.format("%02d:%02d", hour, minute)
                endTime.text = selectedEndTime
                // Perform any further actions with the selected end time
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedDayName = parent?.getItemAtPosition(position).toString()
        selectedDayNumber = getDayNumber(selectedDayName)
        Toast.makeText(this, selectedDayNumber.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun getDayNumber(dayName: String): Int {
        return when (dayName) {
            "Monday" -> Calendar.MONDAY
            "Tuesday" -> Calendar.TUESDAY
            "Wednesday" -> Calendar.WEDNESDAY
            "Thursday" -> Calendar.THURSDAY
            "Friday" -> Calendar.FRIDAY
            "Saturday" -> Calendar.SATURDAY
            "Sunday" -> Calendar.SUNDAY
            else -> Calendar.MONDAY
        }
    }

}