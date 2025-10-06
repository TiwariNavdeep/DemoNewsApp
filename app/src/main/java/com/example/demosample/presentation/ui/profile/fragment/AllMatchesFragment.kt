package com.example.demosample.presentation.ui.profile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.demosample.R
import com.example.demosample.databinding.FragmentAllMatchesBinding
import com.example.demosample.domain.model.UserModel
import com.example.demosample.presentation.customView.EndlessRecyclerView
import com.example.demosample.presentation.state.UiState
import com.example.demosample.presentation.viewModels.ProfileViewModel
import com.example.demosample.presentation.ui.profile.adapter.AllMatchesAdapter
import com.example.demosample.utils.network.NetworkObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMatchesFragment : Fragment(), EndlessRecyclerView.Pager {

    private lateinit var binding: FragmentAllMatchesBinding
    private lateinit var viewModel: ProfileViewModel

    private var adapter: AllMatchesAdapter? = null

    private val networkObserver: NetworkObserver by lazy {
        NetworkObserver(requireActivity())
    }
    companion object {
        /**
         * Use this factory method to create a new instance of AllMatchesFragment
         */
        @JvmStatic
        fun newInstance() =
            AllMatchesFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class]
        observeData()
        initAdapter()
        networkObserver.startNetworkCallback()
        observerNetwork()
    }

    /*
        observe netWork
         when internet available then get users from server otherwise
         get users from database
     */

    private fun observerNetwork() {
        Log.d("Flow_CHECK", "observerNetwork ->> ")

        networkObserver.isConnected.observe(viewLifecycleOwner){connected->
            Log.d("Flow_CHECK", "isConnected ->> $connected ")

            if(connected){
                viewModel.getUsersFromServer()
            }else{
                //reset page when internet is off
                viewModel.pageNo=1
                viewModel.getUsersFromDb()
            }
        }
    }

    /*
        stop network callbacks
    */
    override fun onDestroy() {
        super.onDestroy()
        networkObserver.stopNetworkCallback()
    }

    private fun initAdapter() {
        adapter = AllMatchesAdapter{accept, id ->
            val status = if (accept)
                getString(R.string.accepted)
            else
                getString(R.string.rejected)

            viewModel.updateInvitationStatus(id, status)
        }
        binding.rvProfiles.setPager(this)
        binding.rvProfiles.setProgressView(R.layout.layout_load_more)
        binding.rvProfiles.adapter = adapter
    }

    private fun initRecyclerView(list: List<UserModel>) {
        adapter?.submitList(list)
    }

    private fun observeData() {
        viewModel.usersProfileState.observe(viewLifecycleOwner) {state->
            when(state){
                is UiState.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Success ->{
                    binding.progressBar.visibility = View.GONE
                    binding.rvProfiles.isRefreshing = false
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
        //observe toastMessage
        viewModel.toastMessage.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                Toast.makeText(requireActivity(),it,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
    * for now paging only work when internet if on
    * i will fetch all from db in one times*/

    override fun loadNextPage() {
        if(networkObserver.isConnected.value?:true){
            viewModel.pageNo += 1
            viewModel.loadNextPage =false
            binding.rvProfiles.isRefreshing = true
            viewModel.getUsersFromServer()
        }
    }

    override fun shouldLoad(): Boolean {
        return viewModel.loadNextPage
    }
}