package com.jason.daisy.viewsolves

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.daisy.database.Solve
import com.jason.daisy.databinding.FragmentViewSolvesBinding
import com.jason.daisy.scrambletimer.PuzzleType

class ViewSolvesFragment : Fragment(), SolvesAdapterListener {
    private var _binding: FragmentViewSolvesBinding? = null
    private val binding: FragmentViewSolvesBinding
        get() = requireNotNull(_binding)
    private lateinit var viewModel: ViewSolvesViewModel
    private lateinit var adapter: SolvesAdapter
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentViewSolvesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = requireActivity().getSharedPreferences("Puzzle_Preferences", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(
                this,
                ViewSolvesViewModelFactory(
                        requireActivity().application,
                        sharedPreference.getString("SelectedPuzzle", PuzzleType.ThreeByThree.toString()) ?: "Three by Three")
        ).get(ViewSolvesViewModel::class.java)

        setRecycler()
        setListeners()

        viewModel.data.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }

    private fun setRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SolvesAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setListeners() {
        binding.viewTimerButton.setOnClickListener { changeScreen() }
        binding.deleteAllButton.setOnClickListener { showDeleteAllDialog() }
    }

    private fun changeScreen() {
        val action = ViewSolvesFragmentDirections.actionViewSolvesFragmentToScrambleTimerFragment()
        findNavController().navigate(action)
    }

    override fun deleteSolve(s: Solve) {
        viewModel.delete(s)
    }

    private fun showDeleteAllDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete All Solves")
        builder.setMessage("This action will delete all solves. Continue?")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteAll()
                }
                else -> Log.d("DELETEALLSOLVES", "CANCELLED")
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }
}