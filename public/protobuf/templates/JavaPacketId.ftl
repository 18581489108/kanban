package ${packageName};

import java.util.Map;
import java.util.HashMap;

<#list importClasses as importInfo>
import ${importInfo};
</#list>

/**
 * 此类由工具自动生成，不可更改!!!
 *
 * This class created by ProtobufHelper on ${.now?string["yyyy-MM-dd HH:mm:ss"]}.
 * Don't modify!!!
 */
public class ${className} {
    <#list allMessages as kv>
    //region ${kv.key} 消息id
    <#list kv.value as msg>
    <#if msg.comment?exists && msg.comment != "">
    /**
     * ${msg.comment}
     */
    </#if>
    public static final short ${msg.name} = ${msg.id?c};
    </#list>
    //endregion

    </#list>

    /**
    * 消息包类和id的映射关系
    */
    private static final Map${r'<'}Class${r'<'}?${r'>'}, Short${r'>'} packetIdMap = new HashMap${r'<'}${r'>'}();

    static {
        <#list allMessages as kv>
        //region ${kv.key} 消息id
        <#list kv.value as msg>
        packetIdMap.put(${kv.key}.${msg.name}.class, ${className}.${msg.name});
        </#list>
        //endregion

        </#list>
    }

    /**
    * 通过message类型获取message的id
    */
    public static short getPacketId(Class${r'<'}?${r'>'} clazz) {
        return packetIdMap.get(clazz);
    }
}