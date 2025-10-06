package com.example.demosample.presentation.ui.profile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.demosample.databinding.FragmentMyChoiceBinding
import com.example.demosample.domain.model.UserModel
import com.example.demosample.presentation.state.UiState
import com.example.demosample.presentation.viewModels.ProfileViewModel
import com.example.demosample.presentation.ui.profile.adapter.MyChoiceUsersProfileAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChoiceFragment : Fragment() {

    private lateinit var binding: FragmentMyChoiceBinding
    lateinit var viewModel: ProfileViewModel

    companion object {
        /**
         * Use this factory method to create a new instance of MyChoiceFragment
         */
        @JvmStatic
        fun newInstance() =
            MyChoiceFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyChoiceBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class]

        Log.d("Flow_CHECK","MyChoiceFragment - onViewCreated ")
        observeData()
    }

    /*
    * show database users
    * if list is empty then show noData view */
    private fun initRecyclerView(list: List<UserModel>) {
        binding.txtNoData.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
        binding.rvProfiles.adapter = MyChoiceUsersProfileAdapter(
            ArrayList(list)
        )
    }

    private fun observeData() {
        viewModel.getMyChoiceUsersFromDb()

        viewModel.usersProfileState.observe(viewLifecycleOwner) {state->
            when(state){
                is UiState.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Success ->{
                    binding.progressBar.visibility = View.GONE
                    initRecyclerView(state.data)
                }
                is UiState.Error->{
                    binding.progressBar.visibility = View.GONE
                    if(state.message.isNotEmpty()){
                        Toast.makeText(requireActivity(),state.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}