package com.evan.dokan.ui.home.notice

import com.evan.bazar.data.db.entities.Notice


interface INoticeUpdateListener {
    fun onUpdate(notice: Notice)

}