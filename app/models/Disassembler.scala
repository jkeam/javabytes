package models
import java.util.UUID
import java.io._
import scala.sys.process._
import scalax.io._

class Disassembler {

  def disassemble(code:String):String = {
    var error = false
    var message = ""
    //generate UUID
    val rawId = "t" + UUID.randomUUID().toString()
    val id = rawId.replaceAll("-", "")

    //create file in tmp/
    val fw = new FileWriter("tmp/" + id + ".java")

    //write code out to that file
    try {
      fw.write("public class " + id + " { public static void test(){")
      fw.write(code)
      fw.write("}}")
      fw.flush()
    }
    catch {
      case e: Exception =>
       message += "Unable to create source.<br>"
       error = true
    }
    finally {
      fw.close()
    }
    if (error) return message

    //run compiler
    var compilation = ""
    try {
      compilation = ("javac tmp/" + id + ".java").!!
    }
    catch {
      case e: Exception =>
       message += "Unable to compile.<br>"
       error = true
       quietRemoveFile(id)
       return message
    }

    try {
    var decomp = ("javap -c tmp/" + id).!!
    //decomp = decomp.replaceAll("Compiled from " + id, "")
    //decomp = decomp.replaceAll("\u007D", "\u007D\u007D")
    //decomp = decomp.replaceAll("\u007B", "\u007B\u007B")

    decomp = decomp.replaceAll("\\{", "{{")
    decomp = decomp.replaceAll("\\}", "}}")
    decomp = decomp.replaceAll("\"", "'")
    decomp = decomp.replaceAll("\n", "<br>")
    decomp = decomp.replaceAll("\t", " ")
    return decomp.toString()
    }
    catch {
      case e: Exception =>
       message += "Unable to disassemble.\n"
       error = true
       quietRemoveFile(id)
       return message
    }

    //remove
    try {
      removeFile(id)
    }
    catch {
      case e: Exception =>
       message += "Unable to cleanup.<br>"
       error = true
       return message
    }

    if (error) return message

    return "An unknown error has ocurred"
  }

  def quietRemoveFile(id:String) {
    try {
      val sourceFile = new File("tmp/" + id + ".java")
      sourceFile.delete()

      val classFile = new File("tmp/" + id + ".class")
      classFile.delete()
    }
    catch {
      case e: Exception => System.out.println("Couldn't remove file")
    }
  }

  def removeFile(id:String) {
    val sourceFile = new File("tmp/" + id + ".java")
    sourceFile.delete()

    val classFile = new File("tmp/" + id + ".class")
    classFile.delete()
  }

}
