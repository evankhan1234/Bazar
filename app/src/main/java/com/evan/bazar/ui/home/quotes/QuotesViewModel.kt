package com.evan.bazar.ui.home.quotes

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.evan.bazar.data.db.entities.Quote
import com.evan.bazar.data.repositories.QuotesRepository
import com.evan.bazar.util.Coroutines
import com.evan.bazar.util.lazyDeferred


class QuotesViewModel(
   private val repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
    fun saveUser(quote: Quote){
        Coroutines.main {
            Log.e("sdfds","Sds"+quote.created_at)
            repository.saveUser(quote)
        }

    }
}
