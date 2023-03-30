package com.numberinfo.core.di

import androidx.room.Room
import com.numberinfo.core.db.AppDatabase
import com.numberinfo.features.home.domain.useCase.GetAllFactsUseCase
import com.numberinfo.features.home.domain.useCase.GetFactUseCase
import com.numberinfo.features.home.presentation.HomeViewModel
import com.numberinfo.network.NumberRepository
import com.numberinfo.network.NumbersAPI
import com.numberinfo.network.RetrofitClient
import com.numberinfo.network.utils.NumberRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val koinModule = module {
    single { RetrofitClient.getInstance().create(NumbersAPI::class.java) }
    single<NumberRepository> { NumberRepositoryImpl(get()) }
    single { get<AppDatabase>().numbersDao() }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database")
            .build()
    }

    factory { GetFactUseCase(get(), get(), get(named(DispatcherType.IO))) }
    factory { GetAllFactsUseCase(get()) }

    viewModel { HomeViewModel(get(), get()) }
}