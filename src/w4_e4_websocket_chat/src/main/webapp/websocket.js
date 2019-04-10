var ws;

function connect() {
   var username = document.getElementById("username").value;
   ws = new WebSocket("ws://localhost:20778/WebSocketDemoNoDisconnect-master/chat/" + username);

    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
        log.scrollTop = log.scrollHeight;
    };
}

function disconnect() {
    ws.onclose = function(event)
    {
      var log = document.getElementById("log");
      log.innerHTML += "Disconnected.\n";
      log.scrollTop = log.scrollHeight;
    };
    
    ws.close();
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content":content
    });

    ws.send(json);
}