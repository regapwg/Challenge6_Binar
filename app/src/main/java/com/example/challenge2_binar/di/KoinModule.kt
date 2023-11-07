package com.example.challenge2_binar.di

import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.database.cartDb.SimpleDatabase
import com.example.challenge2_binar.database.categoryDB.CategoryDatabase
import com.example.challenge2_binar.database.menuDb.MenuDatabase
import com.example.challenge2_binar.repository.LocalData
import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.repository.NetworkData
import com.example.challenge2_binar.repository.CartRepository
import com.example.challenge2_binar.util.ListViewSharedPreference
import com.example.challenge2_binar.util.LoginSharedPreference
import com.example.challenge2_binar.viewModel.DetailViewModel
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.example.challenge2_binar.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val dataModule
        get() = module {
            //DB
            single { SimpleDatabase.getInstance(context = get()) }
            factory { get<SimpleDatabase>().simpleChartDao }

            single { MenuDatabase.getInstance(context = get())}
            factory { get<MenuDatabase>().menuDao }

            single { CategoryDatabase.getInstance(context = get())}
            factory { get<CategoryDatabase>().categoryDao }

            //API
            single { APIClient.instance }

            //REPOSITORY
            factory { MenuRepository(get(),get()) }
            factory { CartRepository(get()) }
            factory { LocalData(get(), get()) }
            factory { NetworkData(get()) }

            //sharedpref
            single {LoginSharedPreference(context = get())}
            single { ListViewSharedPreference(context = get()) }
        }

    val uiModule
        get() = module {
            viewModel { HomeViewModel(get()) }
            viewModel { DetailViewModel(get()) }
            viewModel { KeranjangViewModel(get()) }
        }
}