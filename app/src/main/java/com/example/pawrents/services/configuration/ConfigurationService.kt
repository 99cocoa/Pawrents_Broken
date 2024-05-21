package com.example.pawrents.services.configuration

import com.example.pawrents.R
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.example.pawrents.services.configuration.ConfigurationService
import com.google.firebase.remoteconfig.get
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConfigurationServiceImpl @Inject constructor(): ConfigurationService {
    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if(BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings {minimumFetchIntervalInSeconds = 0}
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(R.xml.remote_config)
    }

    override suspend fun fetchConfiguration(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    override val isShowTaskEditButtonConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()

    companion object{
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}