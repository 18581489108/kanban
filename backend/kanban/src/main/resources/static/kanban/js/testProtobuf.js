"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var example_1 = require("./message/example");
var WebSocketClient = (function () {
    function WebSocketClient(uri) {
        this.webSocket = new WebSocket(uri);
        this.webSocket.binaryType = "arraybuffer";
        this.webSocket.onopen = this.onOpen;
        this.webSocket.onmessage = this.onMessage;
    }
    WebSocketClient.prototype.onOpen = function (evt) {
        console.log("open");
        var loginRequest = example_1.example.LoginRequest.create({ uuid: 9 });
        this.sendMessage(1001, example_1.example.LoginRequest.encode(loginRequest).finish());
    };
    WebSocketClient.prototype.onMessage = function (msg) {
        console.log(msg);
    };
    WebSocketClient.prototype.sendMessage = function (packetId, message) {
        var packet = this.createPacket(packetId, message);
        this.webSocket.send(packet);
        console.log("send msg:" + packetId);
    };
    WebSocketClient.prototype.createPacket = function (packetId, message) {
        var id = Uint8Array.of(packetId);
        var len = Uint8Array.of(message.length);
        var packet = new Uint8Array(id.length + len.length + message.length);
        packet.set(id);
        packet.set(len, id.length);
        packet.set(message, id.length + len.length);
        return packet;
    };
    return WebSocketClient;
}());
var webSocket = new WebSocketClient("ws://localhost:8090/ws");
