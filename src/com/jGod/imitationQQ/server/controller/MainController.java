package com.jGod.imitationQQ.server.controller;

import com.jGod.imitationQQ.server.controller.UserController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

public class MainController {
    public static NetworkPackage networkPackage;
    public static ConcurrentHashMap<Integer,UserController> online = new ConcurrentHashMap<>();

    private int state = 0; //1表示服务启动

    public static void main(String[] args) throws  IOException {
        MainController mainController = new MainController();
        mainController.state=1;
        while(true){
            ServerSocket serverSocket = new ServerSocket(8001);
            networkPackage = new NetworkPackage();
            networkPackage.setMainController(mainController);
            new Thread(networkPackage).start();
            while(mainController.state!=0){
                System.out.println("服务器启动已启动！");
                try {
                    Socket socket = serverSocket.accept();
                    UserController userController = new UserController(socket);
                    new Thread(userController).start();
                    networkPackage.addSocket(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void open(){
        state = 1;
    }

    public void close(){
        state = 0;
    }
}
