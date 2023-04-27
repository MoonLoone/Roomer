package com.example.roomer.data.local

import androidx.paging.PagingSource
import com.example.roomer.data.room.entities.RoomWithHost
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.domain.model.entities.User
import kotlinx.coroutines.flow.Flow

interface RoomerStoreInterface {
    suspend fun getFavourites(limit: Int = 10, offset: Int = 0): List<Room>
    suspend fun addFavourite(room: Room)
    suspend fun addManyFavourites(favouriteRooms: List<Room>)
    suspend fun isFavouritesEmpty(): Boolean
    suspend fun deleteFavourite(room: Room)
    suspend fun getCurrentUser(): User
    suspend fun addCurrentUser(user: User)
    suspend fun updateCurrentUser(user: User)
    suspend fun deleteCurrentUser()
    suspend fun getAllUsers(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    suspend fun getUserById(userId: Int): User
    suspend fun addUser(user: User)
    suspend fun addManyUsers(users: List<User>)
    suspend fun updateUser(user: User)
    fun getPagingFavourites(): PagingSource<Int, RoomWithHost>
    suspend fun clearFavourites()
}
