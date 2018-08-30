package cn.kurisu9.controller;

import cn.kurisu9.entity.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * WebSocket的控制器
 * */
@Controller
public class KanbanWsController {

    @MessageMapping("/hello")
    public Greeting greeting(String msg) {
        System.out.println("客户端消息：" + msg);
        return new Greeting("hello");
    }
}
