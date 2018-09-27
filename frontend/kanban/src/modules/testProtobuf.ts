import {example} from '../message/example';

export class WebSocketClient {
    webSocket : WebSocket;

    constructor(uri : string) {
        this.webSocket = new WebSocket(uri);
        this.webSocket.binaryType = "arraybuffer";

        this.webSocket.onopen = (evt: any) => {
            console.log("open");
            let loginRequest = example.LoginRequest.create({uuid: 9});
            this.sendMessage(1001, example.LoginRequest.encode(loginRequest).finish());
        };

        this.webSocket.onmessage = (msg: any) => {
            console.log(msg);
        };
    }

    sendMessage(packetId : number, message : Uint8Array) : void {
        let packet = this.createPacket(packetId, message);
        this.webSocket.send(packet);
        console.log("send msg:" + packetId);
    }

    /**
     * 创建数据包
     */
    private createPacket(packetId : number, message : Uint8Array) : Uint8Array {
        let id = Uint8Array.of(packetId);
        let len = Uint8Array.of(message.length);

        let packet = new Uint8Array(id.length + len.length + message.length);
        packet.set(id);
        packet.set(len, id.length);
        packet.set(message, id.length + len.length);

        return packet;
    }
}




