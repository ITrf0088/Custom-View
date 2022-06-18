package org.rasulov.customviews

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.R
import com.google.android.material.button.MaterialButton
import org.rasulov.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val token = Any()
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomButtons.setListener {
            if (it == ButtonType.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Update cancel")
            } else if (it == ButtonType.POSITIVE) {
                binding.bottomButtons.setPositiveButtonText("Update OK")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(token)
    }
}