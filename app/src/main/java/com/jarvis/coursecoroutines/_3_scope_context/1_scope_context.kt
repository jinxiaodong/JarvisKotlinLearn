package com.jarvis.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.ContinuationInterceptor

fun main() = runBlocking<Unit> {
  val context = Dispatchers.IO + Job() + Job()
  val scope = CoroutineScope(Dispatchers.IO)
  val job = scope.launch {
    this.coroutineContext[Job]
    coroutineContext.job
    coroutineContext[ContinuationInterceptor]
  }
  delay(10000)
}