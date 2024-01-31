const WebSocket = require('ws');
const http = require('http');

const server = http.createServer();
const wss = new WebSocket.Server({ server });

const clients = new Set();

wss.on('connection', (ws) => {
  clients.add(ws);

  ws.on('message', (message) => {
    const data = JSON.parse(message);

    if (data.type === 'username') {
      ws.username = data.data;
    } else if (data.type === 'message') {
      // Broadcast the message to all clients
      broadcast({ username: ws.username, message: data.data });
    }
  });

  ws.on('close', () => {
    clients.delete(ws);
  });
});

function broadcast(data) {
  const message = JSON.stringify(data);
  clients.forEach((client) => {
    client.send(message);
  });
}

server.listen(3000, () => {
  console.log('WebSocket server is listening on port 3000.');
});