package com.jason.daisy.scrambletimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jason.daisy.R
import com.jason.daisy.databinding.FragmentScrambleTimerBinding

class ScrambleTimerFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentScrambleTimerBinding? = null
    private val binding: FragmentScrambleTimerBinding
        get() = requireNotNull(_binding)
    private lateinit var viewModel : ScrambleTimerViewModel
    private lateinit var hiddenElements : List<View>

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

        //Get ViewModel reference
        viewModel = ViewModelProvider(this, ScrambleTimerViewModelFactory(requireActivity().application)).get(
            ScrambleTimerViewModel::class.java)

        //Update Spinner with puzzle types
        binding.puzzleSpinner.adapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, PuzzleType.values())
        binding.puzzleSpinner.onItemSelectedListener = this

        //Attach observers to livedata
        viewModel.currentTime.observe(requireActivity(), { binding.timerTextView.text = it })
        viewModel.timerColor.observe(requireActivity(), { binding.timerTextView.setTextColor(it) })
        viewModel.scramble.observe(requireActivity(), { binding.scrambleTextView.text = it })
        viewModel.timerActive.observe(requireActivity(), { setElementsVisibility(hiddenElements, !it) })

        //Listener setup
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

    private fun setElementsVisibility(list: List<View>, setVisible: Boolean) {
        for(e in list) {
            e.visibility = if(setVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun changeScreen() {
        val action = ScrambleTimerFragmentDirections.actionScrambleTimerFragmentToViewSolvesFragment()
        findNavController().navigate(action)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val puzzleChosen = PuzzleType.getPuzzleType(parent.getItemAtPosition(pos).toString())
        viewModel.changePuzzle(puzzleChosen)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}