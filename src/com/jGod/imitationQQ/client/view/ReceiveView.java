package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Receive;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ReceiveView extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/applicationView.jpg"));//313*489
    JScrollPane jScrollPane = new JScrollPane();
    JList jList = new JList();
    JLabel jTip = new JLabel("(被)接受好友申请名单");
    /*-------------------------*/
    private ArrayList<Receive> list = new ArrayList<>();
    private TaskPackage taskPackage;
    private MainView mainView;

    public ReceiveView(TaskPackage taskPackage,MainView mainView) {
        this.mainView = mainView;
        this.taskPackage = taskPackage;
        this.setSize(325, 525);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadInfo();
        init();
        this.add(jScrollPane);
        this.add(jTip);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.setReceiveView(0);
                ReceiveView.this.dispose();
            }
        });
    }

    public void loadInfo() {
        if (taskPackage == null) return;
        list.clear();
        int len = Integer.parseInt(taskPackage.getInfo("receiveNum"));
        for (int i = 0; i < len; i++) {
            String key = "user" + i + "@";
            String c1 = key + "id";
            String c2 = key + "name";
            String c3 = key + "applicant_id";
            String c4 = key + "applicant_name";
            String c5 = key + "text";
            String c6 = key + "date";
            Receive receive = new Receive();
            receive.setId(Integer.parseInt(taskPackage.getInfo(c1)));
            receive.setName(taskPackage.getInfo(c2));
            receive.setApplicant_id(Integer.parseInt(taskPackage.getInfo(c3)));
            receive.setApplicant_name(taskPackage.getInfo(c4));
            receive.setText(taskPackage.getInfo(c5));
            receive.setDate(Long.parseLong(taskPackage.getInfo(c6)));
            list.add(receive);
        }
        ReceiveListModel receiveListModel = new ReceiveListModel(list);
        jList.setModel(receiveListModel);
        jList.setCellRenderer(new ReceiveListCellRenderer());
        jList.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        jList.setPreferredSize(new Dimension(360, 350));
        jScrollPane.setPreferredSize(new Dimension(360, 350));
        taskPackage = null;
    }

    public void init() {
        jBack.setBounds(0, 0, 313, 489);
        jTip.setBounds(70, 10, 160, 25);
        jTip.setFont(new Font("宋体", Font.BOLD, 15));
        jTip.setForeground(Color.GREEN);
        jScrollPane.setViewportView(jList);
        jScrollPane.setBounds(5, 40, 300, 440);
    }

    class ReceiveListModel extends AbstractListModel {
        ArrayList<Receive> uArray;

        public ReceiveListModel(ArrayList<Receive> list) {
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

    class ReceiveListCellRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Receive receive = (Receive) value;
            setText("<html>" + receive.getId() + "(" + receive.getName() + ")接受了" + receive.getApplicant_id() + "(" + receive.getApplicant_name() + ")的好友请求" + "<br/>" +
                    "验证信息：" + receive.getText() + "<html/>");
            setFont(new Font("楷体", Font.BOLD, 13));
            return this;
        }
    }
}
