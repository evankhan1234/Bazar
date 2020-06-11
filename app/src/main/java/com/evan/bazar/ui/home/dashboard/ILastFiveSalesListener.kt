package com.evan.bazar.ui.home.dashboard

import com.evan.bazar.data.db.entities.LastFiveSales

interface ILastFiveSalesListener {

    fun onLast(data: MutableList<LastFiveSales>)
}