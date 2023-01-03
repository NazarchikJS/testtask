package com.nazartaranyuk.data.data

import com.nazartaranyuk.data.R
import com.nazartaranyuk.data.models.Item


class ItemProvider {

    private val list = mutableListOf(
        Item(image = R.drawable.item_1),
        Item(image = R.drawable.item_1),
        Item(image = R.drawable.item_1),
        Item(image = R.drawable.item_1),
        Item(image = R.drawable.item_1),
        Item(image = R.drawable.item_2),
        Item(image = R.drawable.item_2),
        Item(image = R.drawable.item_2),
        Item(image = R.drawable.item_2),
        Item(image = R.drawable.item_2),
        Item(image = R.drawable.item_3),
        Item(image = R.drawable.item_3),
        Item(image = R.drawable.item_3),
        Item(image = R.drawable.item_3),
        Item(image = R.drawable.item_3),
    )

    fun provideItems(): List<Item> {
        val newList = MutableList(9) {
            list.random()
            list.random()
            list.random()
            list.random()
            list.random()
            list.random()
        }
        return newList
    }
}