package com.evan.bazar.data.network.responses

import com.evan.bazar.data.db.entities.Quote


data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)