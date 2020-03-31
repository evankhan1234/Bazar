package com.evan.bazar.ui.home.quotes

import com.evan.bazar.R
import com.evan.bazar.data.db.entities.Quote
import com.evan.bazar.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem


class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}