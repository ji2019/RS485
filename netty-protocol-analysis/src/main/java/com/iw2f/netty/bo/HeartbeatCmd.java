package com.iw2f.netty.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;

@Getter
public class HeartbeatCmd {

	/**
	 * Hub_ID
	 */
	private Integer hub_id;
	
	/**
	 * mac 地址
	 */
	private String mac;
	
	/**
	 * 电量 quantity of electricity
	 */
	private Integer e_qty;
	
	/**
	 * 频率
	 */
	private Integer fcy;
	
	/**
	 * hub数据来源（ant+ + ble）
	 */
	private Integer src;
	
	/**
	 * 
	 */
	private Integer rssi;
	
	/**
	 * 内外网标识  1 外网 0 内网
	 */
	private Integer net;
	
	/**
	 * HUB版本信息
	 */
	private Integer version;
	
	@JSONField(serialize = false)
	private byte[] data;
	
	/******************************************************************************************
		心跳包
	 ******************************************************************************************/
	/**
	 * 
	 * AA 			//magic data  0
	 * 00 00 00 77 	//HUB_ID 1 4
	 * 00 00 		//包序号（注：心跳包的包序号设备只会使用00 00）  4 2
	 * 23			//version
	 * 00 24 		//len
	 * FF FF 00		//reserver
	 * 00 00 00 00 00 00 //hub mac addr
	 * 04			//cmd
	 * 01			//key
	 * 00 0B		//key pack len
	 * 320A000000000001000000 //pack data
	 * C6 77			//check sum
	 * 
	 */
	/**
	 * 32 	//电池电量
	 * 0A 	//数据发送频率
	 * 00	//hub数据来源（ant+ + ble）
	 * 00	//备注 len
	 * 00	//限制蓝牙名称长度
	 * 00	//限制蓝牙UUID长度
	 * 00	//rssi 信号
	 * 01	//内外网标志，外网
	 * 00	//服务器盒子IP len
	 * 00	//服务器盒子port len
	 * 00	//HUB版本信息
	 *    其中pack data里面我是用的信息只使用了电池电量、hub数据来源、内外网标志
	 * 
	 */
	public HeartbeatCmd(byte[] ts) {
		this.data = ts;
//		hub_id = U.byteArrayToInt(U.subBytes(ts, 1, 4));
//		mac = ByteBufUtil.hexDump(U.subBytes(ts, 13, 6));
//		//packData  Start at 23
//		e_qty = (int)U.subByte(ts, 23);
//		fcy = (int)U.subByte(ts, 24);
//		src = (int)U.subByte(ts, 25);
//		version = (int)U.subByte(ts, 33);
	}
	
	//应答包
	public byte[] responsePackage() {
		return new byte[] {};
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
    
}
