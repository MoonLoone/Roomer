package com.example.roomer.utils

import androidx.compose.runtime.Composable
import com.example.roomer.screens.*

enum class Screens(val composeViewFunction: @Composable () -> Unit, val parentName: String) {
    Account(
        { AccountScreen() },
        NavbarItem.Profile.name,
    ),
    Location(
        { },
        NavbarItem.Profile.name,
    ),
    Rating(
        { },
        NavbarItem.Profile.name,
    ),
    Settings(
        { },
        NavbarItem.Profile.name,
    ),
    Logout(
        { },
        NavbarItem.Profile.name,
    ),
    Chat(
        { MessageScreen() },
        NavbarItem.Chats.name,
    ),
    SearchRoom(
        { SearchRoomScreen() },
        NavbarItem.Home.name,
    ),
    SearchRoommate(
        { SearchRoommateScreen() },
        NavbarItem.Home.name,
    ),
    SearchRoommateResults(
        { SearchRoommateResults() },
        NavbarItem.Home.name,
    ),
    SearchRoomResults(
        { SearchRoomResults() },
        NavbarItem.Home.name,
    )
}