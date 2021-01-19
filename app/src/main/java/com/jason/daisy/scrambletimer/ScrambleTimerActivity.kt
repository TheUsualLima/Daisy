package com.jason.daisy.scrambletimer

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jason.daisy.R
import com.jason.daisy.databinding.ActivityScrambleTimerBinding
import com.jason.daisy.viewsolves.ViewSolvesActivity

class ScrambleTimerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var vBinding : ActivityScrambleTimerBinding
    private lateinit var vm : ScrambleTimerViewModel
    private lateinit var hiddenElements : List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityScrambleTimerBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        //Elements to hide when solving
        hiddenElements = listOf(vBinding.lunaButton,
                vBinding.viewSolvesButton,
                vBinding.puzzleSpinner,
                vBinding.scrambleTextView
        )

        //Get ViewModel reference
        vm = ViewModelProvider(this, ScrambleTimerViewModelFactory(application)).get(
            ScrambleTimerViewModel::class.java)

        //Update Spinner with puzzle types
        vBinding.puzzleSpinner.adapter = ArrayAdapter<PuzzleType>(this, R.layout.support_simple_spinner_dropdown_item, PuzzleType.values())
        TODO("Set on item selected listener to the spinner")
        findViewById<Spinner>(R.id.puzzle_spinner)

        //Attach observers to livedata
        vm.currentTime.observe(this, { vBinding.timerTextView.text = it })
        vm.timerColor.observe(this, { vBinding.timerTextView.setTextColor(it) })
        vm.scramble.observe(this, { vBinding.scrambleTextView.text = it })
        vm.timerActive.observe(this, { setElementsVisibility(hiddenElements, !it) })

        //Listener setup
        vBinding.lunaButton.setOnClickListener {
            Toast.makeText(vBinding.lunaButton.context, "I Love you, Luna", Toast.LENGTH_SHORT).show()
        }

        vBinding.viewSolvesButton.setOnClickListener {
            changeScreen()
        }

        vBinding.mainLayout.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> !vm.handleActionDown()
                MotionEvent.ACTION_UP -> {
                    vBinding.mainLayout.performClick()
                    vm.handleActionUp()
                    false
                }
                else -> true
            }
        }
    }

    private fun setElementsVisibility(list: List<View>, setVisible: Boolean) {
        for(e in list) {
            e.visibility = if(setVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun changeScreen() {
        val viewSolvesIntent = Intent(this, ViewSolvesActivity::class.java).apply{}
        startActivity(viewSolvesIntent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val t = parent?.getItemAtPosition(position).toString()
        Toast.makeText(parent?.context, t, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}