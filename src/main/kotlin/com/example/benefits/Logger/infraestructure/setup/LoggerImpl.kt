package com.example.benefits.Logger.infraestructure.setup

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineName
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LoggerImpl : Logger {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override suspend fun info(data: String) =
        logger.info(mapOf("correlationId" to coroutineContext[CoroutineName]?.name, "message" to data).toString())

    override suspend fun warn(data: String) =
        logger.warn(mapOf("correlationId" to coroutineContext[CoroutineName]?.name, "message" to data).toString())

    override suspend fun error(data: String, throwable: Throwable) = logger.error(
        mapOf("correlationId" to coroutineContext[CoroutineName]?.name, "message" to data).toString(),
        throwable
    )
}
