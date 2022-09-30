package com.example.benefits.Logger.application

import com.example.benefits.Logger.infraestructure.setup.Logger
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import org.springframework.stereotype.Service

@Service
class GetStudentsServiceCoroutineContext(
    private val logger: Logger
) {
    suspend operator fun invoke() {
        getStudentsFromServer()
    }

    suspend fun getStudentsFromServer() {
        logger.info("Starting getStudentsFromServer [HASH: ${coroutineContext.hashCode()}]")
        getDelay()
        delay(800)
        logger.info("Ending getStudentsFromServer")
        println("inside getStudentsFromServer ... [HASH: ${coroutineContext.hashCode()}]")
    }

    suspend fun getDelay() {
        delay(10000)
    }
}