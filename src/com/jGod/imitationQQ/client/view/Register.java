package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;
import static java.lang.Thread.sleep;

public class Register extends JFrame{
    int id;
    String name;
    int head=0;
    int age;
    String gender;
    long birthday;
    String password;
    String type;
    String y;
    String m;
    String d;
    JLabel jTop = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/register.png"));
    JLabel jLnumber=new JLabel("账号");
    JTextField jTnumber=new JTextField();
    JLabel jLname=new JLabel("昵称");
    JTextField jTname=new JTextField();
    JLabel jLpass=new JLabel("密码");
    JPasswordField jPass=new JPasswordField();
    JLabel jLsex=new JLabel("性别");
    ButtonGroup radioGroup=new ButtonGroup();
    JRadioButton  jRboy=new JRadioButton("男",false);
    JRadioButton  jRgirl=new JRadioButton("女",false);
    JLabel jLbirth=new JLabel("生日");
    JLabel jyear=new JLabel("年");
    JLabel jmonth=new JLabel("月");
    JLabel jday=new JLabel("日");
    DefaultComboBoxModel yearModel = new DefaultComboBoxModel();
    DefaultComboBoxModel monthModel = new DefaultComboBoxModel();
    DefaultComboBoxModel dayModel=new DefaultComboBoxModel();
    JComboBox year = new JComboBox();
    JComboBox month = new JComboBox();
    JComboBox day=new JComboBox();
    String timeT[]= {"公历","农历"};
    JComboBox timeType=new JComboBox(timeT);
    JButton jBregist=new JButton("立即注册");

    Socket socket;
    private BufferedReader in = null;
    private BufferedWriter out = null;
    public Register(){
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("注册");
        this.add(jTnumber);
        this.add(jLnumber);
        this.add(jLname);
        this.add(year);
        this.add(month);
        this.add(day);
        this.add(jTname);
        this.add(jBregist);
        this.add(jLbirth);
        this.add(jLpass);
        this.add(jLsex);
        this.add(jmonth);
        this.add(jRboy);
        this.add(jRgirl);
        this.add(jyear);
        this.add(jday);
        this.add(jPass);
        this.add(jTop);
        init();
    }

