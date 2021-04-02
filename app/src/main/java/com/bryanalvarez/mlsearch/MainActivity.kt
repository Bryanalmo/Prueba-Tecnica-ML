package com.bryanalvarez.mlsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * function to display an snackBar with the error, it's called from different fragments
     * @param parent fragment view
     * @param message error message to be displayed
     */
    fun showError(parent: View, message: String?) {
        val error = message ?: "Ocurrió un error, intentalo nuevamente"
        val snackBar = Snackbar.make(parent, error, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
        snackBar.show()
    }
}