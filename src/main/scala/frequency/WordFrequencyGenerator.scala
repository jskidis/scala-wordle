package com.skidis.wordle.frequency

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.io.Source

object WordFrequencyGenerator extends App  {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val answers = Source.fromResource("answers.txt").getLines().map(w => w.toUpperCase()).toVector

  val words = Source.fromResource("guessable-words.txt").getLines().map(w => w.toUpperCase())
  val wordChunks = words.grouped(500)
//  val words: Iterator[String] = Source.fromResource("answers.txt").getLines.map(w => w.toUpperCase())
//  val wordChunks = words.grouped(80)

  val startTime = System.currentTimeMillis()

  val chunkedResponses  = Future.sequence(
    wordChunks.map { wordChunk => WordFrequencyResponse.retrieve(wordChunk) }
  )
  val frequencies = Await.result(chunkedResponses, 60.minute).flatten

  val endTime = System.currentTimeMillis()
  println(s"${(endTime - startTime) / 1000.0} ")

  val filteredFrequencies = frequencies
    .map { wf => if(answers.contains(wf.word)) WordFrequency(wf.word, wf.frequency * 10.0) else wf }
//    .filter { wf => wf.frequency > 1.00E-7 }

  filteredFrequencies.foreach { wf => println(s"${wf.word}, ${wf.frequency}") }

  println(s"Words returned: ${frequencies.size}")

  system.terminate()
}
