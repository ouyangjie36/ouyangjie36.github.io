package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;

public class AddFriend extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/addFriend.jpg"));
    JLabel jID = new JLabel("ID:");
    JLabel jText = new JLabel("验证信息");
    JTextArea jTextField = new JTextArea();
    JButton jButton = new JButton();
    ImageIcon jIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/addUser.jpg");

    private int friend_id;
    private MainView mainView;
    public AddFriend(int friend_id, MainView mainView){
        this.mainView = mainView;
        this.setSize(474,266);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("添加好友");
        this.friend_id = friend_id;
        jID.setText("ID:"+friend_id);
        this.add(jID);
        this.add(jText);
        this.add(jTextField);
        this.add(jButton);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.setAddFriend(null);
                AddFriend.this.dispose();
            }
        });
        init();
    }

    public void init(){
        jBack.setBounds(0,0,474,266);
        jID.setBounds(120,25,150,30);
        jID.setFont(new Font("宋体",Font.BOLD,16));

        jText.setBounds(50,60,65,30);
        jText.setFont(new Font("宋体",Font.BOLD,14));
        jText.setForeground(Color.YELLOW);

        jTextField.setBounds(120,60,230,100);
        jTextField.setFont(new Font("楷体",Font.BOLD,18));

        jIcon.setImage(jIcon.getImage().getScaledInstance(64,23,0));
        jButton.setBounds(200,180,64,23);
        jButton.setIcon(jIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskPackage taskPackage = new TaskPackage(2,2,new HashMap<>());
                taskPackage.getHashMap().put("friend_id",String.valueOf(friend_id));
                taskPackage.getHashMap().put("text",jTextField.getText());
                networkPackage.sendPackageToController(taskPackage);
                mainView.setAddFriend(null);
                AddFriend.this.dispose();
            }
        });
    }
}
