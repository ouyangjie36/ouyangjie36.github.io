package com.jGod.imitationQQ.server.model.service;

import com.jGod.imitationQQ.server.model.service.serviceImp.ChatGroupImp;
import com.jGod.imitationQQ.server.model.service.serviceImp.ChatPersonImp;
import com.jGod.imitationQQ.server.model.service.serviceImp.ManagerMenuImp;
import com.jGod.imitationQQ.server.model.service.serviceImp.UserMenuImp;
import com.jGod.imitationQQ.server.model.service.serviceInterface.Service;

public class ServiceMenu {
    private static ChatGroupImp chatGroup;
    private static ChatPersonImp chatPerson;
    private static ManagerMenuImp managerMenu;
    private static UserMenuImp userMenu;
    private static Warn warn;

    static {
        chatGroup = new ChatGroupImp();
        chatPerson = new ChatPersonImp();
        managerMenu = new ManagerMenuImp();
        userMenu = new UserMenuImp();
        warn = new Warn();
    }

    public static Warn getWarn() {
        return warn;
    }

    public static ChatGroupImp getChatGroup() {
        return chatGroup;
    }

    public static ChatPersonImp getChatPerson() {
        return chatPerson;
    }

    public static ManagerMenuImp getManagerMenu() {
        return managerMenu;
    }

    public static UserMenuImp getUserMenu() {
        return userMenu;
    }
}
