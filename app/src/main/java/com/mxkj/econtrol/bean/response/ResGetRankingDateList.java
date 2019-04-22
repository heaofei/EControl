package com.mxkj.econtrol.bean.response;


import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 *  获取控制排名的日期信息接收实体类
 * Created by ${  chenjun  } on 2017/10/31.
 */

public class ResGetRankingDateList extends BaseResponse {


    private List<RankingDateBean> list;


    public List<RankingDateBean> getList() {
        return list;
    }

    public void setList(List<RankingDateBean> list) {
        this.list = list;
    }

    public class RankingDateBean {
        private String dateText;
        private String dateValue;
        private boolean isClick= false;

        public String getDateText() {
            return dateText;
        }

        public void setDateText(String dateText) {
            this.dateText = dateText;
        }

        public String getDateValue() {
            return dateValue;
        }

        public void setDateValue(String dateValue) {
            this.dateValue = dateValue;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }
    }

}
