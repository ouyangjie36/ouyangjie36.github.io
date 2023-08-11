package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.controller.MainController;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;

public class Login extends JFrame {
    JLabel jlTitle = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/loginTitle.png"));
    JLabel jlHead = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/loginHead.png"));
    JLabel jNumber = new JLabel("账户");
    JLabel jPass = new JLabel("密码");
    JTextField jtUserId = new JTextField(); //账号
    JPasswordField jPassword = new JPasswordField(); //密码
    JButton jEnter = new JButton("登录");
    /*----------------------*/
    boolean pass = true;
    Register register;
    JlRegist jLRegist = new JlRegist("注册账号");

    public Login(MainController mainController){
        this.setSize(650,432);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("山寨版QQ");
        init();
        this.add(jNumber);
        this.add(jPass);
        this.add(jtUserId);
        this.add(jPassword);
        this.add(jEnter);
        this.add(jlTitle);
        this.add(jlHead);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                mainController.close();
            }
        });
    }

    public void init(){

        int baseX = 100;
        int baseY = 100;
        int labelX = 60;
        int diffY = 60;

        jlTitle.setBounds(0,0,650,432);
        jNumber.setBounds(baseX,baseY,50,45);
        jNumber.setFont(new Font("宋体",Font.PLAIN,15));
        jtUserId.setBounds(baseX+labelX,baseY,250,45);
        jtUserId.setFont(new Font("宋体",Font.BOLD,18));

        jPass.setBounds(baseX,baseY+diffY,50,45);
        jPass.setFont(new Font("宋体",Font.PLAIN,15));
        jPassword.setBounds(baseX+labelX,baseY+diffY,250,45);
        jPassword.setFont(new Font("宋体",Font.BOLD,18));

        jlHead.setBounds(290,150,70,25);

        jEnter.setFont(new Font("宋体",Font.PLAIN,10));
        jEnter.setBounds(220,300,70,30);

        jLRegist.setFont(new Font("宋体",Font.PLAIN,13));
        jLRegist.setForeground(Color.BLUE);
        jLRegist.setBounds(baseX+50, baseY+2*diffY, 70, 30);
        this.add(jLRegist);
        jEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //登录
                TaskPackage taskPackage = new TaskPackage(17,2,new HashMap<String,String>());
                int id = 0;
                try {
                    id = Integer.parseInt(jtUserId.getText());
                } catch (NumberFormatException ex) {
                    Warn warn = new Warn("账号不是纯数字！");
                    warn.setVisible(true);
                }
                taskPackage.getHashMap().put("id",jtUserId.getText());
                taskPackage.getHashMap().put("password",new String(jPassword.getPassword()));
                networkPackage.sendPackageToController(taskPackage);
                jEnter.setEnabled(false);
                jLRegist.setEnabled(false);
            }
        });
    }
    /*-----------------------*/
    class JlRegist extends JLabel {
        private boolean isSupported;
        private String regist;

        public JlRegist(String regist) {
            this.regist=regist;
            try {
                this.isSupported = Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
            } catch (Exception e) {
                this.isSupported = false;
            }
            setText(false);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setText(isSupported);
                    if (isSupported)
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    setText(false);
                }
                public void mouseClicked(MouseEvent e) {

                    if(register==null) {
                        register=new Register();
                        register.setVisible(true);
                    }
                }
            });
        }
        private void setText(boolean b) {
            if (!b)
                setText( regist);
            else
                setText("<html><font color=blue><u>" + regist);
        }
    }

    public void setRegist(boolean b){
        this.jLRegist.setEnabled(b);
    }
    public JButton getjEnter() {
        return jEnter;
    }

    public void setjEnter(JButton jEnter) {
        this.jEnter = jEnter;
    }

    public void close(){
        dispose();
    }

    public JlRegist getjLRegist() {
        return jLRegist;
    }

    public void setjLRegist(JlRegist jLRegist) {
        this.jLRegist = jLRegist;
    }
}
