package com.jGod.imitationQQ.server.controller;

public class IOUtils {
    public static int byteToInt(byte[] b){
        if(b.length!=4){
            throw new RuntimeException();
        }
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res+=((b[i]&0xFF)<<((3-i)*8));
        }
        return res;
    }
}
