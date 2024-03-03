package com.example.hw_4_1.ui.home

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_4_1.R
import com.example.hw_4_1.databinding.FragmentHomeBinding
import com.example.hw_4_1.ui.create.TaskCreateFragment
import com.example.hw_4_1.ui.models.Task

class HomeFragment : Fragment() {

    private val adapter = TaskAdapter(this::itemClicked)
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setListeners()
        setupItemTouchHelper()
    }

    private fun itemClicked(id: Long) {
        val task = adapter.currentList.find { it.id == id }
        findNavController().navigate(
            R.id.taskCreateFragment,
            args = bundleOf(
                TaskCreateFragment.TASK_KEY to task
            )
        )
    }

    private fun setupItemTouchHelper() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeItem(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    paint.color = Color.GRAY
                    c.drawRect(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat(),
                        paint
                    )

                    val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
                    val iconMargin = (itemView.height - icon?.intrinsicHeight!!) / 2
                    val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                    val iconBottom = iconTop + icon.intrinsicHeight
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
    }

    private fun removeItem(position: Int) {
        val task = adapter.currentList.getOrNull(position)
        task?.let {
            val currentList = adapter.currentList.toMutableList()
            currentList.remove(it)
            adapter.submitList(currentList)
            Toast.makeText(context, "Task removed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setup() {
        binding.rvTasks.adapter = adapter
    }

    private fun setListeners() {
        binding.btnAddNewTask.setOnClickListener {
            findNavController().navigate(R.id.taskCreateFragment)
        }

        setFragmentResultListener(TaskCreateFragment.RESULT_KEY) { _, bundle ->
            val task =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(
                        TaskCreateFragment.TASK_KEY,
                        Task::class.java
                    )
                } else {
                    bundle.getParcelable(TaskCreateFragment.TASK_KEY)
                }

            val isEdit = bundle.getBoolean(TaskCreateFragment.IS_EDIT_KEY, false)

            var data = mutableListOf<Task>()
            data.addAll(adapter.currentList)

            if (isEdit) {
                data = data.map {
                    if (it.id == task?.id) task
                    else it
                }.toMutableList()
            } else {
                data.add(task ?: return@setFragmentResultListener)
            }
            adapter.submitList(data)
        }
    }
}