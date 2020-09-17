package com.example.kotlin.comon

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.kotlin.R

import com.example.kotlin.data.entity.Note.Color
import java.text.SimpleDateFormat
import java.util.*


const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"

fun Date.format(): String =
    SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        .format(this)

fun Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, getColorRes()
    )


fun Color.getColorRes(): Int =
    when (this) {
        Color.WHITE -> R.color.white
        Color.YELLOW -> R.color.yellow
        Color.GREEN -> R.color.green
        Color.BLUE -> R.color.blue
        Color.RED -> R.color.red
        Color.VIOLET -> R.color.violet
    }

