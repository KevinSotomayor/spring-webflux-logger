package com.example.benefits.Logger.infraestructure.aspect

import java.util.UUID
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

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
