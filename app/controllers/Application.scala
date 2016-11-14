package controllers

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.Logger
import models._

object Application extends Controller {

  implicit val codeReads: Reads[Code] = (
    (JsPath \ "code").read[String] and
    (JsPath \ "version").read[String]
  )(Code.apply _)

  def index = Action {
    Ok(views.html.index(""))
  }

  def submitCode = Action(BodyParsers.parse.json) { request =>
    Logger.debug("submit code")

    val codeResult = request.body.validate[Code]
    codeResult.fold(
      errors => {
        BadRequest(Json.obj("status" ->"ERROR", "message" -> JsError.toFlatJson(errors)))
      },
      code => {
        val helper = new DisassemblerService()
        val diss = helper.disassemble(code.version, code.code)
        Ok(diss)
      }
    )
  }

}
