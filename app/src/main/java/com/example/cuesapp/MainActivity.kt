package com.example.cuesapp

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputText: TextInputEditText
    private lateinit var ivMoodIllustration: ImageView
    private lateinit var btnNext: Button
    private lateinit var moodButtons: List<MaterialButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputLayout = findViewById(R.id.inputLayout)
        inputText = findViewById(R.id.inputText)
        ivMoodIllustration = findViewById(R.id.ivMoodIllustration)
        btnNext = findViewById(R.id.btnNext)

        // Force input to be lowercase only
        inputText.filters = arrayOf(InputFilter { source, start, end, _, _, _ ->
            source?.subSequence(start, end).toString().lowercase()
        })

        val validTimes = listOf("morning", "afternoon", "evening", "night")

        // Error handling for input as the user types
        inputText.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.trim() ?: ""
            when {
                input.isEmpty() -> {
                    inputLayout.error = null
                    inputLayout.endIconDrawable = null
                }
                input !in validTimes -> {
                    inputLayout.error = getString(R.string.error_invalid_time)
                    inputLayout.endIconDrawable = null
                }
                else -> {
                    inputLayout.error = null
                    inputLayout.setEndIconDrawable(R.drawable.ic_check_green)
                    inputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
                }
            }
        }

        // Logic for mood button selection and illustration update
        moodButtons = listOf(
            findViewById(R.id.btnEnergetic),
            findViewById(R.id.btnCalm),
            findViewById(R.id.btnLowEnergy),
            findViewById(R.id.btnSad)
        )

        moodButtons.forEach { button ->
            button.setOnClickListener {
                // Deselect all others
                moodButtons.forEach { it.isSelected = false }
                // Select the clicked one
                button.isSelected = true

                // Fade animation logic
                val fadeOut = AlphaAnimation(1.0f, 0.0f)
                fadeOut.duration = 200

                val fadeIn = AlphaAnimation(0.0f, 1.0f)
                fadeIn.duration = 500

                fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                    override fun onAnimationStart(animation: android.view.animation.Animation?) {}
                    override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                    override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                        // Update illustration based on selection
                        when (button.id) {
                            R.id.btnEnergetic -> ivMoodIllustration.setImageResource(R.drawable.cloud_energetic)
                            R.id.btnCalm -> ivMoodIllustration.setImageResource(R.drawable.cloud_calm)
                            R.id.btnLowEnergy -> ivMoodIllustration.setImageResource(R.drawable.cloud_low_energy)
                            R.id.btnSad -> ivMoodIllustration.setImageResource(R.drawable.cloud_sad)
                        }
                        ivMoodIllustration.startAnimation(fadeIn)
                    }
                })

                ivMoodIllustration.startAnimation(fadeOut)
            }
        }

        // Validate everything on "Next" Button Click
        btnNext.setOnClickListener {
            val input = inputText.text.toString().trim()
            val anyMoodSelected = moodButtons.any { it.isSelected }

            when {
                input.isEmpty() -> {
                    inputLayout.error = getString(R.string.error_empty)
                    inputLayout.endIconDrawable = null
                }
                input !in validTimes -> {
                    inputLayout.error = getString(R.string.error_invalid_time)
                    inputLayout.endIconDrawable = null
                }
                !anyMoodSelected -> {
                    Toast.makeText(this, getString(R.string.toast_select_mood), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    inputLayout.error = null
                    inputLayout.setEndIconDrawable(R.drawable.ic_check_green)
                    inputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
                    
                    // Determine which mood was selected
                    val selectedMood = when {
                        findViewById<MaterialButton>(R.id.btnEnergetic).isSelected -> "energetic"
                        findViewById<MaterialButton>(R.id.btnCalm).isSelected -> "calm"
                        findViewById<MaterialButton>(R.id.btnLowEnergy).isSelected -> "low_energy"
                        findViewById<MaterialButton>(R.id.btnSad).isSelected -> "sad"
                        else -> ""
                    }

                    // Navigate to SuggestionsActivity
                    val intent = Intent(this, SuggestionsActivity::class.java).apply {
                        putExtra("SELECTED_TIME", input)
                        putExtra("SELECTED_MOOD", selectedMood)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        // Check if we should reset (default to true when coming back via CLEAR_TOP/SINGLE_TOP)
        resetUI()
    }

    private fun resetUI() {
        inputText.text?.clear()
        inputLayout.error = null
        inputLayout.endIconDrawable = null
        moodButtons.forEach { it.isSelected = false }
        ivMoodIllustration.setImageResource(R.drawable.cloud_pick)
    }
}
