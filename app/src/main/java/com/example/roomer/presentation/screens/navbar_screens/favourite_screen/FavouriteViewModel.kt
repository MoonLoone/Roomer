package com.example.roomer.presentation.screens.navbar_screens.favourite_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.roomer.data.paging.RoomerPagingSource
import com.example.roomer.data.paging.RoomerRemoteMediator
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.data.room.RoomerDatabase
import com.example.roomer.data.room.entities.LocalRoom
import com.example.roomer.data.room.entities.toRoom
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.toLocalRoom
import com.example.roomer.domain.usecase.navbar_screens.FavouriteUseCase
import com.example.roomer.presentation.ui_components.shared.HousingLike
import com.example.roomer.utils.Constants
import com.example.roomer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val roomerRepository: RoomerRepositoryInterface,
    private val housingLike: HousingLike,
    private val roomerDatabase: RoomerDatabase,
) : ViewModel() {

    private val _state: MutableStateFlow<FavouriteScreenState> = MutableStateFlow(
        FavouriteScreenState()
    )
    val state: StateFlow<FavouriteScreenState> = _state
    var pagingData: Flow<PagingData<Room>>? = null
    private val favouritesUseCase = FavouriteUseCase(roomerRepository)

    @OptIn(ExperimentalPagingApi::class)
    fun getFavourites() {
        viewModelScope.launch {
            if (pagingData == null) {
                val currentUser = roomerRepository.getLocalCurrentUser()
                val favouriteDao = roomerDatabase.favourites
                pagingData = Pager(
                    PagingConfig(
                        pageSize = Constants.Chat.PAGE_SIZE,
                        maxSize = Constants.Chat.CASH_SIZE,
                        initialLoadSize = Constants.Chat.INITIAL_SIZE
                    ),
                    remoteMediator = RoomerRemoteMediator(
                        roomerDatabase,
                        useCaseFunction = { offset ->
                            var items = listOf<Room>()
                            favouritesUseCase(currentUser.userId, offset, 10).collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        if ((resource.data?.size ?: 0) > 0) {
                                            items = resource.data!!
                                            _state.value = FavouriteScreenState(success = true)
                                        } else {
                                            _state.value = FavouriteScreenState(
                                                success = true,
                                                emptyList = true,
                                            )
                                            items = emptyList()
                                        }
                                    }

                                    is Resource.Loading -> {
                                        _state.value = FavouriteScreenState(isLoading = true)
                                    }

                                    is Resource.Internet -> {
                                        _state.value = FavouriteScreenState(internetProblem = true)
                                    }

                                    else -> {
                                        _state.value =
                                            FavouriteScreenState(error = resource.message ?: "")
                                    }
                                }
                            }
                            items
                        },
                        saveToDb = { response -> favouriteDao.save((response as List<Room>).map { it.toLocalRoom() }) },
                        deleteFromDb = { favouriteDao.deleteAll() }
                    )
                ) {
                    RoomerPagingSource { offset: Int, limit: Int ->
                        _state.value = FavouriteScreenState(isLoading = true)
                        val favourites = favouriteDao.getAll(limit, offset)
                        if (favourites.isEmpty()) _state.value =
                            FavouriteScreenState(emptyList = true)
                        else _state.value = FavouriteScreenState(success = true)
                        favourites.map { it.room.toRoom() }
                    }
                }.flow
                _state.value = FavouriteScreenState(isLoading = true)
            }
        }
    }

    fun dislikeHousing(housingId: Int) {
        viewModelScope.launch {
            val currentUser = roomerRepository.getLocalCurrentUser()
            housingLike.dislikeHousing(housingId, currentUser.userId)
        }
    }
}
