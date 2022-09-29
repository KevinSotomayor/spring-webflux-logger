package com.example.benefits.Logger.application

import com.example.benefits.Logger.domain.Student
import com.example.benefits.Logger.infraestructure.setup.Logger
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetStudentsServiceSergio(
    private val logger: Logger
) {
    suspend operator fun invoke() {
        getStudentsFromServer()
    }

    suspend fun getStudentsFromServer() {
        logger.info("Starting getStudentsFromServer")
        getDelay()
        delay(800)
        logger.info("Ending getStudentsFromServer")
        println("inside getStudentsFromServer ... with ${kotlin.coroutines.coroutineContext[CoroutineName]?.name}")
    }

    suspend fun getDelay() {
        delay(10000)
    }
}