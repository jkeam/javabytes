function Codebytes() {
  var inputMirror;
  var outputMirror;

  this.submitCode = function() {
    $('#disassemble_button').attr('disabled', 'disabled');
    outputMirror.setValue("Running...");

    var rawText = inputMirror.getValue();
    var version = $('#javaVersion').val();
    if (!rawText || !version) return;
    $.ajax({
      type: 'POST',
      url: '/',
      data: JSON.stringify({
        "code": rawText,
        "version": version
      }),
      dataType: 'text',
      contentType: 'application/json; charset=utf-8'
    })
    .always(function(xhr, status) {
      $('#disassemble_button').removeAttr('disabled');
    })
    .done(function(code) {
      code = code.replace(/<br>/g, "\n");
      outputMirror.setValue(code);
    })
    .fail(function(xhr, status, errorThrown) {
      debugger;
      console.error('Error compiling');
    });
  };
 
  this.onReady= function() {
    inputMirror = CodeMirror.fromTextArea($("#inputTextArea").get(0), {
      lineNumbers: true,
      matchBrackets: true,
      tabSize: 2,
      mode: "text/x-java"
    });
    outputMirror = CodeMirror.fromTextArea($("#outputTextArea").get(0), {
      lineNumbers: true,
      readOnly: true,
      matchBrackets: true,
      tabSize: 2,
      mode: "text/x-java"
    });
  };
}
var codebytes = new Codebytes();
