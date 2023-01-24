package com.example.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import java.nio.file.Files.write

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("foo") {
            call.respondText("bar")
        }
        get("baz") {
            call.respondText("zap")
        }
        get("buzz") {
            call.respondBytes("buzz".toByteArray())
        }
    }
}