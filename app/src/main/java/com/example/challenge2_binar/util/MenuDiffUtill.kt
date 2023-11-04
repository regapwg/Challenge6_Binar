package com.example.challenge2_binar.util

import androidx.recyclerview.widget.DiffUtil

class MenuDiffUtil<T>(
    private val oldMenuList: List<T>,
    private val newMenuList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldMenuList.size
    }

    override fun getNewListSize(): Int {
        return newMenuList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMenuList[oldItemPosition] === newMenuList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMenuList[oldItemPosition] == newMenuList[newItemPosition]
    }

}