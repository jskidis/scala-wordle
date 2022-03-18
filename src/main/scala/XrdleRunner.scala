package com.skidis.wordle


trait GuessAndAnswerSets {
  val guessSet: WordSet
  val answerSet: WordSet
}

trait InteractiveProcessorFactory {
  def createInteractiveProcessor(): InteractiveProcessor
}

trait SimulationProcessFactory {
  def createSimulationProcessor(startGuesses: Seq[String] = Nil): SimulationProcessor
}

trait FirstGuessOptFactory {
  def createFirstGuessOptimizer(): FirstGuessOptimizer
}

trait XrdleRunner extends GuessAndAnswerSets {
  def puzzleName: String
}
