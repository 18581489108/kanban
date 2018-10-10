/**
 * 此文件由工具自动生成，不可更改!!!
 *
 * This class created by ProtobufHelper on ${.now?string["yyyy-MM-dd HH:mm:ss"]}.
 * Don't modify!!!
 */
export namespace ${namespace} {
    <#list allMessages as kv>
    //#region ${kv.key} 消息id
    <#list kv.value as msg>
    <#if msg.comment?exists && msg.comment != "">
     /**
      * ${msg.comment}
      */
    </#if>
    export const ${msg.name}: number = ${msg.id?c};
    </#list>
    //#endregion

    </#list>

    /**
     * 定义id与message类的映射
     */
    const packetIdMap: { [key: string]: number; } = {};

    {
        <#list allMessages as kv>
        //#region ${kv.key} 消息id
        <#list kv.value as msg>
        packetIdMap['${msg.name}'] = ${msg.name};
        </#list>
        //#endregion

        </#list>

    }

    /**
     * 根据消息的名称获取对应的消息id
     */
    export function getPacketIdByName(msgName: string) {
        return packetIdMap[msgName];
    }
}