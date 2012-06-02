package controllers

import play.api._
import play.api.mvc._
import scala.sys.process._
import play.libs.Json._

object Application extends Controller {
  
  def index = Action {
   "javac tmp/Hello.java".!
    var decomp = "javap -c tmp/Hello".!!
    var decomps = decomp.split("\n");
    decomps = decomps.filter(d => !d.matches("Compiled from .*"))

    //Ok(views.html.index(decomps))
    Ok(views.html.index("hi"))
  }

  def submitCode = Action(parse.json) { request =>
    (request.body \ "code").asOpt[String].map { code =>
      Ok("{\"code\":\"" + code + "\"}")
    }.getOrElse {
      BadRequest("Missing parameter [code]")
    }
    //case JsObject(fields) => Ok("received: " + fields.toMap + "\n")
    //case _ => Ok("got something else: " + request.body + "\n")
    //Ok("received: " + fields.toMap + "\n")
  }
  
}
