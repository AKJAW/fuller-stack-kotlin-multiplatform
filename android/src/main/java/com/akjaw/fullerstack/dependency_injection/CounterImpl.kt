package com.akjaw.fullerstack.dependency_injection

class CounterImpl : Counter {
    private var counter = 0

    override fun getAndIncrement(): Int {
        counter ++
        return counter
    }
}