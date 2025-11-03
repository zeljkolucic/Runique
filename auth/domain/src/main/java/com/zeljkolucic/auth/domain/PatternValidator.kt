package com.zeljkolucic.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}