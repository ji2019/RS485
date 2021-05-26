package com.iw2f.netty.bo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AntRequestCmd {
	
	private int hub_id;
	private String hubMacAddr;
	private byte dev_type;
	private int transmissionType;
	private int dev_id;
	private int hr;
	private long datetime;
	private long replay;
	@JSONField(serialize = false)
	private byte[] data;
	/**
	 * 卡路里或步数 1 步数 0 卡路里
	 */
	private int sorc = -1;
	
	private double calorie = 0;
	
	private int steps = 0;
	
	// 80 00  为同一页
	private byte page_number;
	
	public AntRequestCmd(byte[] ts) {
		this.data = ts;
		
		//解析整个包
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}