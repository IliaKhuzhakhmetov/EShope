package ru.don.eshope.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun createSnackBar(root: View, @StringRes msg: Int) =
    Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()

fun createSnackBar(root: View, msg: String) =
    Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()