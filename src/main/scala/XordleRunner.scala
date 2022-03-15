package com.skidis.wordle


trait GuessAndAnswerSets {
  val guessSet: WordSet
  val answerSet: WordSet
}

trait InteractiveProcessorFactory {
  def createInteractiveProcessor(): InteractiveProcessor
}

trait SimulationProcessFactory {
  def createSimulationProcessor(): SimulationProcessor
}

trait FirstGuessOptFactory {
  def createFirstGuessOptimizer(): FirstGuessOptimizer
}

trait XordleRunner extends GuessAndAnswerSets {
  def puzzleName: String
}
