package com.example.benefits.Logger.infraestructure

import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.MDC
import org.springframework.context.annotation.Configuration
import reactor.core.CorePublisher
import reactor.core.CoreSubscriber
import reactor.core.publisher.Hooks
import reactor.core.publisher.Operators
import reactor.util.context.Context

// Configuration commented in order to facilitate execution of CoroutineContext
//@Configuration
class MdcContextLifterConfiguration {

    companion object {
        val MDC_CONTEXT_REACTOR_KEY: String = MdcContextLifterConfiguration::class.java.name
    }

    @PostConstruct
    fun contextOperatorHook() {
        Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY, Operators.lift { _, subscriber -> MdcContextLifter(subscriber) })
        //Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY, Operators.) // TODO implement call to MdcContextLifterPublisher
    }

    @PreDestroy
    fun cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY)
    }

}


// Helper that copies the state of Reactor [Context] to MDC on the #onNext function.
class MdcContextLifter<T>(private val coreSubscriber: CoreSubscriber<T>) : CoreSubscriber<T> {

    override fun onNext(t: T) {
        //println(coreSubscriber.currentContext().getOrDefault("correlation_id", "----- no data -----"))
        coreSubscriber.currentContext().copyToMdc()
        coreSubscriber.onNext(t)
    }

    override fun onSubscribe(subscription: Subscription) {
        coreSubscriber.onSubscribe(subscription)
    }

    override fun onComplete() {
        coreSubscriber.onComplete()
    }

    override fun onError(throwable: Throwable?) {
        coreSubscriber.onError(throwable)
    }

    override fun currentContext(): Context {
        return coreSubscriber.currentContext()
    }
}

// TODO Implement way of subscribe to publisher instead of subscriber in order to keep MDC, future test
class MdcContextLifterPublisher<T> (private val corePublisher: CorePublisher<T>): CorePublisher<T>{
    override fun subscribe(s: Subscriber<in T>?) {
        TODO("Not yet implemented")
    }

    override fun subscribe(subscriber: CoreSubscriber<in T>) {
        TODO("Not yet implemented")
    }
}

 // Extension function for the Reactor [Context]. Copies the current context to the MDC, if context is empty clears the MDC.
 // State of the MDC after calling this method should be same as Reactor [Context] state.
private fun Context.copyToMdc() {
    //println(" hash -> ${this.hashCode()}")
    if (!this.getOrEmpty<String>("correlation_id").isEmpty) {
        val map: Map<String, String> = this.stream()
            .collect(Collectors.toMap({ e -> e.key.toString() }, { e -> e.value.toString() }))

        println("Context copy tomdc, $map, ${Thread.currentThread().id}")
        MDC.setContextMap(map)
    } else {
        println("Cleaning MDC  ${Thread.currentThread().id}, ${LocalDateTime.now()}, ${this.hashCode()}")
        MDC.clear()
    }
}
