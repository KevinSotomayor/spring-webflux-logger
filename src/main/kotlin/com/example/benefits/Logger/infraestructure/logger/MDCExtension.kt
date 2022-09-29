package com.example.benefits.Logger.infraestructure.logger

import java.util.*
import java.util.function.Consumer
import org.slf4j.MDC
import reactor.core.publisher.Signal

private const val MDC_REQUEST_ID = "request_id"
private const val MDC_CORRELATION_ID = "correlation_id"

fun finishContext() = MDC.clear()

fun startContext(correlationId: String? = null) {
    finishContext()
    setRequestIdInMdc()
    setCorrelationIdInMdc(correlationId)
}

private fun setRequestIdInMdc() {
    val uniqueId = generateUniqueId()
    println("Id generado: $uniqueId")
    MDC.put(MDC_REQUEST_ID, generateUniqueId())
}

private fun setCorrelationIdInMdc(correlationId: String?) {
    MDC.put(MDC_CORRELATION_ID, correlationId ?: generateUniqueId())
}

fun getRequestId(): String = generateUniqueId()
fun getCorrelationId(): String? = MDC.get(MDC_CORRELATION_ID)

private fun generateUniqueId() = UUID.randomUUID().toString()

fun getContext(): MutableMap<String, String> = MDC.getCopyOfContextMap() ?: mutableMapOf()

fun <T> logOnNext(logStatement: Consumer<T?>): Consumer<Signal<T>>? {
    println("LogOnNext 1 $logStatement")
    return Consumer { signal: Signal<T> ->
        if (!signal.isOnNext) return@Consumer
        val toPutInMdc = signal.context.getOrEmpty<Any>("correlation_id")
        toPutInMdc.ifPresentOrElse(
            { tpim: Any? ->
                println("logOnNext log: $tpim")
                MDC.putCloseable("correlation_id", tpim as String?).use { cMdc ->
                    logStatement.accept(signal.get())
                }
            }
        ) { logStatement.accept(signal.get()) }
    }
}