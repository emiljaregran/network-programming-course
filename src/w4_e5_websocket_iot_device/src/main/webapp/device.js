var ws;

function connect() {
    ws = new WebSocket("ws://localhost:20778/iot_device_webapp/iot_devices/" + "1");
    //sendState("off");

    ws.onmessage = function(event) {
        var log = document.getElementById("currentState");
        var message = JSON.parse(event.data);
        console.log(message.content);
        document.getElementById("currentState").innerHTML = message.content;
        //sendState(message.content);
    };
}

function sendState(state) {
    var json = JSON.stringify({
        "state":state
    });

    ws.send(json);
}

