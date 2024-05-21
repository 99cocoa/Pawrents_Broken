package com.example.pawrents.services.login

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}