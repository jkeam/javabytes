package models
import java.util.UUID
import java.io._
import scala.sys.process._
import scalax.io._

class Disassembler {

  def disassemble(code:String):String = {
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
    } finally {
      fw.close()
    }

    //run compiler
   /*
   "javac tmp/Hello.java".!
    var decomp = "javap -c tmp/Hello".!!
    var decomps = decomp.split("\n");
    decomps = decomps.filter(d => !d.matches("Compiled from .*"))
    */

    ("javac tmp/" + id + ".java").!
    var decomp = ("javap -c tmp/" + id).!!
    //decomp = decomp.replaceAll("Compiled from " + id, "")
    //decomp = decomp.replaceAll("\u007D", "\u007D\u007D")
    //decomp = decomp.replaceAll("\u007B", "\u007B\u007B")

    decomp = decomp.replaceAll("\\{", "{{")
    decomp = decomp.replaceAll("\\}", "}}")
    decomp = decomp.replaceAll("\"", "'")
    decomp = decomp.replaceAll("\n", "<br>")
    decomp = decomp.replaceAll("\t", " ")

    //if no errors
      //run disassembler
    //else
      //take output from compiler and send it back

    //remove
    removeFile(id)
    return decomp.toString()

  }


  def removeFile(id:String) {
    val sourceFile = new File("tmp/" + id + ".java")
    sourceFile.delete()

    val classFile = new File("tmp/" + id + ".class")
    classFile.delete()
  }

}
