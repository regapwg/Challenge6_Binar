package com.example.challenge2_binar.di

import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.database.SimpleDatabase
import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.repository.Repository
import com.example.challenge2_binar.viewModel.DetailViewModel
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.example.challenge2_binar.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val dataModule
        get() = module {
            single { SimpleDatabase.getInstance(context = get()) }
            factory { get<SimpleDatabase>().simpleChartDao }

            //API
            single { APIClient.instance }

            //REPOSITORY
            factory { MenuRepository(get()) }
            factory { Repository(get()) }
        }

    val uiModule
        get() = module {
            viewModel { HomeViewModel(get()) }
            viewModel { DetailViewModel(get()) }
            viewModel { KeranjangViewModel(get()) }
        }
}