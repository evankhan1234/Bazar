package com.evan.bazar.ui.home.newsfeed.publicpost

interface IPublicPostLikeListener {
  fun onCount(count:Int?,type:Int,id:Int)
}