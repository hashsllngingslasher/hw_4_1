package com.example.hw_4_1.ui.create

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.hw_4_1.databinding.FragmentTaskCreateBinding
import com.example.hw_4_1.ui.models.Task

class TaskCreateFragment : Fragment() {

    private lateinit var binding: FragmentTaskCreateBinding
    private var task: Task? = null
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        getTask()
    }

    private fun getTask() {
        val args = arguments ?: return
        task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            args.getParcelable(
                TASK_KEY, Task::class.java
            ) ?: return
        } else {
            args.getParcelable(TASK_KEY) ?: return
        }
        binding.etTaskTitle.setText(task?.title.toString())
        isEdit = true
        binding.btnAddTask.text = "Save"
    }

    private fun setListeners() {
        binding.btnAddTask.setOnClickListener {
            if (binding.etTaskTitle.text.toString().isNotBlank()) {
                val task = if (task == null) createTask() else updateTask()
                saveTask(task)
            }
        }
    }

    private fun updateTask(): Task {
        return task!!.copy(
            title = binding.etTaskTitle.text.toString()
        )
    }

    private fun saveTask(task: Task) {
        setFragmentResult(
            RESULT_KEY, bundleOf(
                TASK_KEY to task, IS_EDIT_KEY to isEdit
            )
        )

        Toast.makeText(context, "Task added!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun createTask(): Task {
        return Task(
            title = binding.etTaskTitle.text.toString(), id = System.currentTimeMillis()
        )
    }

    companion object {
        const val RESULT_KEY = "FRAGMENT_TASK_CREATE_RESULT_KEY"
        const val TASK_KEY = "TASK_KEY"
        const val IS_EDIT_KEY = "IS_EDIT_KEY"
    }
}