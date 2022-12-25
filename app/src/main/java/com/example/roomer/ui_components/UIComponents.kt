package com.example.roomer.ui_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomer.R
import com.example.roomer.models.MessageToList
import com.example.roomer.models.RecommendedRoom
import com.example.roomer.models.RecommendedRoommate
import com.example.roomer.models.SearchUserResult
import com.example.roomer.utils.NavbarItem

@Composable
fun ProfileContentLine(text: String, iconId: Int, onNavigateToFriends: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = { onNavigateToFriends.invoke() }),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = text,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop,
        )
        Text(
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            text = text,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    Divider(color = Color.Black, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))
}

@Composable
fun Navbar(navController: NavHostController, selectedNavbarItemName: String) {
    var selectedItem by remember {
        mutableStateOf(selectedNavbarItemName)
    }
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.secondary_color),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        NavbarItem.values().map { it.name }.forEach { screen ->
            BottomNavigationItem(
                selected = (selectedItem == screen),
                modifier = Modifier
                    .width(80.dp)
                    .padding(
                        start = 4.dp,
                        end = 4.dp
                    ),
                onClick = {
                    selectedItem = screen
                    navController.navigate(screen)
                },
                icon = {
                    val item = NavbarItem.valueOf(screen)
                    if (selectedItem == screen) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(64.dp)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(colorResource(id = R.color.primary)),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .align(Center)
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = painterResource(id = item.iconSelected),
                                    contentDescription = item.description
                                )
                            }
                            Text(
                                text = screen,
                                fontSize = integerResource(id = R.integer.secondary_text_size).sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 4.dp)
                            )
                        }
                    } else {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .align(Center)
                                        .width(24.dp)
                                        .height(24.dp),
                                    painter = painterResource(id = item.iconUnSelected),
                                    contentDescription = item.description
                                )
                            }
                            Text(
                                text = screen,
                                fontSize = integerResource(id = R.integer.secondary_text_size).sp,
                                color = colorResource(id = R.color.text_secondary),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 4.dp),
                            )
                        }
                    }
                })
        }
    }
}

@Composable
fun MessageItem(
    message: MessageToList,
) {
    Row(
        modifier = Modifier
            .clickable { message.navigateToMessage.invoke() }
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "message_client_avatar",
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .padding(start = 8.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
            alignment = Center
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = message.username,
                    fontSize = integerResource(id = R.integer.primary_text_size).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        painter = painterResource(id = if (message.isRead) R.drawable.checked_messages_icon else R.drawable.unchecked_messages_icon),
                        contentDescription = if (message.isRead) "Messages checked" else "Messages unchecked",
                        alignment = Center,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp),
                    )
                    Text(
                        text = message.messageDate, style = TextStyle(
                            color = colorResource(id = R.color.text_secondary),
                            fontSize = 12.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(
                    text = message.messageCutText, style = TextStyle(
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 14.sp,
                    )
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    if (message.unreadMessages > 0) {
                        Text(
                            text =
                            when (message.unreadMessages) {
                                in 1..999 -> message.unreadMessages.toString()
                                else -> "999+"
                            },
                            modifier = Modifier
                                .width(48.dp)
                                .height(20.dp)
                                .background(
                                    color = colorResource(
                                        id = R.color.primary
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
    Divider(
        color = Color.Black,
        modifier = Modifier.padding(top = 2.dp),
    )
}

@Composable
fun Message(isUserMessage: Boolean, text: String, data: String) {
    if (!isUserMessage) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(214.dp)
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .height(IntrinsicSize.Max)
                    .background(
                        colorResource(id = R.color.secondary_color),
                        RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                Text(text = text, textAlign = TextAlign.Start, modifier = Modifier.padding(16.dp))
                Text(text = data, textAlign = TextAlign.End, modifier = Modifier.padding(16.dp))
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(start = 40.dp, top = 16.dp)
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .width(214.dp)
                    .height(IntrinsicSize.Max)
                    .background(
                        colorResource(id = R.color.primary),
                        RoundedCornerShape(bottomStart = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
                    ),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = text)
                Image(
                    painter = painterResource(id = R.drawable.checked_messages_icon),
                    contentDescription = "Checked message"
                )
                Text(text = data)
            }
        }
    }
}

@Composable
fun UserCard(recommendedRoommate: RecommendedRoommate) {
    Column(
        modifier = Modifier
            .height(148.dp)
            .width(100.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = recommendedRoommate.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp, start = 10.dp, end = 10.dp, bottom = 7.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = recommendedRoommate.name,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = "Rating icon",
                    modifier = Modifier
                        .width(integerResource(id = R.integer.ordinary_icon_size).dp)
                        .height(integerResource(id = R.integer.ordinary_icon_size).dp)
                )
                Text(
                    text = recommendedRoommate.rating.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun RoomCard(recommendedRoom: RecommendedRoom, isMiniVersion: Boolean) {
    val cardWidth = if (isMiniVersion) 240.dp else 332.dp
    val cardHeight = if (isMiniVersion) 148.dp else 222.dp
    val imageHeight = if (isMiniVersion) 92.dp else 140.dp
    val nameTextSize = if (isMiniVersion) 16.sp else 20.sp
    val locationTextSize = if (isMiniVersion) 12.sp else 14.sp
    Column(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        var isLiked by remember {
            mutableStateOf(recommendedRoom.isLiked)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ordinary_client),
                contentDescription = "Room image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                painter = if (isLiked) painterResource(id = R.drawable.room_like_in_icon) else painterResource(
                    id = R.drawable.room_like_icon
                ), contentDescription = "Like icon",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
                    .width(32.dp)
                    .height(32.dp)
                    .clickable {
                        isLiked = !isLiked
                    }
            )
        }
        Text(
            text = recommendedRoom.name,
            modifier = Modifier.padding(start = 10.dp, top = if (isMiniVersion) 4.dp else 10.dp),
            style = TextStyle(
                color = colorResource(
                    id = R.color.secondary_color
                ),
                fontSize = nameTextSize,
                fontWeight = FontWeight.Bold,
            )
        )
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = "Location icon",
                modifier = Modifier
                    .width(14.dp)
                    .height(14.dp),
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.secondary_color))
            )
            Text(
                text = recommendedRoom.location, style = TextStyle(
                    color = colorResource(id = R.color.secondary_color),
                    fontSize = locationTextSize,
                )
            )
        }
    }
}

@Composable
fun SearchField(onNavigateToFriends: () -> Unit) {
    var searcherText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(56.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(4.dp),
                color = colorResource(id = R.color.primary_dark),
            ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
        ),
        value = searcherText,
        onValueChange = {
            if (it.text.length > 100) {
                searcherText = it
            }
        },
        label = {
            Text(
                text = "Search for roommate or housing",
                modifier = Modifier.padding(bottom = 24.dp),
                style = TextStyle(
                    color = colorResource(id = R.color.primary_dark),
                    fontSize = 12.sp,
                ),
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.loupe_icon),
                contentDescription = "Search loupe",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_filter_icon),
                contentDescription = "Searcher filters",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .clickable {
                        onNavigateToFriends.invoke()
                    },
            )
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
    )
}

