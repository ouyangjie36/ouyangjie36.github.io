package com.jGod.imitationQQ.server.controller;

import com.jGod.imitationQQ.server.controller.MainController;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkPackage implements Runnable {
    private HashMap<Socket, LinkedList<TaskPackage>> packages = new HashMap();
    private HashMap<Socket,BufferedReader> readers = new HashMap<>();
    private HashMap<Socket,BufferedWriter> writers = new HashMap<>();
    private boolean state;
    private MainController mainController;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public NetworkPackage() {
        state = true;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean hasPackage(Socket socket) {
        if (packages.containsKey(socket) && !packages.get(socket).isEmpty()) {
            return true;
        }
        return false;
    }

    public TaskPackage getPackage(Socket socket) {
        if (hasPackage(socket)) {
            return packages.get(socket).poll();
        }
        return null;
    }

    public HashMap<Socket, LinkedList<TaskPackage>> getPackages() {
        return packages;
    }

    public void setPackages(HashMap<Socket, LinkedList<TaskPackage>> packages) {
        this.packages = packages;
    }

    public void addSocket(Socket socket) throws IOException {
        if (packages.containsKey(socket)) return;
        packages.put(socket, new LinkedList<>());
        writers.put(socket,new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        readers.put(socket,new BufferedReader(new InputStreamReader(socket.getInputStream())));
    }

    public void removeSocket(Socket socket) {
        if (packages.containsKey(socket)) {
            packages.remove(socket);
            writers.remove(socket);
            readers.remove(socket);
        }
    }

    public void swapReceivePackage(Socket socket) {
        if(!readers.containsKey(socket)) return;
        BufferedReader bis = readers.get(socket);
        try {
            String s;
            while (bis.ready()) {
                s = bis.readLine();
                int len = Integer.parseInt(bis.readLine());
                HashMap<String, String> hashMap = new HashMap();
                for (int i = 0; i < len; i++) {
                    hashMap.put(bis.readLine(), bis.readLine());
                }
                packages.get(socket).add(new TaskPackage(Integer.parseInt(s), len, hashMap));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPackage(Socket socket,Object object, int task_id,String[] parameters,String... extra){
        if(!writers.containsKey(socket)) return;
        int len = 0;
        if(parameters!=null) len+=parameters.length;
        if(extra!=null) len+=extra.length/2;
        BufferedWriter bufferedWriter = writers.get(socket);
        try {
             bufferedWriter.write(String.valueOf(task_id));
             bufferedWriter.newLine();
             bufferedWriter.write(String.valueOf(len));
             bufferedWriter.newLine();
             if(parameters!=null){
                 for (String s : parameters) {
                     bufferedWriter.write(s);
                     bufferedWriter.newLine();
                     Field f = object.getClass().getDeclaredField(s);
                     f.setAccessible(true);
                     if(f.get(object) instanceof String){
                         bufferedWriter.write((String)f.get(object));
                     }else{
                         bufferedWriter.write(String.valueOf(f.get(object)));
                     }
                     bufferedWriter.newLine();
                 }
             }
             if(extra!=null){
                 for(String s : extra) {
                     bufferedWriter.write(s);
                     bufferedWriter.newLine();
                 }
             }
            bufferedWriter.flush();
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void restart() {
        state = true;
    }

    public void close() {
        packages.clear();
        readers.clear();
        writers.clear();
        state = false;
    }

    @Override
    public void run() {
        while (state) {
            for (Iterator<Socket> it = packages.keySet().iterator();it.hasNext();) {
                Socket socket = it.next();
                if (socket.isClosed()) {
                    packages.remove(socket);
                    continue;
                }
                swapReceivePackage(socket);
            }
        }
    }
}
