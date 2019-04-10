var ws;

function connect()
{
    var deviceid = "0";
    ws = new WebSocket("ws://localhost:20778/iot_device_webapp/iot_devices/" + deviceid);

    ws.onopen = function()
    {
        console.log("Open");
    };

    ws.onerror = function()
    {
        console.log("Error");
    };

    ws.onmessage = function(event)
    {
        var button = document.getElementById("device_1_button");
        var message = JSON.parse(event.data);
    };
}

function send()
{
    var currentState = document.getElementById("device_1_button").textContent;
    
    if (currentState.localeCompare("Off") === 0)
    {
        document.getElementById("device_1_button").textContent = "On";
    }
    else
    {
        document.getElementById("device_1_button").textContent = "Off";
    }
    
    var json = JSON.stringify({
        "content":currentState
    });

    ws.send(json);
}
