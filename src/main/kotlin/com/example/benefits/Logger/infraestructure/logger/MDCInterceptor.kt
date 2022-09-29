package com.example.benefits.Logger.infraestructure.logger

import java.util.function.BiConsumer
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context


//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCInterceptor : WebFilter {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun postConstruct() {
        println("Bean MDCInterceptor")
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        logger.info("Starting request")

        return chain.filter(exchange)
            .doOnRequest { println("requesting: ${request.uri}") }
            .contextWrite {
                 it.put("correlation_id", getRequestId())
            }

    }
}

