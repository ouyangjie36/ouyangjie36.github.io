package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Application;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;

@SuppressWarnings("all")
public class ApplicationView extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/applicationView.jpg"));//313*489
    JScrollPane jScrollPane = new JScrollPane();
    JList jList = new JList();
    JLabel jTip = new JLabel("申请名单");
    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem j1 = new JMenuItem("接受");
    JMenuItem j2 = new JMenuItem("拒绝");
    /*-------------------------*/
    private ArrayList<Application> list = new ArrayList<>();
    private TaskPackage taskPackage;
    private MainView mainView;
    private int index;

    public ApplicationView(TaskPackage taskPackage,MainView mainView){
        this.mainView = mainView;
        this.taskPackage = taskPackage;
        this.setSize(325,525);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadInfo();
        init();
        this.add(jTip);
        this.add(jScrollPane);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.setApplicationView(0);
                ApplicationView.this.dispose();
            }
        });
    }

    public void init(){
        jBack.setBounds(0,0,313,489);
        jTip.setBounds(120,10,80,25);
        jTip.setFont(new Font("宋体",Font.BOLD,15));
        jTip.setForeground(Color.GREEN);
        jPopupMenu.add(j1);
        jPopupMenu.add(j2);
        j1.setFont(new Font("楷体",Font.PLAIN,14));
        j1.setForeground(Color.BLUE);
        j2.setFont(new Font("楷体",Font.PLAIN,14));
        j2.setForeground(Color.BLUE);
        j1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //接受
                Application a = list.get(index);
                TaskPackage t = new TaskPackage(12,2,new HashMap<>());
                t.getHashMap().put("friend_id",String.valueOf(a.getId()));
                t.getHashMap().put("text",a.getText());
                networkPackage.sendPackageToController(t);
                list.remove(a);
            }
        });
        j2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //拒绝
                Application a = list.get(index);
                TaskPackage t = new TaskPackage(13,2,new HashMap<>());
                t.getHashMap().put("friend_id",String.valueOf(a.getId()));
                t.getHashMap().put("text",a.getText());
                list.remove(a);
                ApplicationListModel applicationListModel = new ApplicationListModel(list);
                jList.setModel(applicationListModel);
                jScrollPane.setViewportView(jList);
                jScrollPane.updateUI();
                networkPackage.sendPackageToController(t);
            }
        });

        jList.setComponentPopupMenu(jPopupMenu);
        jList.addMouseMotionListener(new ApplicationMouseMotionAdapter());
        jScrollPane.setBounds(5,40,300,440);
    }

    public void loadInfo(){
        if(taskPackage==null) return;
        list.clear();
        int len = Integer.parseInt(taskPackage.getInfo("applicantNum"));
        for (int i = 0; i < len; i++) {
            String key = "user" + i + "@";
            String c1 = key + "id";
            String c2 = key + "text";
            String c3 = key + "name";
            String c4 = key + "head";
            Application application = new Application();
            application.setId(Integer.parseInt(taskPackage.getInfo(c1)));
            application.setText(taskPackage.getInfo(c2));
            application.setName(taskPackage.getInfo(c3));
            application.setHead(Integer.parseInt(taskPackage.getInfo(c4)));
            list.add(application);
        }
        ApplicationListModel applicationListModel = new ApplicationListModel(list);
        jList.setModel(applicationListModel);
        jList.setCellRenderer(new ApplicationListCellRenderer());
        jList.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        jList.setPreferredSize(new Dimension(360,350));
        jScrollPane.setViewportView(jList);
        jScrollPane.setPreferredSize(new Dimension(360,350));

        taskPackage = null;
    }
    class ApplicationListModel extends AbstractListModel{
        ArrayList<Application> uArray;
        public ApplicationListModel(ArrayList<Application> list){
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

    class ApplicationListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Application application = (Application) value;
            setText("<html>"+application.getId()+"("+application.getName()+")"+"<br/>"+"验证信息："+application.getText()+"<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+application.getHead()+".gif").getImage().getScaledInstance(32,32,
                    Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(10);
            return this;
        }
    }
    class ApplicationMouseMotionAdapter extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent e){
            if(!list.isEmpty()){
                index = jList.locationToIndex(e.getPoint());
            }
        }
    }
}
