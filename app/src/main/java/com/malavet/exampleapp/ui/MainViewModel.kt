package com.malavet.exampleapp.ui

import androidx.lifecycle.*
import com.malavet.exampleapp.data.SchoolSATRepository
import com.malavet.exampleapp.data.models.SAT
import com.malavet.exampleapp.data.models.School
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* [MainViewModel] the viewmodel for MVVM pattern,
* */
@HiltViewModel
class MainViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle,
    private val schoolSATRepository: SchoolSATRepository
    ) : ViewModel() {

    //state flow for SAT data
    private val _satData = MutableStateFlow<List<SAT?>>(emptyList())
    val satData: StateFlow<List<SAT?>> = _satData

    //state flow for Schools
    private val _schoolist = MutableStateFlow(emptyList<School>())
    val schoolList: StateFlow<List<School>> = _schoolist

    //called on app launch
    init {
        getSchools()
    }

    /*
    * Retrieves Schools from the Repository
    * */
    fun getSchools() {
        viewModelScope.launch {
            val i = schoolSATRepository.schools()
            _schoolist.value = i
        }
    }



    /*
    * Retrieves SAT Data for a School from the Repository
    * */
        fun getSATData(school: String) {
            viewModelScope.launch {
                 val i =  schoolSATRepository.sats(school)
                 _satData.value = i
            }

        }
    }

