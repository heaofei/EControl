package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chan on 2018/2/26.
 *
 * @Description:
 */

public class ResAppTodoList extends BaseResponse {


    private List<SwitchMasterTodoBean> switchMasterTodo;
    private List<MemberApplyTodoBean> memberApplyTodo;

    public List<MemberApplyTodoBean> getMemberApplyTodo() {
        return memberApplyTodo;
    }

    public void setMemberApplyTodo(List<MemberApplyTodoBean> memberApplyTodo) {
        this.memberApplyTodo = memberApplyTodo;
    }

    public List<SwitchMasterTodoBean> getSwitchMasterTodo() {
        return switchMasterTodo;
    }

    public void setSwitchMasterTodo(List<SwitchMasterTodoBean> switchMasterTodo) {
        this.switchMasterTodo = switchMasterTodo;
    }

    public static class SwitchMasterTodoBean implements Serializable{
        /**
         * todoMsg : 用户【13610295454】将住宅【测试小区测试单元T1001】的业主权限转让给您，是否同意接收
         * houseId : 40288b045f51814b015f523f6a3d000e
         */

        private String todoMsg;
        private String houseId;
        private String niceName;
        private String headpicture;

        public String getTodoMsg() {
            return todoMsg;
        }

        public void setTodoMsg(String todoMsg) {
            this.todoMsg = todoMsg;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getNiceName() {
            return niceName;
        }

        public void setNiceName(String niceName) {
            this.niceName = niceName;
        }

        public String getHeadpicture() {
            return headpicture;
        }

        public void setHeadpicture(String headpicture) {
            this.headpicture = headpicture;
        }
    }

    public static class MemberApplyTodoBean implements Serializable{
        private String todoMsg;
        private String houseId;

        public String getTodoMsg() {
            return todoMsg;
        }
        public void setTodoMsg(String todoMsg) {
            this.todoMsg = todoMsg;
        }
        public String getHouseId() {
            return houseId;
        }
        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }
    }
}
