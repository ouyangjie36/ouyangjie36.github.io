package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.User;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;

@SuppressWarnings("all")
public class ModifySelf extends JFrame {
    int low = 0;
    int high = 9;
    int id;
    String name;
    int head;
    int age;
    String gender;
    long birthday;
    String password;
    String type;
    String y;
    String m;
    String d;
    JLabel jTop = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/changeSelfInfo.jpg"));
    JLabel jHead=new JLabel("头像");
    JLabel jHeadImage = new JLabel();
    ImageIcon iHead = new ImageIcon();
    JButton jBHead = new JButton();
    ImageIcon jLHaed = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/changeHead.jpg");
    JScrollPane jScrollPane = new JScrollPane();

    ArrayList<ImageIcon> list = new ArrayList<>();
    JList jList = new JList();

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
    JButton jBregist=new JButton();
    ImageIcon imageIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/save.jpg");

    private String[] strings;
    private String sex;
    private User own;
    private int index;
    private MainView mainView;
    public ModifySelf(User own,MainView mainView){
        this.own = own;
        this.mainView = mainView;
        this.setSize(772,507);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("修改个人信息");
        this.add(jHead);
        this.add(jHeadImage);
        this.add(jBHead);
        this.add(jScrollPane);
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mainView!=null) mainView.setModifySelf(null);
                ModifySelf.this.dispose();
            }
        });
        loadInfo();
        init();
    }

    public void loadInfo(){
        if(own==null) return;
        head = own.getHead();
        iHead = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+own.getHead()+".gif");
        jHeadImage.setIcon(iHead);
        jTname.setText(own.getName());
        age = own.getAge();
        if(own.getGender().equals("男")){
            jRboy.setSelected(true);
            sex = "男";
        }else{
            sex = "女";
            jRgirl.setSelected(true);
        }
        if(own.getBirthday()!=null){
            String[] s = own.getBirthday().split("-");
            strings = s;
            if(s.length==3){
                yearModel.setSelectedItem(Integer.parseInt(s[0]));
                monthModel.setSelectedItem(Integer.parseInt(s[1]));
                dayModel.setSelectedItem(Integer.parseInt(s[2]));
            }
        }
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

        jTop.setBounds(5,0,747,467);
        jHead.setFont(new Font("宋体",Font.PLAIN,15));
        jHead.setBounds(baseX,baseY-disY,50,50);
        jHeadImage.setBounds(baseX+disX,jHead.getY()+9,32,32);
        jHeadImage.setIcon(iHead);
        jBHead.setBounds(jHeadImage.getX()+40,jHeadImage.getY()+7,77,18);
        jBHead.setIcon(jLHaed);
        jBHead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //显示头像组
                JScrollPane js = ModifySelf.this.getjScrollPane();
                JButton jb = ModifySelf.this.getjBHead();
                jb.setEnabled(false);
                js.setVisible(true);
                js.getViewport().setEnabled(true);
            }
        });

        for (int i = low; i <= high; i++) {
            ImageIcon icon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+i+".gif");
            list.add(icon);
        }

        jList.setModel(new HeadListModel(list));
        jList.setPreferredSize(new Dimension(360,350));
        jList.setCellRenderer(new HeadListCellRenderer());
        jList.setEnabled(false);
        jScrollPane.setVisible(false);
        jScrollPane.setBounds(jBHead.getX(),jBHead.getY(),60,200);
        jScrollPane.setPreferredSize(new Dimension(360,350));
        jScrollPane.setViewportView(jList);

        jList.addMouseMotionListener(new ChangeHeadMouseMotionAdapter());
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                head = index + low;
                jHeadImage.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+head+".gif"));
                jList.setEnabled(false);
                jScrollPane.setVisible(false);
                jBHead.setEnabled(true);
            }
        });

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
        jBregist.setBounds(buttonX,buttonY,63,19);
        jBregist.setIcon(imageIcon);
        jBregist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改信息
                int num = 0;
                TaskPackage t = new TaskPackage(1,0,new HashMap<>());
                if(own.getHead()!=head){
                    num++;
                    own.setHead(head);
                    t.getHashMap().put("head",String.valueOf(head));
                }
                if(jTname.getText()!=own.getName()){
                    num++;
                    own.setName(jTname.getText());
                    t.getHashMap().put("name",jTname.getText());
                }
                System.out.println(new String(jPass.getPassword()).trim().length());
                if(jPass.getPassword()!=null && new String(jPass.getPassword()).trim().length()!=0){
                    num++;
                    t.getHashMap().put("password",new String(jPass.getPassword()));
                }
                if(gender!=null && !gender.equals(sex)){
                    num++;
                    t.getHashMap().put("gender",gender);
                }
                if(strings==null || (strings[0].equals(y) || strings[1].equals(m) || strings[2].equals(d))){
                    num++;
                    t.getHashMap().put("birthday",y+"-"+m+"-"+d);
                }
                int a = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(y);
                if(a!=own.getAge()){
                    num++;
                    t.getHashMap().put("age",String.valueOf(a));
                }
                t.setLen(num);
                networkPackage.sendPackageToController(t);
                mainView.setModifySelf(null);
                ModifySelf.this.dispose();
            }
        });
    }

    class ChangeHeadMouseMotionAdapter extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent e){
            if(!list.isEmpty()){
                index = jList.locationToIndex(e.getPoint());
            }
        }
    }

    public JButton getjBHead() {
        return jBHead;
    }

    public void setjBHead(JButton jBHead) {
        this.jBHead = jBHead;
    }

    public User getOwn() {
        return own;
    }

    public void setOwn(User own) {
        this.own = own;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public void setjScrollPane(JScrollPane jScrollPane) {
        this.jScrollPane = jScrollPane;
    }

    public JLabel getjHeadImage() {
        return jHeadImage;
    }

    public void setjHeadImage(JLabel jHeadImage) {
        this.jHeadImage = jHeadImage;
    }

    class HeadListModel extends AbstractListModel{
        ArrayList<ImageIcon> uArray;
        public HeadListModel(ArrayList<ImageIcon> list){
            this.uArray = list;
        }
        @Override
        public int getSize() {
            return uArray.size();
        }

        @Override
        public Object getElementAt(int index) {
            return uArray.get(index);
        }
    }
    class HeadListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            ImageIcon icon = (ImageIcon) value;
            Image img = icon.getImage().getScaledInstance(32,32, Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(10);
            return this;
        }
    }
}
