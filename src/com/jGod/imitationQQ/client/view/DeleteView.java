package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Delete;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeleteView extends JFrame {
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/applicationView.jpg"));//313*489
    JScrollPane jScrollPane = new JScrollPane();
    JList jList = new JList();
    JLabel jTip = new JLabel("(被)删除好友名单");
    /*-------------------------*/
    private ArrayList<Delete> list = new ArrayList<>();
    private TaskPackage taskPackage;
    private MainView mainView;

    public DeleteView(TaskPackage taskPackage,MainView mainView) {
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
                if(mainView!=null) mainView.setDeleteView(0);
                DeleteView.this.dispose();
            }
        });
    }

    public void loadInfo() {
        if (taskPackage == null) return;
        list.clear();
        int len = Integer.parseInt(taskPackage.getInfo("deleteNum"));
        for (int i = 0; i < len; i++) {
            String key = "user" + i + "@";
            String c1 = key + "id";
            String c2 = key + "name";
            String c3 = key + "friend_id";
            String c4 = key + "friend_name";
            String c5 = key + "date";
            Delete delete = new Delete();
            delete.setId(Integer.parseInt(taskPackage.getInfo(c1)));
            delete.setName(taskPackage.getInfo(c2));
            delete.setFriend_id(Integer.parseInt(taskPackage.getInfo(c3)));
            delete.setFriend_name(taskPackage.getInfo(c4));
            delete.setDate(Long.parseLong(taskPackage.getInfo(c5)));
            list.add(delete);
        }
        DeleteListModel deleteListModel = new DeleteListModel(list);
        jList.setModel(deleteListModel);
        jList.setCellRenderer(new DeleteListCellRenderer());
        jList.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        jList.setPreferredSize(new Dimension(360, 350));
        jScrollPane.setPreferredSize(new Dimension(360, 350));
        taskPackage = null;
    }

    public void init() {
        jBack.setBounds(0, 0, 313, 489);
        jTip.setBounds(80, 10, 160, 25);
        jTip.setFont(new Font("宋体", Font.BOLD, 15));
        jTip.setForeground(Color.GREEN);
        jScrollPane.setViewportView(jList);
        jScrollPane.setBounds(5, 40, 300, 440);
    }

    class DeleteListModel extends AbstractListModel {
        ArrayList<Delete> uArray;

        public DeleteListModel(ArrayList<Delete> list) {
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

    class DeleteListCellRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Delete delete = (Delete) value;
            long time = delete.getDate();
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setText("<html>" + delete.getId() + "(" + delete.getName() + ")删除了好友" + delete.getFriend_id() +
                    "(" + delete.getFriend_name() + ")<br/>" + "删除日期：" +simpleDateFormat.format(date) + "<html/>");
            setFont(new Font("楷体", Font.BOLD, 13));
            return this;
        }
    }
}
