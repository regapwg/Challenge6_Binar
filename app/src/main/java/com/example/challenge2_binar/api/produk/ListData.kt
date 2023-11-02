package com.example.challenge2_binar.api.produk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListData(
    val alamat_resto: String?,
    val detail: String?,
    val harga: Int?,
    val harga_format: String?,
    val image_url: String?,
    val nama: String?
): Parcelable