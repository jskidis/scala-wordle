package com.skidis.wordle
package wordle

case class SimpleWordleWord(text: String) extends XrdleWord

case class WordleWordFrequencies(text: String, frequency: Double) extends XrdleFreqWord

