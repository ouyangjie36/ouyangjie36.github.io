package com.jGod.imitationQQ.server.model.service.serviceInterface;

import com.jGod.imitationQQ.server.controller.TaskPackage;

public interface ChatPersonInterface extends Service{
    void show(int id, TaskPackage taskPackage);
    void sendMessageToClient(int id, TaskPackage taskPackage);

    void getMessageFromClient(int id, TaskPackage taskPackage);

}
