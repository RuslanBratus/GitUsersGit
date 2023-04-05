package com.temp.gitusers.history.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.temp.gitusers.R
import com.temp.gitusers.context.MyApplication
import com.temp.gitusers.databinding.FragmentHistoryBinding
import com.temp.gitusers.history.view.adapter.HistoryRecyclerAdapter
import com.temp.gitusers.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: HistoryRecyclerAdapter
    @Inject lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)

        initRecycler()
        requestData()
    }

    private fun requestData() {
        viewModel.getUsersFromDB()
        lifecycleScope.launch {
            viewModel.historyUsers.observe(viewLifecycleOwner) {
                if (it.isEmpty()) showError()
                else mAdapter.submitList(it)
            }
        }
    }

    private fun showError() {
        //@TODO Implement
    }

    private fun initRecycler() {
        mAdapter = HistoryRecyclerAdapter()

        binding.historyRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}