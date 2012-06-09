function Javabytes() {
  var inputMirror;
  var outputMirror;

  this.submitCode= function() {
    $('#disassemble_button').attr('disabled', 'disabled');
    outputMirror.setValue("Running...");

    var rawText = inputMirror.getValue();
    if (!rawText) return;
    var jsonText = JSON.stringify({"code":rawText});
    $.ajax({
      type: 'POST',
      url: '/',
      dataType: 'text',
      contentType: 'application/json; charset=utf-8',
      data: jsonText,
      complete: function(xhr, status) {
        $('#disassemble_button').removeAttr('disabled');
      },
      success: function(code) {
        code = code.replace(/<br>/g, "\n");
        outputMirror.setValue(code);
      },
      error: function(xhr, status, errorThrown) {
        console.error('Error compiling')
      }
    });
  };
 
  this.onReady= function() {
    inputMirror = CodeMirror.fromTextArea($("#inputTextArea").get(0), {lineNumbers:true});
    outputMirror = CodeMirror.fromTextArea($("#outputTextArea").get(0), {lineNumbers:true, readOnly :true});
  };
}
var javabytes = new Javabytes();
