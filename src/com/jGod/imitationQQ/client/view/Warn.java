package com.jGod.imitationQQ.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warn extends JFrame {
    public Warn(String worn) {
        this.setSize(300, 170);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("通知");
        JLabel wornInformation = new JLabel(worn);
        JButton Check = new JButton("确认");
        wornInformation.setBounds(50, 25, 200, 50);
        wornInformation.setFont(new Font("宋体", Font.PLAIN, 15));

        Check.setBounds(100, 75, 75, 30);
        Check.setFont(new Font("宋体", Font.PLAIN, 15));
        this.add(wornInformation);
        this.add(Check);
        Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Warn.this.dispose();
            }
        });
    }

    public static void main(String[] args) {
        Warn warn = new Warn("账号不是纯数字！");
        warn.setVisible(true);
    }
}
