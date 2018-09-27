import {example} from '../message/example';

export class WebSocketClient {
    webSocket : WebSocket;

    constructor(uri : string) {
        this.webSocket = new WebSocket(uri);
        this.webSocket.binaryType = "arraybuffer";

        this.webSocket.onopen = (evt: any) => {
            console.log("open");
            let loginRequest = example.LoginRequest.create({uuid: -9});
            this.sendMessage(1001, example.LoginRequest.encode(loginRequest).finish());
        };

        this.webSocket.onmessage = (msg: any) => {
            console.log(msg);
        };
    }

    sendMessage(packetId : number, message : Uint8Array) : void {
        let packet = this.createPacket(packetId, message);
        console.log("packet:");
        console.log(packet);

        this.webSocket.send(packet);
        console.log("send msg:" + packetId);
    }

    /**
     * 创建数据包
     */
    private createPacket(packetId : number, message : Uint8Array) : Uint8Array {
        let headBuf: Uint8Array = this.createHead(packetId, message.length);
        let packet = new Uint8Array(headBuf.length + message.length);
        packet.set(headBuf);
        packet.set(message, headBuf.length);
        /*
        let id = Uint16Array.of(packetId);
        let len = Uint32Array.of(message.length);

        let packet = new Uint8Array(id.length + len.length + message.length);
        packet.set(id);
        packet.set(len, id.length);
        packet.set(message, id.length + len.length);
        */

        return packet;
    }

    private createHead(packetId: number, msgLen: number): Uint8Array {
        let headBuf = new ArrayBuffer(6);
        let dataView = new DataView(headBuf);
        dataView.setInt16(0, packetId);
        dataView.setInt32(2, msgLen);

        return new Uint8Array(headBuf);
    }
}




