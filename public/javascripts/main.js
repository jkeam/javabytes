function submitCode() {
            var jsonText = JSON.stringify({"code":"System.out.println(\"hi\");"});
            $.ajax({
                type: 'POST',
                url: '/',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: jsonText,
                success: function(json) {
                  console.log(json.code)
                  alert(json.code)
                },
                failure: function(xhr, textStatus, errorThrown) {
                  window.alert('failure: ');
                }
            });
          }

function onReady() {
    var inputMirror = CodeMirror.fromTextArea($("#inputTextArea").get(0), {lineNumbers:true});
    var outputMirror = CodeMirror.fromTextArea($("#outputTextArea").get(0), {lineNumbers:true, readOnly :true});
}

