package com.temp.gitusers.selectedUser.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.temp.gitusers.R
import com.temp.gitusers.Utils
import com.temp.gitusers.context.MyApplication
import com.temp.gitusers.databinding.FragmentSelectedUserBinding
import com.temp.gitusers.history.model.HistoryUser
import com.temp.gitusers.main.viewmodel.MainViewModel
import com.temp.gitusers.selectedUser.model.SelectedUser
import javax.inject.Inject

class SelectedUserFragment : Fragment() {
    private var _binding: FragmentSelectedUserBinding? = null
    private val binding get() = _binding!!
    private var currentLogin: String? = null
    @Inject lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_selected_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectedUserBinding.bind(view)
        try {
            currentLogin = requireArguments().getString(Utils.UserLoginKey)
            startProcess()
        }
        catch (E: Exception) {showError()}

    }

    private fun startProcess() {
        initRecycler()
        if (currentLogin == null) showError()
        else {
            binding.selectedError.isVisible = false
            requestData()
        }
    }

    private fun initRecycler() {
        //@TODO Implement this
    }

    private fun showError() {
        //@TODO implement
        //@TODO Add Loading animation while no data
    }

    private fun requestData() {
        viewModel.getUserInfo(currentLogin!!)
        viewModel.currentUser.observe(viewLifecycleOwner) {
            setUiInfo(it)
        }
    }

    private fun setUiInfo(selectedUser: SelectedUser) {
        Glide.with(requireContext())
            .load(selectedUser.avatarUrl)
            .into(binding.userImage)
        binding.userEmail.text = selectedUser.email
        binding.userName.text = selectedUser.name
        binding.userType.text = selectedUser.type
        binding.userFollowers.text = getString(R.string.user_followers_placeholder, selectedUser.followers)
        binding.userFollowing.text = getString(R.string.user_following_placeholder, selectedUser.following)

        viewModel.saveUserToDB(
            HistoryUser(
                name = selectedUser.name,
                avatarUrl = selectedUser.avatarUrl,
                email = selectedUser.email
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}