package com.example.benefits.Logger.infraestructure.aspect

import java.util.UUID
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

/**
 * This annotation was created to wrap the controller call and run it on
 * a withContext of coroutine that holds the UUID.randomUUID that is created as the context name
 *
 * This test did not work because the context name is lost by using it as the annotation.
 * If used by wrapping it in the controller, it works correctly.
 */
@Aspect
@Component
class CorrelationIdAspect {
    @Around("@annotation(com.example.benefits.Logger.infraestructure.aspect.LogHandler)")
    fun logWithCorrelationId(joinPoint: ProceedingJoinPoint) =
        runBlocking{
            println("Running...")
            withContext(CoroutineName(UUID.randomUUID().toString())) {
                println("Running... with ${kotlin.coroutines.coroutineContext[CoroutineName]?.name}")
                joinPoint.proceed()
            }
        }
}
