package com.example.roomer.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class LocalRoom(
    @PrimaryKey
    val roomId: Int,
    val monthPrice: Int,
    val hostId: Int,
    val description: String,
    val photo: String,
    val bathroomsCount: Int,
    val bedroomsCount: Int,
    val housingType: String,
    val sharingType: String,
    val location: String,
    val title: String,
    val isLiked: Boolean
)
