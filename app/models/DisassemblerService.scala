package models
import scalaj.http._
import play.api.libs.json._
import play.api.Logger

class DisassemblerService {
  def disassemble(version:String, code:String):String = {
    var decomp = ""
    var message = ""
    var error = false

    try {
      val url = play.Play.application.configuration.getString(s"dis.api.${version}")
      val response = Json.parse(Http(url).postForm(Seq("code" -> code)).asString.body)

      (response \ "result").asOpt[String] match {
        case Some(data) => decomp = data
        case _ => ;
      }

      (response \ "errors").asOpt[String] match {
        case Some(data) => decomp = data
        case _ => ;
      }
    }
    catch {
      case e: Exception =>
        Logger.error(e.getMessage())
        message += "Unable to create source.<br>"
        error = true
    }

    if (error) return message
    else return decomp
  }

}
