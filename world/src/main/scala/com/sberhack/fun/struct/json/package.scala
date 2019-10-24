package com.sberhack.fun.struct

import io.circe._
import io.circe.generic.AutoDerivation
import io.circe.jawn.JawnParser

/** Импортим этот объект, чтобы circe сам парсил json'ы */
package object json extends AutoDerivation {
  // json encoders/decoders here

  type SafeJson = Either[ParsingFailure, Json]

  private[this] val parser = new JawnParser

  def parse(input: String): SafeJson = parser.parse(input)

  implicit final class EncoderOps[A](val wrappedEncodeable: A) extends AnyVal {

    def asJson(implicit encoder: Encoder[A]): Json = encoder(wrappedEncodeable)

    def asJsonObject(implicit encoder: ObjectEncoder[A]): JsonObject =
      encoder.encodeObject(wrappedEncodeable)
  }

}
