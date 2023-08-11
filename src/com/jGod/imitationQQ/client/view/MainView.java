package com.jGod.imitationQQ.client.view;

import com.jGod.imitationQQ.client.Bean.Group;
import com.jGod.imitationQQ.client.Bean.User;
import com.jGod.imitationQQ.client.controller.MainController;
import com.jGod.imitationQQ.client.controller.TaskPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jGod.imitationQQ.client.controller.MainController.networkPackage;
import static java.lang.Thread.sleep;

@SuppressWarnings("all")
public class MainView extends JFrame{
    JPanel jPanelTop = new JPanel(); //顶部
    JPanel jPanelCenter1 = new JPanel(); //中间1
    JPanel jPanelCenter2 = new JPanel(); //中间2
    JLabel jLTitle = new JLabel("山寨版qq");

    //个人信息
    JLabel jHead = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/resume.jpg"));
    JLabel jName = new JLabel("不良帅");
    JLabel jState = new JLabel("在线");
    JLabel jFunc = new JLabel("更多功能");

    JLabel jLFindGroup = new JLabel("加入群聊");
    JTextField jTFindGroup = new JTextField("");
    JButton jBFindGroup = new JButton(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/find.jpg"));//19*21
    JLabel jLFind = new JLabel("查找好友");
    JTextField jTFind = new JTextField("");
    JButton jBFind = new JButton(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/find.jpg"));//19*21
    //过渡
    JLabel jLMid_1 = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/mid.jpg"));//305*7
    JLabel jLMid_2 = new JLabel(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/mid.jpg"));//305*7
    //中部好友区
    JTabbedPane jTabbedPane = new JTabbedPane(); //选项板

    JList userList = new JList();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPopupMenu jPFriend = new JPopupMenu();
    JMenuItem jm1 = new JMenuItem("发送即时消息");
    JMenuItem jm2 = new JMenuItem("修改备注");
    JMenuItem jm3 = new JMenuItem("删除好友");

    //底部群聊区
    JList groupList = new JList();
    JScrollPane jScrollPane2 = new JScrollPane();
    JPopupMenu jPGroup = new JPopupMenu();
    JMenuItem jm4 = new JMenuItem("进入群聊");
    JMenuItem jm5 = new JMenuItem("修改备注");
    JMenuItem jm6 = new JMenuItem("退出群聊");

    /*---------------功能区-----------------*/
    private int applicationView = 0;
    private int receiveView = 0;
    private int refuseView = 0;
    private int deleteView = 0;
    private HashMap<Integer,ChatView> personChats = new HashMap<>();
    private HashMap<Integer,GroupView> groupChats = new HashMap<>();
    private ModifySelf modifySelf;
    private AddFriend addFriend;
    private ChangeFriendRemark changeFriendRemark;
    private ChangeGroupRemark changeGroupRemark;
    private CreateGroup createGroup;
    private ArrayList<User> friends;
    private User own;
    private ArrayList<Group> groups;
    private TaskPackage taskPackage;
    private boolean mainState = true;

    private int friendIndex;
    private int groupIndex;

    private int pre = -1;

    private MainController mainController;

    public MainView(TaskPackage taskPackage, MainController mainController){
        this.mainController = mainController;
        this.taskPackage = taskPackage;
        this.setSize(313,590);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadInfo();
        init();
        this.add(jPanelTop);
        this.add(jTabbedPane);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainView.this.dispose();
                mainController.close();
            }
        });
    }

