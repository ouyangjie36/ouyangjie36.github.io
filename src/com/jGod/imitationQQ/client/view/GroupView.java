package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Group;
import com.jGod.imitationQQ.client.Bean.GroupMessage;
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

@SuppressWarnings("all")
public class GroupView extends JFrame{
    JLabel jBack = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/groupView.jpg"));
    JLabel jGroupHead = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/0.gif"));
    JLabel jGroupName = new JLabel("群名");
    JLabel jGroupNum = new JLabel("群号");
    JLabel jGroupMaster = new JLabel("群主号");
    JLabel jGroupNumberOfUser = new JLabel("人数");

    JTextArea jTextArea = new JTextArea();
    JButton jButton = new JButton();
    ImageIcon imageIcon = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/CVsend.jpg");
    /*---------------*/
    JScrollPane jScrollPane1 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JList jList1 = new JList();
    JList jList2 = new JList();
    /*-------功能区--------*/
    private ArrayList<GroupMessage> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private User own;
    private Group group;
    private TaskPackage taskPackage;
    private MainView mainView;

    public GroupView(User own, Group group, TaskPackage taskPackage,MainView mainView){
        this.mainView = mainView;
        this.own = own;
        this.group = group;
        this.taskPackage = taskPackage;
        this.setSize(582,602);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadInfo();
        init();
        this.add(jScrollPane1);
        this.add(jScrollPane2);
        this.add(jGroupHead);
        this.add(jGroupName);
        this.add(jGroupNum);
        this.add(jGroupMaster);
        this.add(jGroupNumberOfUser);
        this.add(jTextArea);
        this.add(jButton);
        this.add(jBack);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(mainView!=null) mainView.getGroupChats().remove(group.getId());
                GroupView.this.dispose();
            }
        });
    }

    public void loadInfo(){
        if(taskPackage==null) return;
        users.clear();
        messages.clear();

        jGroupHead.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+group.getHead()+".gif"));
        jGroupNum.setText("群ID："+String.valueOf(group.getId()));
        jGroupName.setText("群名："+group.getName());
        jGroupMaster.setText("群主ID："+String.valueOf(group.getMaster_id()));
        jGroupNumberOfUser.setText("群成员人数："+String.valueOf(group.getNumber()));
        int len1 = Integer.parseInt(taskPackage.getInfo("userNum"));
        int len2 = Integer.parseInt(taskPackage.getInfo("messageNum"));

        for (int i = 0; i < len1; i++) {
            String key = "user" + i + "@";
            String c1 = key + "head";
            String c2 = key + "name";
            String c3 = key + "remark";
            String c4 = key + "id";
            String c5 = key + "state";
            User t = new User();
            t.setHead(Integer.parseInt(taskPackage.getInfo(c1)));
            t.setName(taskPackage.getInfo(c2));
            t.setId(Integer.parseInt(taskPackage.getInfo(c4)));
            t.setRemark(taskPackage.getInfo(c3));
            t.setState(Integer.parseInt(taskPackage.getInfo(c5)));
            users.add(t);
        }
        UserListModel userListModel = new UserListModel(users);
        jList1.setModel(userListModel);
        jList1.setCellRenderer(new UserListCellRenderer());
        jList1.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        jList1.setPreferredSize(new Dimension(360,350));
        jScrollPane1.setViewportView(jList1);
        jScrollPane1.setPreferredSize(new Dimension(360,350));

        for (int i = len2-1; i >= 0; i--) {
            String key = "message" + i + "@";
            String c1 = key + "from_id";
            String c2 = key + "group_id";
            String c3 = key + "content";
            String c4 = key + "type";
            String c5 = key + "date";
            GroupMessage groupMessage = new GroupMessage();
            groupMessage.setFrom_id(Integer.parseInt(taskPackage.getInfo(c1)));
            groupMessage.setGroup_id(Integer.parseInt(taskPackage.getInfo(c2)));
            groupMessage.setContent(taskPackage.getInfo(c3));
            groupMessage.setType(Integer.parseInt(taskPackage.getInfo(c4)));
            groupMessage.setDate(Long.parseLong(taskPackage.getInfo(c5)));
            for (User user :users) {
                if(user.getId()==groupMessage.getFrom_id()){
                    groupMessage.setSenderHead(user.getHead());
                    groupMessage.setSenderName(user.getName());
                    break;
                }
            }
            messages.add(groupMessage);
        }
        GroupMessageListModel messageListModel = new GroupMessageListModel(messages);
        jList2.setModel(messageListModel);
        jList2.setCellRenderer(new GroupMessqgeListCellRenderer());
        jList2.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        jList2.setPreferredSize(new Dimension(360,350));
        jScrollPane2.setViewportView(jList2);
        jScrollPane2.setPreferredSize(new Dimension(360,350));
        taskPackage = null;
    }

    public void init(){
        jBack.setBounds(0,0,562,562);
        jGroupHead = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+group.getHead()+".gif"));
        jGroupHead.setBounds(400,8,32,32);
        jGroupNum.setFont(new Font("宋体",Font.BOLD,12));
        jGroupNum.setBounds(400,40,150,20);
        jGroupName.setBounds(400,60,150,20);
        jGroupName.setFont(new Font("宋体",Font.BOLD,12));
        jGroupMaster.setBounds(400,80,150,20);
        jGroupMaster.setFont(new Font("宋体",Font.BOLD,12));
        jGroupNumberOfUser.setBounds(400,100,150,20);
        jGroupNumberOfUser.setFont(new Font("宋体",Font.BOLD,12));

        jScrollPane1.setBounds(400,120,162,442);
        jScrollPane1.setBackground(new Color(219,248,254,60));
        jScrollPane2.setBounds(5,5,390,335);
        jScrollPane2.setBackground(new Color(219,248,254,60));

        jTextArea.setLineWrap(true);
        jTextArea.setBounds(5,345,390,180);
        jTextArea.setFont(new Font("楷体",Font.BOLD,14));
        jTextArea.setBackground(new Color(219,248,254));
        jButton.setBounds(315,530,80,23);//80*23
        jButton.setIcon(imageIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送消息
                if(jTextArea.getText().length()==0){
                    return;
                }
                TaskPackage t = new TaskPackage(22,5,new HashMap<>());
                t.getHashMap().put("from_id",String.valueOf(own.getId()));
                t.getHashMap().put("group_id",String.valueOf(group.getId()));
                t.getHashMap().put("type",String.valueOf(1));
                t.getHashMap().put("content",jTextArea.getText());
                long time = System.currentTimeMillis();
                t.getHashMap().put("date",String.valueOf(time));
                messages.add(new GroupMessage(own.getId(),group.getId(),jTextArea.getText(),1,time,own.getHead(),own.getName(),own.getRemark()));
                jTextArea.setText("");
                GroupMessageListModel messageListModel = new GroupMessageListModel(messages);
                jList2.setModel(messageListModel);
                jScrollPane2.setViewportView(jList2);
                jScrollPane2.updateUI();
                networkPackage.sendPackageToController(t);
            }
        });
    }

    public void unpackMessage(TaskPackage t){
        long time = Long.parseLong(t.getInfo("date"));
        String content = t.getInfo("content");
        int type = Integer.parseInt(t.getInfo("type"));
        int from_id = Integer.parseInt(t.getInfo("from_id"));
        User u = null;
        for (User user :users) {
            if(user.getId()==from_id){
                u = user;
                break;
            }
        }
        if(u==null) return;
        messages.add(new GroupMessage(from_id,group.getId(),content,type,time,u.getHead(),u.getName(),u.getRemark()));
        GroupMessageListModel messageListModel = new GroupMessageListModel(messages);
        jList2.setModel(messageListModel);
        jScrollPane2.setViewportView(jList2);
        jScrollPane2.updateUI();
    }

    public void newUser(TaskPackage t){
        User nu = new User();
        nu.setId(Integer.parseInt(t.getInfo("id")));
        nu.setHead(Integer.parseInt(t.getInfo("head")));
        nu.setState(Integer.parseInt(t.getInfo("state")));
        nu.setName(t.getInfo("name"));
        nu.setRemark(t.getInfo("remark"));
        group.setNumber(group.getNumber()+1);
        jGroupNumberOfUser.setText(String.valueOf("群成员人数："+group.getNumber()));
        users.add(nu);
        UserListModel userListModel = new UserListModel(users);
        jList1.setModel(userListModel);
        jScrollPane1.setViewportView(jList1);
        jScrollPane1.updateUI();
        mainView.getjScrollPane2().updateUI();
    }

    public void removeUser(TaskPackage t){
        int i = 0;
        int id = Integer.parseInt(t.getInfo("id"));
        for (; i < users.size(); i++) {
            if(id == users.get(i).getId()){
                break;
            }
        }
        if(i<users.size()){
            users.remove(i);
        }
        group.setNumber(group.getNumber()-1);
        jGroupNumberOfUser.setText("群成员人数："+String.valueOf(group.getNumber()));
        UserListModel userListModel = new UserListModel(users);
        jList1.setModel(userListModel);
        jScrollPane1.setViewportView(jList1);
        jScrollPane1.updateUI();
        mainView.getjScrollPane2().updateUI();
    }

    class GroupMessageListModel extends AbstractListModel{
        ArrayList<GroupMessage> uArray;
        public GroupMessageListModel(ArrayList<GroupMessage> list){
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

    class GroupMessqgeListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            GroupMessage groupMessage = (GroupMessage) value;
            long time = groupMessage.getDate();
            Date date = new Date(time);
            String s = (groupMessage.getSenderRemark()==null||groupMessage.getSenderRemark().length()==0?"":"("+groupMessage.getSenderRemark()+")");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setText("<html>"+simpleDateFormat.format(date)+"<br/>"+groupMessage.getSenderName()+s+"："+groupMessage.getContent()+
                    "<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img =
                    new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+groupMessage.getSenderHead()+".gif").getImage().getScaledInstance(32,32,
                            Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(5);
            return this;
        }
    }
    class UserListModel extends AbstractListModel{
        ArrayList<User> uArray;
        public UserListModel(ArrayList<User> list){
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

    class UserListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            User user = (User) value;
            setText("<html>"+user.getName()+(user.getRemark()==null||user.getRemark().length()==0?"":
            "("+user.getRemark()+")")+"<br/>"+(user.getState()==1?
                    "在线":"离线")+
                    "<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+user.getHead()+".gif").getImage().getScaledInstance(32,32,
                    Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(10);
            return this;
        }
    }
}
