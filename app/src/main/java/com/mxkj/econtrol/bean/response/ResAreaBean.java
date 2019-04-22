package com.mxkj.econtrol.bean.response;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 *
 * 获取省市区，返回实体类，三层数据结构
 */

public class ResAreaBean extends BaseResponse {


	private List<AreaListBean> areaList;

	public List<AreaListBean> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<AreaListBean> areaList) {
		this.areaList = areaList;
	}
	public static class AreaListBean implements IPickerViewData {
		private String areaId;
		private String areaCode;
		private String areaName;
		private String areaParentCode;
		private String level;
		private List<ChildList> childList;

		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

		public String getAreaCode() {
			return areaCode;
		}

		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getAreaParentCode() {
			return areaParentCode;
		}

		public void setAreaParentCode(String areaParentCode) {
			this.areaParentCode = areaParentCode;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public List<ChildList> getChildList() {
			return childList;
		}

		public void setChildList(List<ChildList> childList) {
			this.childList = childList;
		}

		// 实现 IPickerViewData 接口，
		// 这个用来显示在PickerView上面的字符串，
		// PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
		@Override
		public String getPickerViewText() {
			return this.areaName;
		}
	}


	public class ChildList{
		private String areaId;
		private String areaCode;
		private String areaName;
		private String areaParentCode;
		private String level;
		private List<ChildList02> childList;


		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

		public String getAreaCode() {
			return areaCode;
		}

		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getAreaParentCode() {
			return areaParentCode;
		}

		public void setAreaParentCode(String areaParentCode) {
			this.areaParentCode = areaParentCode;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}


		public List<ChildList02> getChildList02() {
			return childList;
		}

		public void setChildList02(List<ChildList02> childList02) {
			this.childList = childList02;
		}
	}



	public class ChildList02{
		private String areaId;
		private String areaCode;
		private String areaName;
		private String areaParentCode;
		private String level;


		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

		public String getAreaCode() {
			return areaCode;
		}

		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getAreaParentCode() {
			return areaParentCode;
		}

		public void setAreaParentCode(String areaParentCode) {
			this.areaParentCode = areaParentCode;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

	}





}
