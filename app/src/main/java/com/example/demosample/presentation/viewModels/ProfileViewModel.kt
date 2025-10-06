package com.example.demosample.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demosample.domain.model.UserModel
import com.example.demosample.domain.useCases.usersProfile.GetAllUserFromDbUseCase
import com.example.demosample.domain.useCases.usersProfile.GetMyChoiceUserFromDbUseCase
import com.example.demosample.domain.useCases.usersProfile.GetUsersProfileFromServerUseCase
import com.example.demosample.domain.useCases.usersProfile.UpdateInvitationUseCase
import com.example.demosample.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getUserFromDbUseCase: GetAllUserFromDbUseCase,
    val getMyChoiceUserFromDbUseCase: GetMyChoiceUserFromDbUseCase,
    val updateInvitationUseCase: UpdateInvitationUseCase,
    val getUsersProfileFromServerUseCase: GetUsersProfileFromServerUseCase
): ViewModel() {

    var pageNo = 1
    var loadNextPage = false

    private val _usersProfileState = MutableLiveData<UiState<List<UserModel>>>()
    val usersProfileState: LiveData<UiState<List<UserModel>>> = _usersProfileState

    private val loadedUsers = mutableListOf<UserModel>() // <--- keeps all loaded pages


    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun getUsersFromServer(){
        viewModelScope.launch {
            getUsersProfileFromServerUseCase(pageNo)
                .onStart {
                    loadNextPage = false
                    //loader show if there is no data
                    // in case of pagination loader not show
                    if(loadedUsers.isEmpty()){
                        _usersProfileState.value = UiState.Loading
                    }
                }
                .catch {
                    _usersProfileState.value = UiState.Error("Something went wrong : ${it.message}")
                }
                .collect {
                    if(pageNo == 1){
                        loadedUsers.clear()
                    }
                    loadedUsers.addAll(it)
                    _usersProfileState.value = UiState.Success(loadedUsers)
                    delay(100)
                    loadNextPage = it.size >= 10
                }
        }
    }

    fun getUsersFromDb(){
        viewModelScope.launch{
            getUserFromDbUseCase()
                .onStart {
                    //show Loader
                    _usersProfileState.value = UiState.Loading
                }
                .catch {
                    //handle errors
                    _usersProfileState.value = UiState.Error(
                        "Something went wrong : ${it.message}")
                }
                .collect {
                    _usersProfileState.value = UiState.Success(it)
                    Log.d("Flow_CHECK","getUsersFromDb - "+it.size)
                }
        }
    }
    /*
    * pass userId & Status Like Accepted or Declined it update on local db
      and data show into MyChoices Tabs
    * */
    fun updateInvitationStatus(id: String,status: String){
        viewModelScope.launch {
            updateInvitationUseCase(id,status)
                .onStart {
                }
                .catch {
                    _toastMessage.value = "Something went wrong : ${it.message}"
                }
                .collect {
                    Log.d("updateInvitation"," $status $it")
                    _toastMessage.value = "Request $status"
                }
        }
    }

    fun getMyChoiceUsersFromDb(){
        viewModelScope.launch{
            getMyChoiceUserFromDbUseCase()
                .onStart {
                    _usersProfileState.value = UiState.Loading
                }
                .catch {
                    //hide Loader
                    //handle errors
                    _usersProfileState.value = UiState.Error(
                        "Something went wrong : ${it.message}")
                }
                .collect {
                    _usersProfileState.value = UiState.Success(it)
                    Log.d("Flow_CHECK","getMyChoiceUsersFromDb - "+it.size)
                }
        }
    }
}