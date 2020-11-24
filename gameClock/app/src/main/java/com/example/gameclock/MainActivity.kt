package com.example.gameclock

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Global variables that will be available throughout the program.
const val topInitialTime: Long = 30
var topTimeLeft = topInitialTime
const val bottomInitialTime: Long = 30
var bottomTimeLeft = bottomInitialTime
const val oneSecondInMilliseconds: Long =  1000
var topClicked = false
var bottomClicked= false


class MainActivity : AppCompatActivity() {

    // This is a variable to hold a special timer object. We set it here once so that it is
    // available throughout the program. It will be reset when the buttons are pressed though.
    private var topTimer: CountDownTimer = object : CountDownTimer(0, 0) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {}
    }

    private var bottomTimer: CountDownTimer = object : CountDownTimer(0, 0) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        //boilerplate code
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create variable references to views
        val text: TextView = findViewById(R.id.textView)
        val topButton: Button = findViewById(R.id.topButton)
        val bottomButton: Button = findViewById(R.id.bottomButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        //set initial states for view attributes
        topButton.text = topInitialTime.toString()
        bottomButton.text = bottomInitialTime.toString()
        topButton.setBackgroundColor(Color.LTGRAY)
        bottomButton.setBackgroundColor(Color.LTGRAY)

        //code will run when the top button is pressed
        topButton.setOnClickListener {

            //make sure bottom button is enabled
            bottomButton.isEnabled = true
            bottomButton.isClickable = true

            //set up timer that will run when the top button is clicked
            bottomTimer = object :
                CountDownTimer(bottomTimeLeft * oneSecondInMilliseconds, oneSecondInMilliseconds) {
                // This is a special function that is part of the timer. It runs every time the
                // timer's interval passes. In this case, it is set to run every second.
                override fun onTick(millisUntilFinished: Long) {
                    // Update the label to show the remaining time and then remove one second from
                    // the timeLeft variable so that if the timer has to be stopped and started
                    // again, we know how much time to put on the new timer.
                    bottomButton.text = "${millisUntilFinished / oneSecondInMilliseconds}"
                    bottomTimeLeft--
                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    bottomButton.setBackgroundColor(Color.RED)
                    text.text = "Out of time!"
                }
            }

            //update boolean variable
            topClicked = true

            //conditional runs when the top button has been clicked
            if (topClicked) {
                //change colors to show the timer on the bottom clock is running
                topButton.setBackgroundColor(Color.GRAY)
                bottomButton.setBackgroundColor(Color.TRANSPARENT)

                //disable the top button until the bottom button is clicked
                topButton.isEnabled = false
                topButton.isClickable = false
            }

            //start the timer
            bottomTimer.start()

            //conditional to stop the other timer when this button is clicked
            if (bottomClicked) {
                topTimer.cancel()
                bottomClicked = false
            }

        }

        //we will write the same code for the bottom button
        bottomButton.setOnClickListener {

            //make sure the top button is enabled
            topButton.isEnabled = true
            topButton.isClickable = true

            //set up timer that will run when the bottom button is clicked
            topTimer = object :
                CountDownTimer(topTimeLeft * oneSecondInMilliseconds, oneSecondInMilliseconds) {
                // This is a special function that is part of the timer. It runs every time the
                // timer's interval passes. In this case, it is set to run every second.
                override fun onTick(millisUntilFinished: Long) {
                    // Update the label to show the remaining time and then remove one second from
                    // the timeLeft variable so that if the timer has to be stopped and started
                    // again, we know how much time to put on the new timer.
                    topButton.text = "${millisUntilFinished / oneSecondInMilliseconds}"
                    topTimeLeft--
                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    topButton.setBackgroundColor(Color.RED)
                    text.text = "Game Over!"
                }
            }
            //update boolean variable
            bottomClicked = true

            //conditional runs when the bottom button has been clicked
            if (bottomClicked) {
                //change colors to show the timer on the bottom clock is running
                bottomButton.setBackgroundColor(Color.GRAY)
                topButton.setBackgroundColor(Color.TRANSPARENT)

                //disable the bottom button until the top button is clicked
                bottomButton.isEnabled = false
                bottomButton.isClickable = false
            }

            //start the timer
            topTimer.start()

            //conditional to stop the other timer when this button is clicked
            if (topClicked){
                bottomTimer.cancel()
                topClicked = false
            }
        }

        //code to run when the reset button is clicked
        resetButton.setOnClickListener {
            //cancel both timers to stop them from running
            topTimer.cancel()
            bottomTimer.cancel()

            //reset the time left to the original time and display it in the button text
            topTimeLeft = topInitialTime
            topButton.text = topTimeLeft.toString()
            bottomTimeLeft = bottomInitialTime
            bottomButton.text = bottomTimeLeft.toString()

            //reset color of each button
            topButton.setBackgroundColor(Color.LTGRAY)
            bottomButton.setBackgroundColor(Color.LTGRAY)

            //ensure both buttons are enabled
            topButton.isEnabled = true
            topButton.isClickable = true
            bottomButton.isEnabled = true
            bottomButton.isClickable = true
        }
    }
}
