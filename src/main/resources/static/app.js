var stompClient = null;
var currentRoom = "1";
var subscription = null;  // 현재 구독을 관리하는 변수

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
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
        subscribeToRoom(currentRoom); // 현재 방에 구독
        loadChatHistory(currentRoom); // 현재 방의 채팅 기록 로드
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function subscribeToRoom(roomId) {
    if (subscription !== null) {
        subscription.unsubscribe();  // 이전 방에 대한 구독 해제
    }
    subscription = stompClient.subscribe('/topic/greetings/' + roomId, function (greeting) {
        showGreeting(roomId, JSON.parse(greeting.body).content);
    });
}

function sendMessage() {
    var chatMessage = {
        roomId: currentRoom,
        content: $("#content").val(),
        writerId: 123,  // Replace with dynamic user ID if needed
        createdDate: new Date()
    };

    // 메시지를 서버로 전송
    stompClient.send("/app/hello", {}, JSON.stringify(chatMessage));

    // 입력 필드를 초기화
    $("#content").val('');
}

function showGreeting(room, message) {
    $("#greetings").append("<tr><td>Room " + room + "</td><td>" + message + "</td></tr>");
}

function loadChatHistory(roomId) {
    $.ajax({
        url: "/find/chat/list/" + roomId,
        method: "GET",
        success: function (data) {
            $("#greetings").html(""); // 기존 메시지 클리어
            data.forEach(function (message) {
                showGreeting(roomId, message.content);
            });
        },
        error: function (error) {
            console.error("Failed to load chat history:", error);
        }
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() {
        connect();
    });
    $("#disconnect").click(function() {
        disconnect();
    });
    $("#send").click(function() {
        sendMessage();
    });
    $("#roomId").change(function() {
        currentRoom = $(this).val();
        if (stompClient !== null && stompClient.connected) {
            subscribeToRoom(currentRoom);  // 새로운 방에 구독
            loadChatHistory(currentRoom);  // 새로운 방의 채팅 기록 로드
        }
    });
});