    public void init(){
        int baseX = 100;
        int labelX = 60;
        int dateX = 90;
        int disX = 70;
        int baseY = 100;
        int disY = 60;
        int buttonX = 180;
        int buttonY = 350;

        for (int i = 1950; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            yearModel.addElement(i);
        }
        for (int j = 1; j <= 12; j++) {
            monthModel.addElement(j);
        }
        for(int k=1; k<=31; k++) {
            dayModel.addElement(k);
        }
        year.setModel((ComboBoxModel) yearModel);
        month.setModel((ComboBoxModel) monthModel);
        day.setModel((ComboBoxModel)dayModel);

        jTop.setBounds(0,0,700,500);
        jLnumber.setFont(new Font("宋体",Font.PLAIN,15));
        jLnumber.setBounds(baseX,baseY-disY,50,50);
        jTnumber.setFont(new Font("宋体",Font.BOLD,18));
        jTnumber.setBounds(baseX+labelX,baseY-disY,220,45);
        /*-------------*/
        jLname.setBounds(baseX,baseY,50,50);
        jLname.setFont(new Font("宋体",Font.PLAIN,15));
        jTname.setBounds(baseX+labelX,baseY,220,45);
        jTname.setFont(new Font("宋体",Font.BOLD,18));
        /*-------------*/
        jLpass.setBounds(baseX,baseY+disY,50,50);
        jLpass.setFont(new Font("宋体",Font.PLAIN,15));
        jPass.setBounds(baseX+labelX,baseY+disY,220,45);
        jPass.setFont(new Font("s宋体",Font.BOLD,18));
        /*-------------*/
        jLsex.setFont(new Font("宋体",Font.PLAIN,15));
        jLsex.setBounds(baseX,baseY+2*disY, 50, 50);
        jRboy.setFont(new Font("宋体",Font.PLAIN,16));
        jRboy.setForeground(Color.BLUE);
        jRboy.setBounds(jLsex.getX()+labelX, jLsex.getY(), 50, 50);
        jRboy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource()==jRboy) {
                    gender="男";
                }
            }

        });
        jRgirl.setFont(new Font("宋体",Font.PLAIN,16));
        jRgirl.setForeground(Color.BLUE);
        jRgirl.setBounds(jLsex.getX()+2*labelX, jLsex.getY(), 50, 50);
        jRgirl.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub\
                if(e.getSource()==jRgirl) {
                    gender="女";
                }

            }
        });
        radioGroup.add(jRboy);
        radioGroup.add(jRgirl);
        /*----------------*/
        jLbirth.setFont(new Font("宋体",Font.PLAIN,15));
        jLbirth.setBounds(baseX, baseY+3*disY, 50, 50);
        timeType.setBackground(Color.WHITE);
        timeType.setFont(new Font("宋体",Font.PLAIN,16));
        timeType.setForeground(Color.BLUE);
        timeType.setBounds(jLbirth.getX()+labelX, jLbirth.getY(), 65, 35);
        type=timeType.getSelectedItem().toString();
        timeType.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                type=timeType.getSelectedItem().toString();
            }
        });
        year.setFont(new Font("宋体",Font.PLAIN,16));
        year.setForeground(Color.BLUE);
        year.setBounds(jLbirth.getX()+labelX, jLbirth.getY(), 65, 35);
        year.setBackground(Color.WHITE);
        y=year.getSelectedItem().toString();
        year.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                y=year.getSelectedItem().toString();
            }
        });
        jyear.setFont(new Font("宋体",Font.PLAIN,16));
        jyear.setBounds(jLbirth.getX()+labelX+disX, jLbirth.getY(), 25, 35);
        month.setFont(new Font("宋体",Font.PLAIN,16));
        month.setForeground(Color.BLUE);
        month.setBounds(jLbirth.getX()+labelX+dateX, jLbirth.getY(), 55, 35);
        month.setBackground(Color.WHITE);
        m=month.getSelectedItem().toString();
        month.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                m=month.getSelectedItem().toString();
            }
        });
        jmonth.setFont(new Font("宋体",Font.PLAIN,16));
        jmonth.setBounds(jLbirth.getX()+2*labelX+dateX, jLbirth.getY(), 25, 35);
        day.setFont(new Font("宋体",Font.PLAIN,16));
        day.setForeground(Color.BLUE);
        day.setBounds(jLbirth.getX()+2*disX+dateX, jLbirth.getY(), 55, 35);
        day.setBackground(Color.WHITE);
        d=day.getSelectedItem().toString();
        day.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                d=day.getSelectedItem().toString();
            }
        });
        jday.setFont(new Font("宋体",Font.PLAIN,16));
        jday.setBounds(jLbirth.getX()+labelX+dateX+2*disX, jLbirth.getY(), 25, 35);
        /*-----------------------*/
        jBregist.setFont(new Font("宋体",Font.BOLD,20));
        jBregist.setBackground(Color.green);
        jBregist.setForeground(Color.white);
        jBregist.setBounds(buttonX, buttonY, 150, 50);
        jBregist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskPackage taskPackage = new TaskPackage(18,7,new HashMap<String,String>());
                int id = 0;
                try {
                    id = Integer.parseInt(jTnumber.getText());
                } catch (NumberFormatException ex) {
                    Warn warn = new Warn("账号不是纯数字！");
                    warn.setVisible(true);
                }
                taskPackage.getHashMap().put("id",jTnumber.getText());
                taskPackage.getHashMap().put("name",jTname.getText());
                taskPackage.getHashMap().put("head",String.valueOf(1));
                age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(y);
                taskPackage.getHashMap().put("age",String.valueOf(age));
                taskPackage.getHashMap().put("gender",gender);
                taskPackage.getHashMap().put("birthday", y+"-"+m+"-"+d);
                taskPackage.getHashMap().put("password",new String(jPass.getPassword()));
                networkPackage.sendPackageToController(taskPackage);
                dispose();
            }
        });
    }
}
