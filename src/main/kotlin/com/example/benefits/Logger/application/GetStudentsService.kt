package com.example.benefits.Logger.application

import com.example.benefits.Logger.domain.Student
import com.example.benefits.Logger.infraestructure.setup.Logger
import kotlinx.coroutines.delay
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetStudentsService(
    private val logger: Logger
) {
    suspend operator fun invoke(): List<Student> {
        logger.info("GetStudentsService invoke start")
        val result = getStudentsFromServer()
        logger.info("GetStudentsService invoke finish")
        return result
    }

    suspend fun getStudentsFromServer(): List<Student> {
        val students = mutableListOf<Student>()
        delay(500)
        students.add(Student("Kevin"))
        delay(500)
        students.add(Student("Bea"))
        delay(100)
        students.add(Student("Sergio"))
        delay(1)
        students.add(Student("Gui"))
        return students
    }
}