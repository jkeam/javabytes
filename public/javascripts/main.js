function Codebytes() {
  var inputMirror;
  var outputMirror;

  this.submitCode = function() {
    var rawText = inputMirror.getValue();
    var version = $('#javaVersion').val();
    if (!rawText || !version) {
      outputMirror.setValue("Please make sure code and version are not blank.");
      return;
    }

    $('#disassemble_button').attr('disabled', 'disabled');
    outputMirror.setValue("Running...");

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
      console.error('Error');
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
