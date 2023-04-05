package com.temp.gitusers.main.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.temp.gitusers.R
import com.temp.gitusers.Utils
import com.temp.gitusers.context.MainActivity
import com.temp.gitusers.context.MyApplication
import com.temp.gitusers.databinding.FragmentMainBinding
import com.temp.gitusers.main.view.adapter.MainRecyclerAdapter
import com.temp.gitusers.main.view.adapter.clickListener.OnUserClickListener
import com.temp.gitusers.main.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, OnUserClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: MainRecyclerAdapter
    @Inject lateinit var viewModel: MainViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)

        setSwipeRefresh()
        initRecycler()
        requestData()
    }

    private fun setSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.post {
            swipeRefresh.isRefreshing = true
            requestData()
        }
    }


    private fun requestData() {
        lifecycleScope.launch {
            viewModel.users.collectLatest {
                swipeRefresh.isRefreshing = false
                mAdapter.submitData(it)
            }
        }
    }

    private fun initRecycler() {
        mAdapter = MainRecyclerAdapter(this)

        binding.recyclerView.apply {
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

    override fun onRefresh() {
        requestData()
    }

    override fun onClick(userLogin: String) {
        val bundle = Bundle()
        bundle.putString(Utils.UserLoginKey, userLogin)
        (requireActivity() as MainActivity).navView
        findNavController().navigate(R.id.action_mainFragment_to_selectedUserFragment, bundle)
    }

}