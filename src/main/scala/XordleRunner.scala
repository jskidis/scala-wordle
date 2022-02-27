package com.skidis.wordle

trait XordleRunner {
  def puzzleName: String
  def startGuess: String
  val guessSet: WordSet
  val answerSet: WordSet
  def createInteractiveProcessor(): InteractiveProcessor
  def createSimulationProcessor(answer: String): SimulationProcessor
}