@Composable
fun BackBtn(onBackNavigation: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back_btn),
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .clickable {
                onBackNavigation.invoke()
            },
        contentDescription = "Back button"
    )
}

@Composable
fun GreenButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.primary_dark),
            contentColor = colorResource(id = R.color.secondary_color)
        )

    ) {
        androidx.compose.material.Text(
            text = text,
        )
    }
}

@Composable
fun GreenButtonOutline(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = colorResource(id = R.color.primary_dark)
        ),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.text_secondary))
    ) {
        androidx.compose.material.Text(
            text = text,
        )
    }
}

@Composable
fun ButtonsRow(label: String, values: List<String>) {
    var selectedItem by remember {
        mutableStateOf(values[0])
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = integerResource(id = R.integer.primary_text_size).sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (value in values) {
                if (value == selectedItem) {
                    GreenButtonPrimary(text = value) { }
                } else {
                    GreenButtonOutline(text = value) {
                        selectedItem = value
                    }
                }
            }
        }
    }
}

@Composable
fun FilterSelect(selectItemName: String, onNavigateToFriends: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .width(88.dp)
                .background(
                    color = if (selectItemName == "Room") colorResource(id = R.color.primary_dark) else Color.White,
                    RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp),
                )
                .border(
                    1.dp,
                    color = colorResource(id = R.color.text_secondary),
                    RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp)
                )
                .clickable { if (selectItemName == "Roommate") onNavigateToFriends.invoke() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (selectItemName == "Room") Image(
                painter = painterResource(id = R.drawable.unchecked_messages_icon),
                contentDescription = "Select room",
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp),
                colorFilter = ColorFilter.tint(
                    colorResource(id = R.color.primary)
                )
            )
            Text(
                text = "Room",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = if (selectItemName == "Room") colorResource(id = R.color.primary) else colorResource(
                        id = R.color.text_secondary
                    )
                )
            )
        }
        Row(
            modifier = Modifier
                .height(40.dp)
                .width(88.dp)
                .background(
                    color = if (selectItemName == "Roommate") colorResource(id = R.color.primary_dark) else Color.White,
                    RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp),
                )
                .border(
                    1.dp,
                    color = colorResource(id = R.color.text_secondary),
                    RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp)
                )
                .clickable { if (selectItemName == "Room") onNavigateToFriends.invoke() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Roommate", style = TextStyle(
                    fontSize = 14.sp,
                    color = if (selectItemName == "Roommate") colorResource(id = R.color.primary) else colorResource(
                        id = R.color.text_secondary
                    )
                )
            )
            if (selectItemName == "Roommate") Image(
                painter = painterResource(id = R.drawable.unchecked_messages_icon),
                contentDescription = "Select roommate",
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp),
                colorFilter = ColorFilter.tint(
                    colorResource(id = R.color.primary)
                )
            )
        }
    }
}

@Composable
fun UserCardResult(searchUser: SearchUserResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp)
            .background(
                color = colorResource(id = R.color.primary),
                shape = RoundedCornerShape(20.dp),
            )
    ) {
        Image(
            painterResource(id = R.drawable.ordinary_client),
            contentDescription = searchUser.name,
            modifier = Modifier
                .fillMaxHeight()
                .width(104.dp),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = searchUser.name,
                style = TextStyle(
                    fontSize = integerResource(id = R.integer.label_text_size).sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "Location",
                    modifier = Modifier
                        .width(integerResource(id = R.integer.ordinary_icon_size).dp)
                        .height(integerResource(id = R.integer.ordinary_icon_size).dp)
                        .align(Alignment.CenterVertically),
                )
                Text(
                    text = searchUser.location,
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = "Status:",
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
                Text(
                    searchUser.status,
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        color = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = "Rating:",
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
                Text(
                    text = searchUser.rate,
                    style = TextStyle(
                        fontSize = integerResource(id = R.integer.primary_text_size).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    ),
                    modifier = Modifier.padding(start = 8.dp),
                )
                Icon(
                    painter = painterResource(id = R.drawable.rating_icon),
                    contentDescription = "Rating star",
                    modifier = Modifier
                        .height(integerResource(id = R.integer.ordinary_icon_size).dp)
                        .width(integerResource(id = R.integer.ordinary_icon_size).dp),
                )
            }
        }
    }
}

