package com.jGod.imitationQQ.server.model.service;

import java.net.Socket;

import static com.jGod.imitationQQ.server.controller.MainController.networkPackage;

public class Warn {
    public void sendWarn(Socket socket, int task_id, String text, int response_id, int success){
        networkPackage.sendPackage(socket,null,task_id,null,"content",text,"response",String.valueOf(response_id),"success",String.valueOf(success));
    }
}
