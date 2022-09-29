package com.example.benefits.Logger.infraestructure

import com.example.benefits.Logger.application.GetStudentsService
import com.example.benefits.Logger.application.GetStudentsServiceAsFlux
import com.example.benefits.Logger.application.GetStudentsServiceSergio
import com.example.benefits.Logger.domain.Student
import com.example.benefits.Logger.infraestructure.aspect.LogHandler
import com.example.benefits.Logger.infraestructure.logger.logOnNext
import com.example.benefits.Logger.infraestructure.setup.Logger
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping(path = ["/api"])
class StudentsController(
    private val getStudentsServiceAsFlux: GetStudentsServiceAsFlux,
    private val getStudentsService: GetStudentsService,
    private val getStudentsSergioService: GetStudentsServiceSergio,
    private val logger: Logger
) {

    @GetMapping(path = ["/students-as-flux"])
    suspend fun getStudentsAsFlux(): Flux<MutableList<Student>> {
        logger.info("Controller start -> ${Thread.currentThread().id}")
        val response = getStudentsServiceAsFlux()

        logger.info("Controller finish -> ${Thread.currentThread().id}")
        return response
    }

    @GetMapping(path = ["/students"])
    suspend fun getStudents(): ResponseEntity<List<Student>> {
        logger.info("Controller start -> ${Thread.currentThread().id}")
        val response = getStudentsService()

        logger.info("Controller finish -> ${Thread.currentThread().id}")
        return ResponseEntity.ok().body(response)
    }


    @LogHandler
    @GetMapping(path = ["/students-sergio"])
    suspend fun getStudentsSergio(): ResponseEntity<String> {
        logger.info("Controller start -> ${Thread.currentThread().id}")
        getStudentsSergioService()
        logger.info("Controller finish -> ${Thread.currentThread().id}")
        return ResponseEntity.ok().body("ok")
    }

}