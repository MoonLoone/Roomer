package com.example.roomer.presentation.screens.navbar_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.domain.model.entities.Room
import com.example.roomer.presentation.ui_components.RoomCard
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun FavouriteScreen(
    navigator: DestinationsNavigator,
) {
    NavbarManagement.showNavbar()
    val listOfFavourites = listOf<Room>()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Favourite",
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.label_text_size).sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        items(listOfFavourites.size) { index ->
            RoomCard(recommendedRoom = listOfFavourites[index], isMiniVersion = false)
        }
    }
}