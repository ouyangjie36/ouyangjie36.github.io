package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.User;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;


public class CreateGroup extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/createGroup.jpg"));
    JLabel jTip = new JLabel("群聊ID：");
    JTextField jTextField = new JTextField();

    JLabel jLname=new JLabel("群名称：");
    JTextField jTname=new JTextField();

    JButton jButton = new JButton();
    ImageIcon imageIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/enter.jpg");

    JLabel jHead=new JLabel("头像");
    JLabel jHeadImage = new JLabel();
    ImageIcon iHead = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/1.gif");
    JButton jBHead = new JButton();
    ImageIcon jLHaed = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/changeHead.jpg");
    JScrollPane jScrollPane = new JScrollPane();

    ArrayList<ImageIcon> list = new ArrayList<>();
    JList jList = new JList();

    /*------------*/
    private MainView mainView;
    int low = 0;
    int high = 9;
    private int index;
    private int head;

    public CreateGroup(MainView mainView){
        this.mainView = mainView;
        this.setSize(676, 381);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("创建群聊");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        this.add(jTip);
        this.add(jTextField);
        this.add(jButton);
        this.add(jLname);
        this.add(jTname);
        this.add(jHead);
        this.add(jHeadImage);
        this.add(jBHead);
        this.add(jScrollPane);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.setCreateGroup(null);
                CreateGroup.this.dispose();
            }
        });
    }

    public void init(){
        jBack.setBounds(0,0,696,435);
        jTip.setBounds(100,50,80,50);
        jTip.setForeground(Color.BLUE);
        jTip.setFont(new Font("宋体",Font.BOLD,16));

        jTextField.setBounds(200,50,200,50);
        jTextField.setFont(new Font("楷体",Font.BOLD,18));

        jLname.setBounds(100,110,80,50);
        jLname.setForeground(Color.BLUE);
        jLname.setFont(new Font("宋体",Font.BOLD,16));

        jTname.setBounds(200,110,200,50);
        jTname.setFont(new Font("楷体",Font.BOLD,18));

        jButton.setBounds(300,240,63,19);
        jButton.setIcon(imageIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建群聊
                int id = 0;
                try {
                    id = Integer.parseInt(jTextField.getText());
                } catch (NumberFormatException ex) {
                    Warn warn = new Warn("群号不是纯数字！");
                    warn.setVisible(true);
                }
                TaskPackage t = new TaskPackage(9,3,new HashMap<>());
                t.getHashMap().put("group_id",String.valueOf(id));
                t.getHashMap().put("head",String.valueOf(head));
                t.getHashMap().put("name",jTname.getText());
                networkPackage.sendPackageToController(t);
                if(mainView!=null) mainView.setCreateGroup(null);
                CreateGroup.this.dispose();
            }
        });

        for (int i = low; i <= high; i++) {
            ImageIcon icon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+i+".gif");
            list.add(icon);
        }

        jHead.setFont(new Font("宋体",Font.PLAIN,15));
        jHead.setBounds(100,170,150,50);
        jHeadImage.setBounds(160,jHead.getY()+9,32,32);
        jHeadImage.setIcon(iHead);
        jBHead.setBounds(jHeadImage.getX()+40,jHeadImage.getY()+7,77,18);
        jBHead.setIcon(jLHaed);
        jBHead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //显示头像组
                JScrollPane js = CreateGroup.this.jScrollPane;
                JButton jb = CreateGroup.this.jBHead;
                jb.setEnabled(false);
                js.setVisible(true);
                js.getViewport().setEnabled(true);
            }
        });
        jList.setModel(new CreateGroupListModel(list));
        jList.setPreferredSize(new Dimension(360,350));
        jList.setCellRenderer(new CreateGroupListCellRenderer());
        jList.setEnabled(false);
        jScrollPane.setVisible(false);
        jScrollPane.setBounds(jBHead.getX(),jBHead.getY(),60,200);
        jScrollPane.setPreferredSize(new Dimension(360,350));
        jScrollPane.setViewportView(jList);

        jList.addMouseMotionListener(new CreateGroupMouseMotionAdapter());
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
    }
    class CreateGroupMouseMotionAdapter extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent e){
            if(!list.isEmpty()){
                index = jList.locationToIndex(e.getPoint());
            }
        }
    }
    class CreateGroupListModel extends AbstractListModel{
        ArrayList<ImageIcon> uArray;
        public CreateGroupListModel(ArrayList<ImageIcon> list){
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
    class CreateGroupListCellRenderer extends JLabel implements ListCellRenderer{

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
