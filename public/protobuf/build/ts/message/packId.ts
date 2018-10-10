/**
 * 此文件由工具自动生成，不可更改!!!
 *
 * This class created by ProtobufHelper on 2018-10-09 16:39:08.
 * Don't modify!!!
 */
export namespace packId {
    //#region Test1 消息id
    export const LoginRequest: number = 1001;
    export const LoginResponse: number = 1002;
    export const Message: number = 1003;
    //#endregion

    //#region Test2 消息id
    export const TestMessage: number = 2001;
    export const Hello: number = 2002;
    //#endregion


    /**
     * 定义id与message类的映射
     */
    const packetIdMap: { [key: string]: number; } = {};

    {
        //#region Test1 消息id
        packetIdMap['LoginRequest'] = LoginRequest;
        packetIdMap['LoginResponse'] = LoginResponse;
        packetIdMap['Message'] = Message;
        //#endregion

        //#region Test2 消息id
        packetIdMap['TestMessage'] = TestMessage;
        packetIdMap['Hello'] = Hello;
        //#endregion


    }

    /**
     * 根据消息的名称获取对应的消息id
     */
    export function getPacketIdByName(msgName: string) {
        return packetIdMap[msgName];
    }
}