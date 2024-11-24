package com.jarvis.coursecoroutines._1_basics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.jarvis.coursecoroutines.R
import com.jarvis.coursecoroutines.common.Contributor
import com.jarvis.coursecoroutines.common.gitHub
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ParallelActivity : ComponentActivity() {
  private val handler = Handler(Looper.getMainLooper())
  private lateinit var infoTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_1)
    infoTextView = findViewById(R.id.infoTextView)

    lifecycleScope.launch {
      coroutineScope {
        val deferred1 = async { gitHub.contributors("square", "retrofit") }
        val deferred2 = async { gitHub.contributors("square", "okhttp") }
        showContributors(deferred1.await() + deferred2.await())
      }
    }
    lifecycleScope.launch {
      val initJob = launch {
//        init()
      }
      val contributors1 = gitHub.contributors("square", "retrofit")
      initJob.join()
//      processData()
    }
  }

  private fun coroutinesStyle() = lifecycleScope.launch {
    val contributors1 = gitHub.contributors("square", "retrofit")
    val contributors2 = gitHub.contributors("square", "okhttp")
    showContributors(contributors1 + contributors2)
  }

  private fun completableFutureStyleMerge() {
    val future1 = gitHub.contributorsFuture("square", "retrofit")
    val future2 = gitHub.contributorsFuture("square", "okhttp")
    future1.thenCombine(future2) { contributors1, contributors2 ->
      contributors1 + contributors2
    }.thenAccept { mergedContributors ->
      handler.post {
        showContributors(mergedContributors)
      }
    }
  }

  private fun showContributors(contributors: List<Contributor>) = contributors
    .map { "${it.login} (${it.contributions})" }
    .reduce { acc, s -> "$acc\n$s" }
    .let { infoTextView.text = it }
}