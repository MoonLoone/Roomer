package com.example.roomer.presentation.screens.entrance.signup.habits_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AboutMeAvatarScreenDestination
import com.example.roomer.presentation.screens.destinations.InterestsScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.ui_components.ButtonsRowMapped
import com.example.roomer.presentation.ui_components.DropdownTextFieldMapped
import com.example.roomer.presentation.ui_components.GreenButtonPrimary
import com.example.roomer.utils.SignUpNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SignUpNavGraph
@Destination
@Composable
fun HabitsScreen(
    navigator: DestinationsNavigator,
    signUpViewModel: SignUpViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.list_elements_margin)
            ),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                color = colorResource(id = R.color.primary_dark),
                progress = 0.6f
            )
            Text(
                text = stringResource(R.string.tell_us_about_your_living_habits),
                fontSize = integerResource(id = R.integer.label_text).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            ButtonsRowMapped(
                label = stringResource(R.string.your_usual_sleep_time),
                values = mapOf(
                    Pair("N", "Night"),
                    Pair("D", "Day"),
                    Pair("O", "Occasionally")
                ),
                value = signUpViewModel.sleepTime,
                onValueChange = {
                    signUpViewModel.sleepTime = it
                }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.attitude_to_alcohol_label),
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = signUpViewModel.alcoholAttitude,
                onValueChange = {
                    signUpViewModel.alcoholAttitude = it
                }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.attitude_to_smoking_label),
                values = mapOf(
                    Pair("P", "Positive"),
                    Pair("N", "Negative"),
                    Pair("I", "Indifferent")
                ),
                value = signUpViewModel.smokingAttitude,
                onValueChange = {
                    signUpViewModel.smokingAttitude = it
                }
            )
            ButtonsRowMapped(
                label = stringResource(R.string.personality_type_label),
                values = mapOf(
                    Pair("E", "Extraverted"),
                    Pair("I", "Introverted"),
                    Pair("M", "Mixed")
                ),
                value = signUpViewModel.personalityType,
                onValueChange = {
                    signUpViewModel.personalityType = it
                }
            )
            DropdownTextFieldMapped(
                mapOfItems = mapOf(
                    Pair("N", "Neat"),
                    Pair("D", "It Depends"),
                    Pair("C", "Chaos")
                ),
                label = stringResource(R.string.clean_habits_label),
                value = signUpViewModel.cleanHabits,
                onValueChange = {
                    signUpViewModel.cleanHabits = it
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreenButtonPrimary(
                    text = stringResource(R.string.back_button_label),
                ) { navigator.navigate(AboutMeAvatarScreenDestination) }
                GreenButtonPrimary(
                    text = stringResource(R.string.continue_button_label),
                ) { navigator.navigate(InterestsScreenDestination) }
            }
        }
    }
}
