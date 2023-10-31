package com.example.challenge2_binar

import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.repository.MenuRepository
import com.example.challenge2_binar.viewModel.SimpleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val dataModule
        get() = module {
            //DATABASE

            //API
            single { APIClient.instance }

            //REPOSITORY
            factory { MenuRepository(get()) }
        }

    val uiModule
        get() = module {
            viewModel { SimpleViewModel(get()) }
        }
}