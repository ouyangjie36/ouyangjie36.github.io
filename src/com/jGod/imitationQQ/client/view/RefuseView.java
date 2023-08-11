package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Receive;
import com.jGod.imitationQQ.client.Bean.Refuse;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Ref;
import java.util.ArrayList;

public class RefuseView extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/applicationView.jpg"));//313*489
    JScrollPane jScrollPane = new JScrollPane();
    JList jList = new JList();
    JLabel jTip = new JLabel("(被)拒绝好友申请名单");
    /*-------------------------*/
    private ArrayList<Refuse> list = new ArrayList<>();
    private TaskPackage taskPackage;
    private MainView mainView;

    public RefuseView(TaskPackage taskPackage,MainView mainView) {
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
                if (mainView!=null) mainView.setRefuseView(0);
                RefuseView.this.dispose();
            }
        });
    }

    public void loadInfo() {
        if (taskPackage == null) return;
        list.clear();
        int len = Integer.parseInt(taskPackage.getInfo("refuseNum"));
        for (int i = 0; i < len; i++) {
            String key = "user" + i + "@";
            String c1 = key + "id";
            String c2 = key + "name";
            String c3 = key + "applicant_id";
            String c4 = key + "applicant_name";
            String c5 = key + "text";
            String c6 = key + "date";
            Refuse refuse = new Refuse();
            refuse.setId(Integer.parseInt(taskPackage.getInfo(c1)));
            refuse.setName(taskPackage.getInfo(c2));
            refuse.setApplicant_id(Integer.parseInt(taskPackage.getInfo(c3)));
            refuse.setApplicant_name(taskPackage.getInfo(c4));
            refuse.setText(taskPackage.getInfo(c5));
            refuse.setDate(Long.parseLong(taskPackage.getInfo(c6)));
            list.add(refuse);
        }
        RefuseListModel refuseListModel = new RefuseListModel(list);
        jList.setModel(refuseListModel);
        jList.setCellRenderer(new RefuseListCellRenderer());
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

    class RefuseListModel extends AbstractListModel {
        ArrayList<Refuse> uArray;

        public RefuseListModel(ArrayList<Refuse> list) {
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

    class RefuseListCellRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Refuse refuse = (Refuse) value;
            setText("<html>" + refuse.getId() + "(" + refuse.getName() + ")拒绝了" + refuse.getApplicant_id() + "(" + refuse.getApplicant_name() + ")" +
                    "的好友请求" + "<br/>" + "验证信息：" + refuse.getText() + "<html/>");
            setFont(new Font("楷体", Font.BOLD, 13));
            return this;
        }
    }
}
