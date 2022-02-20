package com.skidis.wordle

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

package object frequency {
  case class NgramResponse (ngram: String, timeseries: Seq[Double])
  case class WordFrequency (word: String, frequency: Double)

  trait NgramProtocol extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val NgramFormat: RootJsonFormat[NgramResponse] = jsonFormat2(NgramResponse.apply)
  }
}
