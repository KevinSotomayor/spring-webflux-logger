package com.example.benefits.Logger.application

import com.example.benefits.Logger.domain.Student
import com.example.benefits.Logger.infraestructure.setup.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetStudentsServiceAsFlux(
    private val logger: Logger,
) {
    suspend operator fun invoke(): Flux<MutableList<Student>> {
        val logger2 = LoggerFactory.getLogger(this::class.java)
        logger2.trace("Hi trace")
        logger.info("GetStudentsService invoke start")
        val result = mutableListOf<Student>()
        coroutineScope {
            repeat(100) {
                async {
                    result.add(addStudent(it))
                }
            }
        }
        logger.info("GetStudentsService invoke finish")

        return Flux.just(result)
    }

    private suspend fun addStudent(index: Int): Student {
        delay(2500)
        return Student("Student n$index")
    }
}