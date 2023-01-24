package com.example.plugins;

import com.example.module

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test

class RoutingKtTest {

    @Test
    fun testGet() = testApplication {
        application {
            module()
        }
    }
}