var stompClient = null;

(function () {
    console.log("Teste");
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (str) {
            console.log(str);
            showGreeting(JSON.parse(str.body).states);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName(status) {
    stompClient.send("/app/hello", {}, status);
}

function showGreeting(states) {

    var content = "";
    for (i=0; i<states.length; i++) {
        console.log("Hey: "+states[i][0]);
        content +=
            '<tr>'+
            '<td>'+states[i][0]+'</td>'+
            '<td>'+states[i][1]+'</td>'+
            '<td>'+states[i][2]+'</td>'+
            '<td>'+states[i][3]+'</td>'+
            '<td>'+states[i][4]+'</td>'+
            '<td>'+states[i][5]+'</td>'+
            '<td>'+states[i][6]+'</td>'+
            '<td>'+states[i][7]+'</td>'+
            '<td>'+states[i][8]+'</td>'+
            '<td>'+states[i][9]+'</td>'+
            '<td>'+states[i][10]+'</td>'+
            '<td>'+states[i][11]+'</td>'+
            '<td>'+states[i][12]+'</td>'+
            '<td>'+states[i][13]+'</td>'+
            '<td>'+states[i][14]+'</td>'+
            '<td>'+states[i][15]+'</td>'+
            '<td>'+states[i][16]+'</td>'+
            '</tr>';
    }

    $("#table").html(content);

}

$(function () {
    console.log("yoyoy");
    var state = "both";
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    //$( "#connect" ).click(function() { connect(); });
    connect();
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    setInterval(function(){ sendName(state); }, 8000);

    $('input:radio[name=optradio]').click(function() {
        var values = $('input:radio[name=optradio]:checked').map(function() {
          return this.value
        }).get();
        
        //console.log("yoyoy");
        //console.log(values);
        sendName(values);
        state = values;
        // do something with values array
      })
});

