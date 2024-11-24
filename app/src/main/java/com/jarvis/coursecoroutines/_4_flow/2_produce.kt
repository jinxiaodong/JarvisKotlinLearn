package com.jarvis.coursecoroutines._4_flow

import com.jarvis.coursecoroutines.common.gitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val receiver = scope.produce {
    while (isActive) {
      val data = gitHub.contributors("square", "retrofit")
      send(data)
    }
  }
  launch {
    delay(5000)
    while (isActive) {
      println("Contributors: ${receiver.receive()}")
    }
  }
  delay(10000)
}