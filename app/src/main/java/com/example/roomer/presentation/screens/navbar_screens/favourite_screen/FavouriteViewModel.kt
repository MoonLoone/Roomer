package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.data.shared.housing_like.HousingLikeInterface
import com.example.roomer.domain.model.entities.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    val housingLike: HousingLikeInterface
) : ViewModel() {

    private val _pagingData: MutableState<Flow<PagingData<Room>>> = mutableStateOf(emptyFlow())
    val pagingData: State<Flow<PagingData<Room>>> = _pagingData

    init {
        viewModelScope.launch {
            val response = roomerRepository.getFavouritesForUser()
            _pagingData.value = response.map { pagingData ->
                pagingData.map { localRoom ->
                    localRoom.toRoom()
                }
            }.cachedIn(viewModelScope)
        }
    }
}
