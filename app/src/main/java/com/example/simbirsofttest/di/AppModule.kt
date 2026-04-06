package com.example.simbirsofttest.di

import com.example.simbirsofttest.data.cache.CacheModule
import com.example.simbirsofttest.data.repository.TaskRepository
import com.example.simbirsofttest.data.repository.TaskRepositoryImpl
import com.example.simbirsofttest.presentation.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CacheModule> { CacheModule.Base(androidContext()) }

    single<TaskRepository> { TaskRepositoryImpl(get()) }

    viewModel { TaskViewModel(get()) }
}