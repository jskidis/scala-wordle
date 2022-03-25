package com.skidis.wordle
package hintgen

import scala.collection.mutable

trait CachingWordHintsGenerator extends WordHintsGenerator {
  val patternCache: mutable.Map[(String, String), WordHints] = mutable.Map[(String, String), WordHints]()

  override def generateWordHints(answer: String, word: String): WordHints = {
    patternCache.getOrElseUpdate((answer, word), super.generateWordHints(answer, word))
  }
}
