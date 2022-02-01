package com.skidis.wordle

import scala.io.Source

object WordleProcessorIntegrationTest extends App {
  val candidateWords = WordReader.read(Source.fromResource("words.txt"))
  val answers = WordReader.read(Source.fromResource("answers.txt"))

  val results: Set[(Option[(String, Int)], String, Int)] = answers.zipWithIndex.map { case (answer, index) =>
    val colorPatternGenerator: ColorPatternGenerator = WordColorPatternGenerator.generateCurryable(answer)
    val result = WordleProcessor.process(colorPatternGenerator, debugOutput = false)(candidateWords)

    result match {
      case None => println(s"$index, **NO RESULT**")
      case Some((guess, _)) if guess != answer => println(s"$index, WRONG GUESS(answer=$answer guess=$guess")
      case Some((guess, num)) => println(s"$index, $guess, $num")
    }

    (result, answer, index)
  }
}
