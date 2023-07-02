package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskViewModel
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        var EdTitle = findViewById<EditText>(R.id.detail_ed_title)
        var EdDesc = findViewById<EditText>(R.id.detail_ed_description)
        var EdDueDate = findViewById<EditText>(R.id.detail_ed_due_date)
        var btnDelete = findViewById<Button>(R.id.btn_delete_task)

        //TODO 11 : Show detail task and implement delete action
        val taskID = getIntent().getIntExtra(TASK_ID, 1)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        detailTaskViewModel.setTaskId(taskID)
        detailTaskViewModel.task.observe(this) {
            if (it != null) {
                EdTitle.setText(it.title)
                EdDesc.setText(it.description)
                EdDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
            }
        }

        btnDelete.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }


    }
}