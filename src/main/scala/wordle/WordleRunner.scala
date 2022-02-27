package com.skidis.wordle
package wordle

import strategy.{ClusterStrategy, ReverseClusterStrategy}

import scala.io.Source

trait WordleRunner extends XordleRunner {
  override def puzzleName: String = "Wordle"
}

trait WordleStandardRunner extends WordleRunner with WordReader {
  override def startPhrase: String = "SLATE"
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("word-frequency-filtered.txt"))
  override def createStaticProcessor(): XordleProcessor = new WordleInteractiveProcessor with ClusterStrategy
}

trait WordleAnswerOnlyRunner extends WordleStandardRunner {
  override lazy val guessSet: WordSet = readWords(Source.fromResource("answers.txt"))
}

trait WordleReverseRunner extends WordleRunner with WordReader {
  override def startPhrase: String = "JAZZY"
  override lazy val guessSet: WordSet = readWordFrequencies(Source.fromResource("words-filtered-by-frequency.txt"))
  override def createStaticProcessor(): XordleProcessor = new WordleInteractiveProcessor with ReverseClusterStrategy
}

object WordleStandardInteractiveRunner extends App
  with XordleInteractiveRunner with WordleStandardRunner {
  runInteractive()
}

object WordleAnswerOnlyInteractiveRunner extends App
  with XordleInteractiveRunner with WordleAnswerOnlyRunner {
  runInteractive()
}

object WordleReverseInteractiveRunner extends App
  with XordleInteractiveRunner with WordleReverseRunner {
  runInteractive()
}