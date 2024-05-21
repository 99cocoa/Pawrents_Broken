package com.example.pawrents.services

import com.example.pawrents.services.account.AccountService
import com.example.pawrents.services.account.AccountServiceImpl
import com.example.pawrents.services.configuration.ConfigurationService
import com.example.pawrents.services.configuration.ConfigurationServiceImpl
import com.example.pawrents.services.login.LogService
import com.example.pawrents.services.login.LogServiceImpl
import com.example.pawrents.services.storage.StorageService
import com.example.pawrents.services.storage.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}