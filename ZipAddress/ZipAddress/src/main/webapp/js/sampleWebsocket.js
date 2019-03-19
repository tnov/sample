$(function() {
    var ws = new WebSocket('ws://localhost:8080/SampleRestApplication/websocket');

    ws.onopen = function() {
        $('#send').on('click', function() {
//            var message = {name: "Hoge"};
//            ws.send(JSON.stringify(message));
            ws.send("test");
        });

        $('#close').on('click', function() {
            ws.close();
        });
    };

    ws.onmessage = function(e) {
        console.log(e.data);
    };
});