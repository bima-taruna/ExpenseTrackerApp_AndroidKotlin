package com.bima.expensetrackerapp.common

interface BaseUseCase<In,Out> {
    fun execute(input:In):Out
}