package com.jason.daisy.scrambletimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jason.daisy.R
import com.jason.daisy.databinding.FragmentScrambleTimerBinding

class ScrambleTimerFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentScrambleTimerBinding? = null
   // private val sharedPreference = requireActivity().getSharedPreferences("Puzzle_Preferences", Context.MODE_PRIVATE)
    private val binding: FragmentScrambleTimerBinding
        get() = requireNotNull(_binding)
    private val viewModel : ScrambleTimerViewModel by viewModels { ScrambleTimerViewModelFactory(requireActivity().application) }
    private lateinit var hiddenElements : List<View>
    private lateinit var sharedPreference: SharedPreferences

    companion object {
        fun newInstance() = ScrambleTimerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScrambleTimerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Elements to hide when solving
        hiddenElements = listOf(binding.lunaButton,
            binding.viewSolvesButton,
            binding.puzzleSpinner,
            binding.scrambleTextView
        )

        sharedPreference = requireActivity().getSharedPreferences("Puzzle_Preferences", Context.MODE_PRIVATE)

        setSpinner()
        setObservers()
        setListeners()
    }

    private fun setSpinner() {
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, PuzzleType.values())
        binding.puzzleSpinner.adapter = arrayAdapter
        binding.puzzleSpinner.onItemSelectedListener = this
        val puzzleString = sharedPreference.getString("SelectedPuzzle", PuzzleType.ThreeByThree.toString()) ?: "Three by Three"
        val previousPuzzleSelection = arrayAdapter.getPosition(
                PuzzleType.getPuzzleType(puzzleString)
        )
        binding.puzzleSpinner.setSelection(previousPuzzleSelection)
    }

    private fun setObservers() {
        viewModel.currentTime.observe(requireActivity(), { binding.timerTextView.text = it })
        viewModel.timerColor.observe(requireActivity(), { binding.timerTextView.setTextColor(it) })
        viewModel.scramble.observe(requireActivity(), { binding.scrambleTextView.text = it })
        viewModel.timerActive.observe(requireActivity(), { setElementsVisibility(hiddenElements, !it) })
    }

    private fun setElementsVisibility(list: List<View>, setVisible: Boolean) {
        for(e in list) {
            e.visibility = if(setVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setListeners() {
        binding.lunaButton.setOnClickListener {
            Toast.makeText(binding.lunaButton.context, "I Love you, Luna", Toast.LENGTH_SHORT).show()
        }

        binding.viewSolvesButton.setOnClickListener {
            changeScreen()
        }

        binding.mainLayout.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> !viewModel.handleActionDown()
                MotionEvent.ACTION_UP -> {
                    binding.mainLayout.performClick()
                    viewModel.handleActionUp()
                    false
                }
                else -> true
            }
        }
    }

    private fun changeScreen() {
        val action = ScrambleTimerFragmentDirections.actionScrambleTimerFragmentToViewSolvesFragment()
        findNavController().navigate(action)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val puzzleChosen = PuzzleType.getPuzzleType(parent.getItemAtPosition(pos).toString())
        viewModel.selectPuzzle(puzzleChosen)

        val editor = sharedPreference.edit()
        editor.putString("SelectedPuzzle", parent.getItemAtPosition(pos).toString())
        editor.apply()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}