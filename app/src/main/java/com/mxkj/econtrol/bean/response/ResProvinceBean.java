package com.mxkj.econtrol.bean.response;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 *
 */

public class ResProvinceBean   implements IPickerViewData {

	private String name;

	public ResProvinceBean(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPickerViewText() {
		return name;
	}
}
