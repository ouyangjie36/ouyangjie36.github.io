package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.PersonMessage;
import com.jGod.imitationQQ.client.Bean.User;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;

public class ChatView extends JFrame {
    JLabel jLfriendPortrait = new JLabel();
    JLabel jLfriendName=new JLabel("我一直都在");
    JLabel jLfriendState = new JLabel("在线");
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/chatView.jpg"));

    JTextArea jTextArea = new JTextArea();
    JButton jButton = new JButton();
    ImageIcon imageIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/CVsend.jpg");
    /*------------显示与好友的聊天信息------------------------------*/
    JPanel jPanel = new JPanel();
    JScrollPane jScrollPane = new JScrollPane();
    JList jList = new JList();

    /*------------功能区-----------*/
    private ArrayList<PersonMessage> list = new ArrayList<>();
    private TaskPackage taskPackage;
    private User own;
    private User friend;
    private MainView mainView;
    private boolean state = true;

    public ChatView(User own,User friend,TaskPackage taskPackage,MainView mainView){
        this.mainView = mainView;
        this.taskPackage = taskPackage;
        this.setSize(447,663);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.friend = friend;
        this.own = own;
        loadInfo();
        init();
        this.add(jLfriendPortrait);
        this.add(jLfriendName);
        this.add(jLfriendState);
        this.add(jPanel);
        this.add(jTextArea);
        this.add(jButton);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.getPersonChats().remove(friend.getId());
                ChatView.this.close();
            }
        });
    }

    public void loadInfo(){
        if(taskPackage==null) return;
        list.clear();
        jLfriendPortrait.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+friend.getHead()+".gif"));
        jLfriendName.setText(friend.getName());
        jLfriendState.setText(friend.getState()==0?"离线":"在线");
        int number = Integer.parseInt(taskPackage.getInfo("messageNum"));
        for (int i = number-1; i >= 0; i--) {
            String key = "message"+i+"@";
            String c1 = key + "from_id";
            String c2 = key + "to_id";
            String c3 = key + "content";
            String c4 = key + "type";
            String c5 = key + "date";
            PersonMessage personMessage = new PersonMessage();
            personMessage.setFrom_id(Integer.parseInt(taskPackage.getInfo(c1)));
            personMessage.setTo_id(Integer.parseInt(taskPackage.getInfo(c2)));
            personMessage.setContent(taskPackage.getInfo(c3));
            personMessage.setType(Integer.parseInt(taskPackage.getInfo(c4)));
            personMessage.setDate(Long.parseLong(taskPackage.getInfo(c5)));
            String name1;
            String name2;
            int head;
            if(own.getId()==personMessage.getFrom_id()){
                name1 = own.getName();
                name2 = friend.getName();
                head = own.getHead();
            }else{
                name1 = friend.getName();
                name2 = own.getName();
                head = friend.getHead();
            }
            personMessage.setName(name1);
            personMessage.setFriendName(name2);
            personMessage.setSenderHead(head);
            list.add(personMessage);
        }

        PersonMessageListModel messageListModel = new PersonMessageListModel(list);
        jList.setModel(messageListModel);
        jList.setCellRenderer(new PersonMessqgeListCellRenderer());
        jList.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        jList.setPreferredSize(new Dimension(360,350));
        jScrollPane.setPreferredSize(new Dimension(360,350));
        taskPackage = null;
    }

    public void init(){
        jBack.setBounds(0,0,429,643);
        jLfriendPortrait.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+friend.getHead()+".gif"));
        jLfriendPortrait.setBounds(5,0,32,32);
        jLfriendName.setBounds(40,2,150,15);
        jLfriendName.setFont(new Font("宋体",Font.BOLD,10));
        jLfriendName.setText(friend.getName()+(friend.getRemark().length()==0?"":("("+friend.getRemark()+")")));
        jLfriendState.setFont(new Font("宋体",Font.BOLD,10));
        jLfriendState.setBounds(40,17,100,15);
        jLfriendState.setText(friend.getState()==0?"离线":"在线");
        jPanel.setBounds(5,32,420,368);
        jPanel.setBackground(new Color(219,248,254,60));
        jScrollPane.setViewportView(jList);
        jPanel.add(jScrollPane);

        jTextArea.setLineWrap(true);
        jTextArea.setBounds(5,405,420,190);
        jTextArea.setFont(new Font("楷体",Font.BOLD,16));
        jTextArea.setBackground(new Color(255,235,181));
        jButton.setBounds(335,600,80,23);//80*23
        jButton.setIcon(imageIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送消息
                if(jTextArea.getText().length()==0){
                    return;
                }
                TaskPackage t = new TaskPackage(7,5,new HashMap<>());
                t.getHashMap().put("from_id",String.valueOf(own.getId()));
                t.getHashMap().put("to_id",String.valueOf(friend.getId()));
                t.getHashMap().put("type",String.valueOf(1));
                t.getHashMap().put("content",jTextArea.getText());
                long time = System.currentTimeMillis();
                t.getHashMap().put("date",String.valueOf(time));
                list.add(new PersonMessage(own.getId(),friend.getId(),jTextArea.getText(),1,time,own.getName(),friend.getName(), own.getHead()));
                jTextArea.setText("");
                PersonMessageListModel messageListModel = new PersonMessageListModel(list);
                jList.setModel(messageListModel);
                jScrollPane.setViewportView(jList);
                jScrollPane.updateUI();
                networkPackage.sendPackageToController(t);
            }
        });
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    class PersonMessageListModel extends AbstractListModel{
        ArrayList<PersonMessage> uArray;
        public PersonMessageListModel(ArrayList<PersonMessage> list){
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

    class PersonMessqgeListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            PersonMessage personMessage = (PersonMessage) value;
            long time = personMessage.getDate();
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setText("<html>"+simpleDateFormat.format(date)+"<br/>"+personMessage.getName()+"->"+personMessage.getFriendName()+"："+personMessage.getContent()+
                    "<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img =
                    new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+personMessage.getSenderHead()+".gif").getImage().getScaledInstance(32,32,
                    Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(5);
            return this;
        }
    }

    public void unpackMessage(TaskPackage t){
        long time = Long.parseLong(t.getInfo("date"));
        String content = t.getInfo("content");
        int type = Integer.parseInt(t.getInfo("type"));
        list.add(new PersonMessage(friend.getId(),own.getId(),content,type,time,friend.getName(),own.getName(),friend.getHead()));
        PersonMessageListModel messageListModel = new PersonMessageListModel(list);
        jList.setModel(messageListModel);
        jScrollPane.setViewportView(jList);
        jScrollPane.updateUI();
    }

    public void close(){
        state = false;
        this.dispose();
    }
}
