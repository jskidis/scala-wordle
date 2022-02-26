package com.skidis.wordle.frequency

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer

import scala.concurrent.{ExecutionContextExecutor, Future}

object WordFrequencyResponse extends NgramProtocol {
  val baseUri: Uri = Uri("https://books.google.com/ngrams/json").
    withQuery(Query("year_start" -> "2019")).
    withQuery(Query("year_end" -> "2019")).
    withQuery(Query("corpus" -> "28")).
    withQuery(Query("smoothing" -> "0"))

  def retrieve(words: Seq[String])
    (implicit system: ActorSystem, materializer: Materializer, executionContext: ExecutionContextExecutor)
  : Future[Seq[WordFrequency]] = {

    val uri = baseUri.withQuery(Query("content" -> words.mkString(",")))
    for {
      response <- Http().singleRequest(HttpRequest(uri = uri))
      unmarshalled <- Unmarshal(response.entity).to[Seq[NgramResponse]]
    } yield unmarshalled.map {
      ngramFrequency => WordFrequency(ngramFrequency.ngram, ngramFrequency.timeseries.sum)
    }
  }
}
