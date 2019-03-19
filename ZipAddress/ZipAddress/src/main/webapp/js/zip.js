$(function() {
    $("#sendbtn").click(function() {
        alert("test");
        // 多重送信を防ぐため通信完了までボタンをdisableにする
        var button = $(this);
        button.attr("disabled", true);
        // URL
        var code = "/tools/code/" + $("#code").val();
        // 各フィールドから値を取得してJSONデータを作成
        var data = {
//              name: $("#name").val(),
//              age: parseInt($("#age").val())
        };
        
        // 通信実行
        $.ajax({
            type:"get",                // method = "POST"
            url:code,        // POST送信先のURL
            data:JSON.stringify(data),  // JSONデータ本体
            contentType: 'application/json', // リクエストの Content-Type
            dataType: "json",           // レスポンスをJSONとしてパースする
            success: function(json_data) {   // 200 OK時
                // JSON Arrayの先頭が成功フラグ、失敗の場合2番目がエラーメッセージ
                if (!json_data[0]) {    // サーバが失敗を返した場合
                    alert("Transaction error. " + json_data[1]);
                    return;
                }
                //alert("success");
                console.log(json_data[0]);
                $("#name").val(json_data[0].namePrefecturesKanji + json_data[0].nameCityKanji + json_data[0].nameTownAreaKanji);
                // 成功時処理
                //location.reload();
            },
            error: function() {         // HTTPエラー時
                alert("Server Error. Pleasy try again later.");
            },
            complete: function() {      // 成功・失敗に関わらず通信が終了した際の処理
                button.attr("disabled", false);  // ボタンを再び enableにする
            }
        });
    });
});