    public void loadInfo(){
        if(taskPackage==null) return;
        own = new User();
        if(friends!=null) friends.clear();
        if(groups!=null) groups.clear();
        friends = new ArrayList<>();
        groups = new ArrayList<>();
        own.setHead(Integer.parseInt(taskPackage.getInfo("head")));
        own.setName(taskPackage.getInfo("name"));
        own.setId(Integer.parseInt(taskPackage.getInfo("id")));
        own.setBirthday(taskPackage.getInfo("birthday"));
        own.setGender(taskPackage.getInfo("gender"));
        own.setAge(Integer.parseInt(taskPackage.getInfo("age")));
        jName.setText(own.getName());
        jHead.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+own.getHead()+".gif"));

        int friendNumber = Integer.parseInt(taskPackage.getInfo("friendNum"));
        int groupNumber = Integer.parseInt(taskPackage.getInfo("groupNum"));
        for (int i = 0; i < friendNumber; i++) {
            String key = "friend" + i + "@";
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
            friends.add(t);
        }
        FriendListModel friendListModel = new FriendListModel(friends);
        userList.setModel(friendListModel);
        userList.setCellRenderer(new FriendListCellRenderer());
        userList.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        userList.setPreferredSize(new Dimension(360,350));
        jScrollPane1.setViewportView(userList);
        jScrollPane1.setPreferredSize(new Dimension(360,350));

        for (int i = 0; i < groupNumber; i++) {
            String key = "group" + i + "@";
            String c1 = key + "head";
            String c2 = key + "name";
            String c3 = key + "number";
            String c4 = key + "id";
            String c5 = key + "master_id";
            Group g = new Group();
            g.setHead(Integer.parseInt(taskPackage.getInfo(c1)));
            g.setName(taskPackage.getInfo(c2));
            g.setNumber(Integer.parseInt(taskPackage.getInfo(c3)));
            g.setId(Integer.parseInt(taskPackage.getInfo(c4)));
            g.setMaster_id(Integer.parseInt(taskPackage.getInfo(c5)));
            groups.add(g);
        }
        GroupListModel groupListModel = new GroupListModel(groups);
        groupList.setModel(groupListModel);
        groupList.setCellRenderer(new GroupListCellRenderer());
        groupList.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        groupList.setPreferredSize(new Dimension(360,350));
        jScrollPane2.setViewportView(groupList);
        jScrollPane2.setPreferredSize(new Dimension(360,350));
        taskPackage = null;
    }

    public boolean isMainState() {
        return mainState;
    }

    public void setMainState(boolean mainState) {
        this.mainState = mainState;
    }

    public TaskPackage getTaskPackage() {
        return taskPackage;
    }

    public void setTaskPackage(TaskPackage taskPackage) {
        this.taskPackage = taskPackage;
    }

    public void init(){
        jPanelTop.setBounds(0,0,313,140);
        jPanelTop.setLayout(null);
        jPanelTop.setBackground(new Color(46,139,87));
        jLTitle.setForeground(Color.WHITE);
        jLTitle.setFont(new Font("黑体",Font.BOLD,15));
        jLTitle.setBounds(5,5,313,25);

        jHead.setBounds(10,33,32,32);
        jName.setForeground(Color.WHITE);
        jName.setFont(new Font("宋体",Font.BOLD,13));
        jName.setBounds(45,30,180,20);
        jState.setForeground(Color.BLUE);
        jState.setFont(new Font("宋体",Font.PLAIN,12));
        jState.setBounds(45,50,30,15);
        jFunc.setForeground(Color.GREEN);
        jFunc.setFont(new Font("宋体",Font.BOLD,14));
        jFunc.setBounds(80,50,200,25);
        JPopupMenu jPFunc = new JPopupMenu();
        JMenuItem jmf1 = new JMenuItem("好友申请记录");
        JMenuItem jmf2 = new JMenuItem("好友通过记录");
        JMenuItem jmf3 = new JMenuItem("好友拒绝记录");
        JMenuItem jmf4 = new JMenuItem("好友删除记录");
        JMenuItem jmf5 = new JMenuItem("创建群聊");
        jmf1.setFont(new Font("楷体",Font.PLAIN,14));
        jmf1.setForeground(Color.BLUE);
        jmf2.setFont(new Font("楷体",Font.PLAIN,14));
        jmf2.setForeground(Color.BLUE);
        jmf3.setFont(new Font("楷体",Font.PLAIN,14));
        jmf3.setForeground(Color.BLUE);
        jmf4.setFont(new Font("楷体",Font.PLAIN,14));
        jmf4.setForeground(Color.BLUE);
        jmf5.setFont(new Font("楷体",Font.PLAIN,14));
        jmf5.setForeground(Color.BLUE);

        jPFunc.add(jmf1);
        jPFunc.add(jmf2);
        jPFunc.add(jmf3);
        jPFunc.add(jmf4);
        jPFunc.add(jmf5);

        jmf1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //好友申请记录
                if(applicationView>0){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                applicationView++;
                TaskPackage t = new TaskPackage(11,0,null);
                networkPackage.sendPackageToController(t);
            }
        });
        jmf2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //好友申请通过记录
                if(receiveView>0){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                receiveView++;
                TaskPackage t = new TaskPackage(15,0,null);
                networkPackage.sendPackageToController(t);
            }
        });
        jmf3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //好友申请拒绝记录
                if(refuseView>0){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                refuseView++;
                TaskPackage t = new TaskPackage(14,0,null);
                networkPackage.sendPackageToController(t);
            }
        });
        jmf4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //好友删除记录
                if(deleteView>0){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                deleteView++;
                TaskPackage t = new TaskPackage(16,0,null);
                networkPackage.sendPackageToController(t);
            }
        });
        jmf5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建群聊
                if(createGroup!=null){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                createGroup = new CreateGroup(MainView.this);
                createGroup.setVisible(true);
            }
        });
        jFunc.setComponentPopupMenu(jPFunc);

        JMenuItem jm0 = new JMenuItem("修改个人资料");
        jm0.setFont(new Font("楷体",Font.PLAIN,14));
        jm0.setForeground(Color.BLACK);
        jm0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改个人信息
                if(modifySelf!=null){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                modifySelf = new ModifySelf(own,MainView.this);
                modifySelf.setVisible(true);
            }
        });

        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(jm0);
        jName.setComponentPopupMenu(jPopupMenu);

        jLFindGroup.setFont(new Font("楷体",Font.BOLD,14));
        jLFindGroup.setBounds(3,80,80,25);
        jTFindGroup.setFont(new Font("宋体",Font.BOLD,12));
        jTFindGroup.setForeground(Color.GRAY);
        jTFindGroup.setBounds(85,80,178,25);
        jBFindGroup.setBounds(267,82,19,21);
        jBFindGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //加入群聊
                int num = 0;
                try {
                    num = Integer.parseInt(jTFindGroup.getText());
                } catch (NumberFormatException ex) {
                    Warn warn = new Warn("群号不是纯数字！");
                    warn.setVisible(true);
                }
                if(pre == num){
                    Warn warn = new Warn("请勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                pre = num;
                TaskPackage t = new TaskPackage(20,1,new HashMap<>());
                t.getHashMap().put("group_id",String.valueOf(num));
                networkPackage.sendPackageToController(t);
            }
        });

        jLFind.setFont(new Font("楷体",Font.BOLD,14));
        jLFind.setBounds(3,110,80,25);
        jTFind.setFont(new Font("宋体",Font.BOLD,12));
        jTFind.setForeground(Color.GRAY);
        jTFind.setBounds(85,110,178,25);
        jBFind.setBounds(267,112,19,21);
        jBFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加好友
                int friend_id = 0;
                try {
                    friend_id = Integer.parseInt(jTFind.getText());
                } catch (NumberFormatException ex) {
                    Warn warn = new Warn("账号不是纯数字！");
                    warn.setVisible(true);
                    return;
                }
                if(addFriend!=null) return;
                addFriend = new AddFriend(friend_id,MainView.this);
                addFriend.setVisible(true);
            }
        });
        jPanelTop.add(jBFind);
        jPanelTop.add(jLFindGroup);
        jPanelTop.add(jTFindGroup);
        jPanelTop.add(jBFindGroup);
        jPanelTop.add(jLFind);
        jPanelTop.add(jTFind);
        jPanelTop.add(jName);
        jPanelTop.add(jState);
        jPanelTop.add(jFunc);
        jPanelTop.add(jHead);
        jPanelTop.add(jLTitle);
        //过渡1
        jLMid_1.setBounds(0,140,313,7);
        //中间
        jPFriend.add(jm1);
        jPFriend.add(jm2);
        jPFriend.add(jm3);

        jm1.setFont(new Font("楷体",Font.PLAIN,14));
        jm1.setForeground(Color.BLUE);
        jm2.setFont(new Font("楷体",Font.PLAIN,14));
        jm2.setForeground(Color.BLUE);
        jm3.setFont(new Font("楷体",Font.PLAIN,14));
        jm3.setForeground(Color.BLUE);

        jm1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送及时消息
                int friend_id = friends.get(friendIndex).getId();
                if(personChats.containsKey(friend_id)){
                    Warn warn = new Warn("请等待，切勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                personChats.put(friend_id,null);
                TaskPackage t = new TaskPackage(4,1,new HashMap<>());
                t.getHashMap().put("friend_id",String.valueOf(friend_id));
                networkPackage.sendPackageToController(t);
            }
        });

        jm2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改备注
                User friend = friends.get(friendIndex);
                if(changeFriendRemark!=null){
                    Warn warn = new Warn("请等待，切勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                changeFriendRemark = new ChangeFriendRemark(friend,MainView.this);
                changeFriendRemark.setVisible(true);
            }
        });

        jm3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除好友
                int i = friendIndex;
                int friend_id = friends.get(i).getId();
                TaskPackage t = new TaskPackage(3,1,new HashMap<>());
                t.getHashMap().put("friend_id",String.valueOf(friend_id));
                friends.remove(i);
                FriendListModel friendListModel = new FriendListModel(friends);
                userList.setModel(friendListModel);
                jScrollPane1.setViewportView(userList);
                jScrollPane1.updateUI();
                networkPackage.sendPackageToController(t);

            }
        });
        userList.setComponentPopupMenu(jPFriend);
        userList.addMouseMotionListener(new PersonChatMouseMotionAdapter());

        //选项板
        jTabbedPane.setForeground(Color.DARK_GRAY);
        jTabbedPane.setBackground(Color.WHITE);
        jTabbedPane.setFont(new Font("宋体",Font.PLAIN,11));
        jTabbedPane.addTab("联系人",jPanelCenter1);
        jTabbedPane.setBounds(0,147,313,382);
        jPanelCenter1.setLayout(new BorderLayout());
        jPanelCenter1.setBackground(Color.WHITE);
        jPanelCenter1.setBounds(0,147,313,382);
        jPanelCenter1.add(jScrollPane1);

        jPGroup.add(jm4);
        jPGroup.add(jm5);
        jPGroup.add(jm6);
        jPGroup.setBackground(Color.WHITE);
        jPGroup.setBounds(0,147,313,382);
        jTabbedPane.add("群组",jPanelCenter2);

        jm4.setFont(new Font("楷体",Font.PLAIN,14));
        jm4.setForeground(Color.BLUE);
        jm5.setFont(new Font("楷体",Font.PLAIN,14));
        jm5.setForeground(Color.BLUE);
        jm6.setFont(new Font("楷体",Font.PLAIN,14));
        jm6.setForeground(Color.BLUE);
        jm4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //进入群聊
                int group_id = groups.get(groupIndex).getId();
                if(groupChats.containsKey(group_id)){
                    Warn warn = new Warn("请等待，切勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                groupChats.put(group_id,null);
                TaskPackage t = new TaskPackage(19,1,new HashMap<>());
                t.getHashMap().put("group_id",String.valueOf(group_id));
                networkPackage.sendPackageToController(t);
            }
        });
        jm5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改群聊备注
                int group_id = groups.get(groupIndex).getId();
                if(changeGroupRemark!=null){
                    Warn warn = new Warn("请等待，切勿重复点击！");
                    warn.setVisible(true);
                    return;
                }
                changeGroupRemark = new ChangeGroupRemark(group_id,MainView.this);
                changeGroupRemark.setVisible(true);
            }
        });
        jm6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //退出群聊
                int i = groupIndex;
                int group_id = groups.get(i).getId();
                if(groups.get(i).getMaster_id()==own.getId()){
                    Warn warn = new Warn("群主不允许离开群聊！");
                    warn.setVisible(true);
                    return;
                }
                TaskPackage t = new TaskPackage(21,1,new HashMap<>());
                t.getHashMap().put("group_id",String.valueOf(group_id));
                groups.remove(i);
                GroupListModel groupListModel = new GroupListModel(groups);
                userList.setModel(groupListModel);
                jScrollPane2.setViewportView(groupList);
                jScrollPane2.updateUI();
                networkPackage.sendPackageToController(t);
            }
        });
        jPanelCenter2.setLayout(new BorderLayout());
        jPanelCenter2.setBackground(Color.WHITE);
        jPanelCenter2.setBounds(0,147,313,382);
        jPanelCenter2.add(jScrollPane2);
        groupList.setComponentPopupMenu(jPGroup);
        groupList.addMouseMotionListener(new GroupChatMouseMotionAdapter());
        //底部
        jLMid_2.setBounds(0,529,313,7);
    }

    public void addNewFriend(TaskPackage t){
        if(t==null) return;
        User u = new User();
        u.setHead(Integer.parseInt(t.getInfo("head")));
        u.setName(t.getInfo("name"));
        u.setState(Integer.parseInt(t.getInfo("state")));
        u.setId(Integer.parseInt(t.getInfo("id")));
        u.setRemark(t.getInfo("remark"));
        friends.add(u);
        FriendListModel friendListModel = new FriendListModel(friends);
        userList.setModel(friendListModel);
        jScrollPane1.setViewportView(userList);
        jScrollPane1.updateUI();
    }

    public void addNewGroup(TaskPackage t){
        Group g = new Group();
        g.setHead(Integer.parseInt(t.getInfo("head")));
        g.setName(t.getInfo("name"));
        g.setNumber(Integer.parseInt(t.getInfo("number")));
        g.setId(Integer.parseInt(t.getInfo("id")));
        g.setMaster_id(Integer.parseInt(t.getInfo("master_id")));
        groups.add(g);
        GroupListModel groupListModel = new GroupListModel(groups);
        groupList.setModel(groupListModel);
        jScrollPane2.setViewportView(groupList);
        jScrollPane2.updateUI();
    }

    public void friendOnline(TaskPackage t){
        if(t==null) return;
        int friend_id = Integer.parseInt(t.getInfo("friend_id"));
        for (User user :friends) {
            if(user.getId()==friend_id && user.getState()==0){
                user.setState(1);
                jScrollPane1.updateUI();
                break;
            }
        }
    }

    public void updateSelf(){
        jName.setText(own.getName());
        jHead.setIcon(new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+own.getHead()+".gif"));
    }

    public void updateFriendRemark(){
        jScrollPane1.updateUI();
    }

    public void updateGroupRemark(){
        jScrollPane2.updateUI();
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public User getOwn() {
        return own;
    }

    public void setOwn(User own) {
        this.own = own;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    class FriendListModel extends AbstractListModel{
        ArrayList<User> uArray;
        public FriendListModel(ArrayList<User> list){
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

    class FriendListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            User user = (User) value;
            String s = (user.getRemark()==null||user.getRemark().length()==0)?"":("("+user.getRemark()+")");
            setText("<html>"+user.getName()+s+"<br/>"+(user.getState()==1?
                    "在线":"离线")+ "<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+user.getHead()+".gif").getImage().getScaledInstance(32,32,
                    Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(10);
            return this;
        }
    }
    class GroupListModel extends AbstractListModel{
        ArrayList<Group> uArray;
        public GroupListModel(ArrayList<Group> list){
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

    class GroupListCellRenderer extends JLabel implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Group group = (Group) value;
            setText("<html>"+group.getName()+"<br/>"+"人数："+String.valueOf(group.getNumber())+"<html/>");
            setFont(new Font("楷体",Font.BOLD,13));
            Image img = new ImageIcon("src/com/jGod/imitationQQ/client/view/image/"+group.getHead()+".gif").getImage().getScaledInstance(32,32,
                    Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(img));
            setIconTextGap(10);
            return this;
        }
    }

    class PersonChatMouseMotionAdapter extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e){
            if(!friends.isEmpty()){
                friendIndex = userList.locationToIndex(e.getPoint());
            }
        }
    }

    class GroupChatMouseMotionAdapter extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e){
            if(!groups.isEmpty()){
                groupIndex = groupList.locationToIndex(e.getPoint());
            }
        }
    }

    public ModifySelf getModifySelf() {
        return modifySelf;
    }

    public void setModifySelf(ModifySelf modifySelf) {
        this.modifySelf = modifySelf;
    }

    public AddFriend getAddFriend() {
        return addFriend;
    }

    public void setAddFriend(AddFriend addFriend) {
        this.addFriend = addFriend;
    }

    public void exit(){
        this.setMainState(false);
        this.setVisible(false);
    }

    public HashMap<Integer, ChatView> getPersonChats() {
        return personChats;
    }

    public void setPersonChats(HashMap<Integer, ChatView> personChats) {
        this.personChats = personChats;
    }

    public HashMap<Integer, GroupView> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(HashMap<Integer, GroupView> groupChats) {
        this.groupChats = groupChats;
    }

    public ChangeFriendRemark getChangeFriendRemark() {
        return changeFriendRemark;
    }

    public void setChangeFriendRemark(ChangeFriendRemark changeFriendRemark) {
        this.changeFriendRemark = changeFriendRemark;
    }

    public int getApplicationView() {
        return applicationView;
    }

    public void setApplicationView(int applicationView) {
        this.applicationView = applicationView;
    }

    public int getReceiveView() {
        return receiveView;
    }

    public void setReceiveView(int receiveView) {
        this.receiveView = receiveView;
    }

    public int getRefuseView() {
        return refuseView;
    }

    public void setRefuseView(int refuseView) {
        this.refuseView = refuseView;
    }

    public int getDeleteView() {
        return deleteView;
    }

    public void setDeleteView(int deleteView) {
        this.deleteView = deleteView;
    }

    public ChangeGroupRemark getChangeGroupRemark() {
        return changeGroupRemark;
    }

    public void setChangeGroupRemark(ChangeGroupRemark changeGroupRemark) {
        this.changeGroupRemark = changeGroupRemark;
    }

    public CreateGroup getCreateGroup() {
        return createGroup;
    }

    public void setCreateGroup(CreateGroup createGroup) {
        this.createGroup = createGroup;
    }
}
