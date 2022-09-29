package com.example.benefits.Logger.infraestructure.setup

interface Logger {
    suspend fun info(data: String)
    suspend fun warn(data: String)
    suspend fun error(data: String, throwable: Throwable)
}
