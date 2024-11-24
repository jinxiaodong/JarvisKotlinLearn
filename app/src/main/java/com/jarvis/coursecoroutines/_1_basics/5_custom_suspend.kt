package com.jarvis.coursecoroutines._1_basics

import com.jarvis.coursecoroutines.common.Contributor
import com.jarvis.coursecoroutines.common.gitHub

suspend fun getRetrofitContributors(): List<Contributor> {
  return gitHub.contributors("square", "retrofit")
}

suspend fun customSuspendFun() {

}