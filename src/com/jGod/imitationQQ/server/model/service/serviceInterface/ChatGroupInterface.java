package com.jGod.imitationQQ.server.model.service.serviceInterface;

import com.jGod.imitationQQ.server.controller.TaskPackage;

public interface ChatGroupInterface extends Service{
    void show(int id, TaskPackage taskPackage);
    void sendMessageToClient(int id, TaskPackage taskPackage);

    void getMessageFromClient(int id, TaskPackage taskPackage);

    void modifySelfRemark(int id,TaskPackage taskPackage);

    void exitGroup(int id,TaskPackage taskPackage);

    void disbandGroup(int id,TaskPackage taskPackage);
}
