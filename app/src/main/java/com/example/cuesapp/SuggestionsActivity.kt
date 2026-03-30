package com.example.cuesapp

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SuggestionsActivity : AppCompatActivity() {

    private var isFrontVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggestions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Access the views from the included layout
        val cardContainer = findViewById<FrameLayout>(R.id.cardContainer)
        val cardFront = findViewById<LinearLayout>(R.id.cardFront)
        val cardBack = findViewById<LinearLayout>(R.id.cardBack)
        val tvEnjoyDay = findViewById<TextView>(R.id.tvEnjoyDay)

        // camera distance
        val scale = resources.displayMetrics.density
        cardFront.cameraDistance = 10000 * scale
        cardBack.cameraDistance = 10000 * scale

        cardContainer.setOnClickListener {
            flipCard(cardFront, cardBack)
        }
        
        // Retrieve data from intent
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: ""
        val selectedMood = intent.getStringExtra("SELECTED_MOOD") ?: ""
        
        // Update greeting based on time
        val greeting = when (selectedTime.lowercase()) {
            "morning" -> getString(R.string.enjoy_morning)
            "afternoon" -> getString(R.string.enjoy_afternoon)
            "evening" -> getString(R.string.enjoy_evening)
            "night" -> getString(R.string.enjoy_night)
            else -> getString(R.string.enjoy_your_day)
        }
        tvEnjoyDay.text = greeting

        // Set listener to go back to MainActivity
        tvEnjoyDay.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Clear the activity stack so MainActivity is the new top
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        
        // Update background based on mood
        when (selectedMood) {
            "energetic" -> cardFront.setBackgroundResource(R.drawable.card_bg_energetic)
            "calm" -> cardFront.setBackgroundResource(R.drawable.card_bg_calm)
            "low_energy" -> cardFront.setBackgroundResource(R.drawable.card_bg_low_energy)
            "sad" -> cardFront.setBackgroundResource(R.drawable.card_bg_sad)
            else -> cardFront.setBackgroundResource(R.drawable.card_bg_energetic)
        }

        // Update Card Content based on Time and Mood
        updateCardContent(selectedTime, selectedMood)
    }

    private fun updateCardContent(time: String, mood: String) {
        val tvFrontTitle1 = findViewById<TextView>(R.id.tvFrontTitle1)
        val tvFrontTitle2 = findViewById<TextView>(R.id.tvFrontTitle2)
        val tvBackTitle1 = findViewById<TextView>(R.id.tvBackTitle1)
        val tvBackSuggestion1 = findViewById<TextView>(R.id.tvBackSuggestion1)
        val tvBackTitle2 = findViewById<TextView>(R.id.tvBackTitle2)
        val tvBackSuggestion2 = findViewById<TextView>(R.id.tvBackSuggestion2)

        // Set the correct text color for the back card based on mood
        val textColorId = when (mood) {
            "energetic" -> R.color.card_energetic
            "calm" -> R.color.card_calm
            "low_energy" -> R.color.card_low_energy
            "sad" -> R.color.card_sad
            else -> R.color.card_energetic
        }
        val textColor = ContextCompat.getColor(this, textColorId)
        tvBackTitle1.setTextColor(textColor)
        tvBackTitle2.setTextColor(textColor)

        when (mood) {
            "energetic" -> {
                when (time.lowercase()) {
                    "afternoon" -> {
                        tvFrontTitle1.text = getString(R.string.midday_boost_title)
                        tvFrontTitle2.text = getString(R.string.activity_buddy_title)
                        tvBackTitle1.text = getString(R.string.midday_boost_title)
                        tvBackSuggestion1.text = getString(R.string.midday_boost_suggestion)
                        tvBackTitle2.text = getString(R.string.activity_buddy_title)
                        tvBackSuggestion2.text = getString(R.string.activity_buddy_suggestion)
                    }
                    "evening" -> {
                        tvFrontTitle1.text = getString(R.string.golden_hour_title)
                        tvFrontTitle2.text = getString(R.string.hit_town_title)
                        tvBackTitle1.text = getString(R.string.golden_hour_title)
                        tvBackSuggestion1.text = getString(R.string.golden_hour_suggestion)
                        tvBackTitle2.text = getString(R.string.hit_town_title)
                        tvBackSuggestion2.text = getString(R.string.hit_town_suggestion)
                    }
                    "night" -> {
                        tvFrontTitle1.text = getString(R.string.night_owl_title)
                        tvFrontTitle2.text = getString(R.string.steer_wheel_title)
                        tvBackTitle1.text = getString(R.string.night_owl_title)
                        tvBackSuggestion1.text = getString(R.string.night_owl_suggestion)
                        tvBackTitle2.text = getString(R.string.steer_wheel_title)
                        tvBackSuggestion2.text = getString(R.string.steer_wheel_suggestion)
                    }
                    "morning" -> {
                        tvFrontTitle1.text = getString(R.string.sunrise_title)
                        tvFrontTitle2.text = getString(R.string.hype_check_title)
                        tvBackTitle1.text = getString(R.string.sunrise_title)
                        tvBackSuggestion1.text = getString(R.string.sunrise_suggestion)
                        tvBackTitle2.text = getString(R.string.hype_check_title)
                        tvBackSuggestion2.text = getString(R.string.hype_check_suggestion)
                    }
                }
            }
            "calm" -> {
                when (time.lowercase()) {
                    "morning" -> {
                        tvFrontTitle1.text = getString(R.string.earliest_bird_title)
                        tvFrontTitle2.text = getString(R.string.bookworm_title)
                        tvBackTitle1.text = getString(R.string.earliest_bird_title)
                        tvBackSuggestion1.text = getString(R.string.earliest_bird_suggestion)
                        tvBackTitle2.text = getString(R.string.bookworm_title)
                        tvBackSuggestion2.text = getString(R.string.bookworm_suggestion)
                    }
                    "afternoon" -> {
                        tvFrontTitle1.text = getString(R.string.walk_talk_title)
                        tvFrontTitle2.text = getString(R.string.on_leash_title)
                        tvBackTitle1.text = getString(R.string.walk_talk_title)
                        tvBackSuggestion1.text = getString(R.string.walk_talk_suggestion)
                        tvBackTitle2.text = getString(R.string.on_leash_title)
                        tvBackSuggestion2.text = getString(R.string.on_leash_suggestion)
                    }
                    "evening" -> {
                        tvFrontTitle1.text = getString(R.string.send_link_title)
                        tvFrontTitle2.text = getString(R.string.log_ticket_title)
                        tvBackTitle1.text = getString(R.string.send_link_title)
                        tvBackSuggestion1.text = getString(R.string.send_link_suggestion)
                        tvBackTitle2.text = getString(R.string.log_ticket_title)
                        tvBackSuggestion2.text = getString(R.string.log_ticket_suggestion)
                    }
                    "night" -> {
                        tvFrontTitle1.text = getString(R.string.archive_day_title)
                        tvFrontTitle2.text = getString(R.string.namaste_title)
                        tvBackTitle1.text = getString(R.string.archive_day_title)
                        tvBackSuggestion1.text = getString(R.string.archive_day_suggestion)
                        tvBackTitle2.text = getString(R.string.namaste_title)
                        tvBackSuggestion2.text = getString(R.string.namaste_suggestion)
                    }
                }
            }
            "low_energy" -> {
                when (time.lowercase()) {
                    "morning" -> {
                        tvFrontTitle1.text = getString(R.string.tiny_steps_title)
                        tvFrontTitle2.text = getString(R.string.caffeine_boost_title)
                        tvBackTitle1.text = getString(R.string.tiny_steps_title)
                        tvBackSuggestion1.text = getString(R.string.tiny_steps_suggestion)
                        tvBackTitle2.text = getString(R.string.caffeine_boost_title)
                        tvBackSuggestion2.text = getString(R.string.caffeine_boost_suggestion)
                    }
                    "afternoon" -> {
                        tvFrontTitle1.text = getString(R.string.footnote_title)
                        tvFrontTitle2.text = getString(R.string.unwind_retreat_title)
                        tvBackTitle1.text = getString(R.string.footnote_title)
                        tvBackSuggestion1.text = getString(R.string.footnote_suggestion)
                        tvBackTitle2.text = getString(R.string.unwind_retreat_title)
                        tvBackSuggestion2.text = getString(R.string.unwind_retreat_suggestion)
                    }
                    "evening" -> {
                        tvFrontTitle1.text = getString(R.string.get_back_title)
                        tvFrontTitle2.text = getString(R.string.observe_distance_title)
                        tvBackTitle1.text = getString(R.string.get_back_title)
                        tvBackSuggestion1.text = getString(R.string.get_back_suggestion)
                        tvBackTitle2.text = getString(R.string.observe_distance_title)
                        tvBackSuggestion2.text = getString(R.string.observe_distance_suggestion)
                    }
                    "night" -> {
                        tvFrontTitle1.text = getString(R.string.running_on_e_title)
                        tvFrontTitle2.text = getString(R.string.explore_sky_title)
                        tvBackTitle1.text = getString(R.string.running_on_e_title)
                        tvBackSuggestion1.text = getString(R.string.running_on_e_suggestion)
                        tvBackTitle2.text = getString(R.string.explore_sky_title)
                        tvBackSuggestion2.text = getString(R.string.explore_sky_suggestion)
                    }
                }
            }
            "sad" -> {
                when (time.lowercase()) {
                    "morning" -> {
                        tvFrontTitle1.text = getString(R.string.comfort_buddy_title)
                        tvFrontTitle2.text = getString(R.string.mood_lifter_title)
                        tvBackTitle1.text = getString(R.string.comfort_buddy_title)
                        tvBackSuggestion1.text = getString(R.string.comfort_buddy_suggestion)
                        tvBackTitle2.text = getString(R.string.mood_lifter_title)
                        tvBackSuggestion2.text = getString(R.string.mood_lifter_suggestion)
                    }
                    "afternoon" -> {
                        tvFrontTitle1.text = getString(R.string.brunch_hug_title)
                        tvFrontTitle2.text = getString(R.string.silent_therapy_title)
                        tvBackTitle1.text = getString(R.string.brunch_hug_title)
                        tvBackSuggestion1.text = getString(R.string.brunch_hug_suggestion)
                        tvBackTitle2.text = getString(R.string.silent_therapy_title)
                        tvBackSuggestion2.text = getString(R.string.silent_therapy_suggestion)
                    }
                    "evening" -> {
                        tvFrontTitle1.text = getString(R.string.cards_on_deck_title)
                        tvFrontTitle2.text = getString(R.string.bigger_picture_title)
                        tvBackTitle1.text = getString(R.string.cards_on_deck_title)
                        tvBackSuggestion1.text = getString(R.string.cards_on_deck_suggestion)
                        tvBackTitle2.text = getString(R.string.bigger_picture_title)
                        tvBackSuggestion2.text = getString(R.string.bigger_picture_suggestion)
                    }
                    "night" -> {
                        tvFrontTitle1.text = getString(R.string.next_track_title)
                        tvFrontTitle2.text = getString(R.string.dotted_line_title)
                        tvBackTitle1.text = getString(R.string.next_track_title)
                        tvBackSuggestion1.text = getString(R.string.next_track_suggestion)
                        tvBackTitle2.text = getString(R.string.dotted_line_title)
                        tvBackSuggestion2.text = getString(R.string.dotted_line_suggestion)
                    }
                }
            }
        }
    }

    private fun flipCard(front: View, back: View) {
        val outAnim = AnimatorInflater.loadAnimator(this, R.animator.flip_out)
        val inAnim = AnimatorInflater.loadAnimator(this, R.animator.flip_in)

        if (isFrontVisible) {
            outAnim.setTarget(front)
            inAnim.setTarget(back)
            outAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    front.visibility = View.GONE
                    back.visibility = View.VISIBLE
                    inAnim.start()
                }
            })
            outAnim.start()
            isFrontVisible = false
        } else {
            outAnim.setTarget(back)
            inAnim.setTarget(front)
            outAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    back.visibility = View.GONE
                    front.visibility = View.VISIBLE
                    inAnim.start()
                }
            })
            outAnim.start()
            isFrontVisible = true
        }
    }
}
