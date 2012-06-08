function Javabytes() {
  var inputMirror;
  var outputMirror;

  this.submitCode= function() {
    var rawText = inputMirror.getValue();
    if (!rawText) return;
    var jsonText = JSON.stringify({"code":rawText});
    $.ajax({
      type: 'POST',
      url: '/',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: jsonText,
      success: function(json) {
        var code = json.code;
        code = code.replace(/<br>/g, "\n");
        outputMirror.setValue(code);
      },
      failure: function(xhr, textStatus, errorThrown) {
        window.alert('failure: ');
      }
    });
  };
 
  this.onReady= function() {
    inputMirror = CodeMirror.fromTextArea($("#inputTextArea").get(0), {lineNumbers:true});
    outputMirror = CodeMirror.fromTextArea($("#outputTextArea").get(0), {lineNumbers:true, readOnly :true});
  };
}
var javabytes = new Javabytes();
