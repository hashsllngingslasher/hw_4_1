package com.example.hw_4_1.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title: String,
    val id: Long
) : Parcelable
