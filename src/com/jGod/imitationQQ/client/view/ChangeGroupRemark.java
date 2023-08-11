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

public class ChangeGroupRemark extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/changeFriendRemark.jpg"));
    JLabel jTip = new JLabel("请在文本框内输入群聊备注：");
    JTextField jTextField = new JTextField();
    JButton jButton = new JButton();
    ImageIcon imageIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/save.jpg");

    /*------------*/
    private int group_id;
    private MainView mainView;

    public ChangeGroupRemark(int group_id,MainView mainView){
        this.group_id = group_id;
        this.mainView = mainView;
        this.setSize(676, 381);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("修改群聊备注");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        this.add(jTip);
        this.add(jTextField);
        this.add(jButton);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.setChangeGroupRemark(null);
                ChangeGroupRemark.this.dispose();
            }
        });
    }

    public void init(){
        jBack.setBounds(0,0,676,381);
        jTip.setBounds(200,100,267,50);
        jTip.setForeground(Color.BLUE);
        jTip.setFont(new Font("宋体",Font.BOLD,16));

        jTextField.setBounds(200,150,267,50);
        jTextField.setFont(new Font("楷体",Font.BOLD,18));

        jButton.setBounds(300,210,63,19);
        jButton.setIcon(imageIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改群聊备注
                TaskPackage t = new TaskPackage(8,2,new HashMap<>());
                t.getHashMap().put("group_id",String.valueOf(group_id));
                t.getHashMap().put("remark",jTextField.getText());
                networkPackage.sendPackageToController(t);
                mainView.setChangeGroupRemark(null);
                ChangeGroupRemark.this.dispose();
            }
        });
    }
}
