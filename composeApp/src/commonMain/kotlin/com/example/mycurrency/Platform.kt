package com.example.mycurrency

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
