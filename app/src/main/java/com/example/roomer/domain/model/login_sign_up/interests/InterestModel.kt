package com.example.roomer.domain.model.login_sign_up.interests

import com.google.gson.annotations.SerializedName

data class InterestModel(
    @SerializedName("id") val id: String,
    @SerializedName("interest") val interest: String
    )
