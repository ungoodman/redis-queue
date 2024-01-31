const WebSocket = require('websocket').client;
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

var client = new WebSocket();

// const ws = new WebSocket('ws://localhost:8080/ws');

// ws.on('open', () => {
//   console.log('Connected to the server.');
//   ws.send(JSON.stringify({ type: 'subscribe', data: 'test' }));

//   ws.on('message', (message) => {
//     const data = JSON.parse(message);
//     console.log(`${data}`);
//   });
// });

// ws.on('connection', (ws) => {
//     ws.on('message', (message) => {
//         const data = JSON.parse(message);
//         console.log(`${data}`);
//       });
// });

// ws.on('message', (message) => {
//     const data = JSON.parse(message);
//     console.log(`${data}`);
//   });

// ws.on('close', () => {
//   console.log('Connection closed.');
//   process.exit(0);
// });

// ws.on('error', (error) => {
//   console.error(`WebSocket error: ${error}`);
// });

client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    console.log('WebSocket Client Connected');
    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log("Received: '" + message.utf8Data + "'");
        }
    });

    function sendNumber() {
        if (connection.connected) {
            var number = Math.round(Math.random() * 0xFFFFFF);
            connection.sendUTF(number.toString());
            setTimeout(sendNumber, 1000);
        }
    }
    sendNumber();
});

client.connect('ws://localhost:8080/ws', 'echo-protocol');