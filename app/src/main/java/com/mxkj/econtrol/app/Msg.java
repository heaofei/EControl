package com.mxkj.econtrol.app;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description: 定义消息的Id
 */

public class Msg {

    //显示加载中
    public final static int SHOW_LOADING = 0x1;
    //隐藏加载中
    public final static int DISMISS_LOADING = 0x2;
    //TCP命令执行成功
    public final static int TCP_CMD_SUCCESS = 0x3;
    //TCP命令执行失败
    public final static int TCP_CMD_FAIL = 0x4;
    //部件状态改变
    public final static int SAMRT_PART_STATE_CHANGE = 0x5;
    //消息通知
    public final static int MESSAGE_NOTIFY = 0x6;
    //清除TCP命令回调
    public final static int CLEAR_TCP_CMD_LISENTER = 0x7;
    //关闭所有的activity
    public final static int CLEAR_ACITVITY = 0x8;
    //登录失效
    public final static int LOGON_FAILURE = 0x9;
    //审核结果消息提醒
    public final static int CHECK_RESULT = 0x10;
    //关闭主场景activity
    public final static int CLOSE_HOUSE_MAIN_ACTIVITY = 0x11;
    //在家人数变化通知
    public final static int AT_HOME_STATE_CHANGE = 0x12;
    //修改个人信息通知
    public final static int CHANGE_USER_INFO = 0x13;
    //房子数量发生变化通知
    public final static int MY_HOUSE_CHANGE = 0x14;
    //模式发生变化 (其他成员切换)
    public final static int MY_PART_CHANGE = 0x15;
    //收到审核消息提醒（房主）
    public final static int CHECK_EXAMINE = 0x17;
    //切换房子，刷新使用习惯数据
    public final static int  SWITCH_HOUSE = 0x18;
    //门锁添加成功，刷新界面
    public final static int  LOCK_ADD_SUCCESS = 0x19;
    //门锁审核提交成功，刷新界面
    public final static int  LOCK_APPLY_SUCCESS = 0x20;
    // 门锁手势密码错误
    public final static int  LOCK_PESSWORD_GRANT_ERROR = 0x21;


    // 自己推出非默认房子，刷新房子列表
    public final static int EVENBUS_HOUSE_MESSAGE_5553 = 0x22;
    // 自己修改默认房子
    public final static int EVENBUS_HOUSE_MESSAGE_5554 = 0x23;

    //旧业主转让指管理权给新业主后，新业主接收处理
    public final static int HOUSE_MANAGER_SWITCH_ASSIGN = 0x24;
    //业主更换后通知成员
    public final static int HOUSE_MANAGER_SWITCH_HANDLE = 0x25;
    // 个人中心未读消息数量evenbus提醒
    public final static int FRAGMENT_MY_MESSAGE_TOTAL = 0x26;
    //灯光改变颜色、亮度，在fragment里操作
    public final static int ACTIVITY_LIGHT_CHANGE = 0x27;
    // tcp返回的locklist结果列表
    public final static int EVENBUS_SEARCH_LOCK_RESULT = 0x28;
    // tcp收到门锁警报消息
    public final static int EVENBUS_LOCK_ALERTS = 0x29;
    // tcp收到门锁电池消息
    public final static int EVENBUS_LOCK_BATTER_MESSAGE = 0x30;

    // 主页，打开侧栏消息
    public final static int EVENBUS_OPEN_CHELAN_MESSAGE = 0x31;
    // 侧栏消息--更新常用列表数据
    public final static int EVENBUS_UPDE__DATELIST = 0x32;
    // 侧栏消息--更新房子信息
    public final static int EVENBUS_UPDE__HOUSE_NAME = 0x33;


    public final static int showtcp = 0x35;


    public final static String LOCK_APPLY_PASSWORD_STATUES_NOT_SUBMIT = "notSubmited";// 手势密码提交 ：未提交
    public final static String LOCK_APPLY_PASSWORD_STATUES_WAITING = "waiting";// 手势密码提交 ：等待审核
    public final static String LOCK_APPLY_PASSWORD_STATUES_REJECTED = "rejected";// 手势密码提交 ：拒绝
    public final static String LOCK_APPLY_PASSWORD_STATUES_PASSED = "passed"; // 手势密码提交 ：通过审核

    public final static String LOCK_GESTURE_PASSWORD_STATUES_UNINITED = "uninited";// 手势密码未初始化
    public final static String LOCK_GESTURE_PASSWORD_STATUES_LOCKED = "locked";  //手势密码已锁定
    public final static String LOCK_GESTURE_PASSWORD_STATUES_NORMAL = "normal"; //手势密码正常






}
