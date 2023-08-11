package com.jGod.imitationQQ.client.controller;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkPackage implements Runnable {
    private ConcurrentLinkedQueue<TaskPackage> packages = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<TaskPackage> sendPackages = new ConcurrentLinkedQueue<>();
    private boolean state = true;
    private BufferedReader bis;
    private BufferedWriter bos;
    private Socket socket;

    public NetworkPackage(){

    }

    public NetworkPackage(ConcurrentLinkedQueue<TaskPackage> packages, boolean state, Socket socket) {
        this.packages = packages;
        this.state = state;
        this.socket = socket;

    }

    public void init() throws IOException {
        bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bos = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public ConcurrentLinkedQueue<TaskPackage> getPackages() {
        return packages;
    }

    public void setPackages(ConcurrentLinkedQueue<TaskPackage> packages) {
        this.packages = packages;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void close(){
        while(!sendPackages.isEmpty()){
            sendPackage(sendPackages.poll());
        }
        if (!socket.isClosed()){
            try {
                socket.close();
                state = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getNetworkPackage(){
        try {
            String s;
            while (bis.ready()) {
                s = bis.readLine();
                int id = Integer.parseInt(s);
                int len = Integer.parseInt(bis.readLine());
                HashMap<String, String> hashMap = new HashMap();
                for (int i = 0; i < len; i++) {
                    hashMap.put(bis.readLine(), bis.readLine());
                }
                packages.add(new TaskPackage(id,len,hashMap));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPackage(TaskPackage taskPackage){
        if(!state || socket.isClosed()) return;
        if(taskPackage==null) return;
        try {
            bos.write(String.valueOf(taskPackage.getId()));
            bos.newLine();
            bos.write(String.valueOf(taskPackage.getLen()));
            bos.newLine();
            if(taskPackage.getHashMap()==null){
                bos.flush();
                return;
            }
            for (Map.Entry entry :taskPackage.getHashMap().entrySet()) {
                bos.write((String) entry.getKey());
                bos.newLine();
                bos.write((String) entry.getValue());
                bos.newLine();
            }
            bos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPackageToController(TaskPackage taskPackage){
        if(taskPackage==null) return;
        sendPackages.add(taskPackage);
    }

    public TaskPackage getTaskPackage(){
        if(packages.isEmpty()) return null;
//        System.out.println(packages.peek().toString());
        return packages.poll();
    }
    public void send(){
        if(sendPackages.isEmpty()) return;
        while(!sendPackages.isEmpty()){
//            System.out.println(sendPackages.peek().toString());
            sendPackage(sendPackages.poll());
        }
    }

    @Override
    public void run() {
        while(state){
            getNetworkPackage();
            send();
        }
    }
}
