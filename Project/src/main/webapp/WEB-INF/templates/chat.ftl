<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>
        function sendMessage(userId, text, chatId) {
            let body = {
                userId: userId,
                text: text,
                chatId: chatId
            };

            $.ajax({
                url: "/rest/messages",
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json"
            });
        }

        // LONG POLLING
        function receiveMessage(userId) {
            $.ajax({
                url: "/rest/messages?userId=" + userId,
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $('#messages').first().after('<li class="list-group-item">' + response[0]['userId'] + ": " + response[0]['text'] + '</li>');
                    receiveMessage(userId);
                }
            })
        }
    </script>
</head>
<body>
<body onload="receiveMessage('${userId}')">
<h1>Chat</h1>
<div>
    <h3>Input msg:</h3>
    <input id="message" class="messageInput" placeholder="Your message">
    <button onclick="sendMessage('${userId}' ,$('#message').val(), '${chatId}')" class="btn btn-dark">Send</button>
</div>
<div>
    <h4>Last messages</h4>
    <ul id="messages">

    </ul>
    <#list chatMessages as message>
        <li>${message.userId} :=: ${message.text}</li>
    </#list>
</div>
</body>
</html>