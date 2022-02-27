package com.skidis.wordle

trait XordleRunner {
  def puzzleName: String
  def startPhrase: String
  val guessSet: WordSet
  def createStaticProcessor(): XordleProcessor
}
