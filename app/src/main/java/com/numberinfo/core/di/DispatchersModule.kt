package com.numberinfo.core.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val SINGLE_DISPATCHER = "Single"

val dispatchersModule = module {
    single(named(DispatcherType.DEFAULT)) { Dispatchers.Default }
    single(named(DispatcherType.IO)) { Dispatchers.IO }
    single(named(DispatcherType.MAIN)) { Dispatchers.Main }
    single(named(DispatcherType.IMMEDIATE)) { Dispatchers.Main.immediate }
    single(named(DispatcherType.SINGLE)) { newSingleThreadContext(SINGLE_DISPATCHER) }
}