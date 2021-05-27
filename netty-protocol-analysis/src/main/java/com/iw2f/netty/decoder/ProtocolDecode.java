package com.iw2f.netty.decoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.iw2f.netty.utils.U;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 协议解码
 * @author wangjc
 *
 */
@Slf4j
public class ProtocolDecode extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			Object o = decode(ctx, in);
			if (o != null) {
				out.add(o);
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}

	private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
		try {
			
//			数据通讯举例：
//			0  1  2  3    5    7    9    11		
//			01 03 08 0198 0011 0000 00FA 3192
//			01	为地址码
//			03	为功能码
//			08	为8个字节数据
//			0198是pH，换算成pH是4.08
//			0011是余氯，换算成余氯是0.17
//			0000是ORP，（设备未安装）
//			00FA是温度，25.0℃
//
//			ph和余氯要除以100，温度要除以10
			
			
//			while (in.isReadable()) {
				//in.readByte();
				int i = in.readableBytes();
				byte[] ts = new byte[i];
				in.readBytes(ts);
				log.info("i {} ts.length {}",i,ts.length);
				if(ts.length == 13) {
					//地址码
					int i0 = U.byteToInt(U.subByte(ts, 0));					
					//功能码
					int i1 = U.byteToInt(U.subByte(ts, 1));
					//8个字节数据
					int i2 = U.byteToInt(U.subByte(ts, 2));
					//pH，换算成pH是4.08
					int i3 = U.byteArrayToInt(U.subBytes(ts, 3,2));
					//余氯，换算成余氯是0.17
					int i5 = U.byteArrayToInt(U.subBytes(ts, 5,2));
					//ORP，（设备未安装）
					int i7 = U.byteArrayToInt(U.subBytes(ts, 7,2));
					//是温度，25.0℃
					int i9 = U.byteArrayToInt(U.subBytes(ts, 9,2));
					
			        BigDecimal ii3 = BigDecimal.valueOf(i3).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
			        BigDecimal ii5 = BigDecimal.valueOf(i5).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
			        BigDecimal ii9 = BigDecimal.valueOf(i9).divide(BigDecimal.valueOf(10),2, RoundingMode.HALF_UP);
			        
					log.info("地址码 {}  功能码 {}  pH{} 余氯 {}  温度 {}",i0,i1,ii3,ii5,ii9);
				}
				log.info(ByteBufUtil.hexDump(ts));
				
				//https://www.cnblogs.com/leesf456/p/6898069.html
				//ByteBufUtil.hexDump(buffer);
				// 判断包的类型
//				ts = escape(ts);
//				check(ts);
//				byte cmd = U.subByte(ts, 19);
//				if (Cmd.UploadAnt == cmd) {
//					AntRequestCmd request = new AntRequestCmd(ts);
//					return request;
//				} else if (Cmd.UploadBle == cmd) {
//					log.info("Cmd.UploadBle");
//					return null;
//				} else if (Cmd.Heartbeat == cmd) {
//					HeartbeatCmd hb = new HeartbeatCmd(ts);
//					return hb;
//				} else if (Cmd.Ack == cmd) {
//					log.info("Cmd.Ack");
//					return null;
//				} else {
//					throw new RuntimeException("Unknown cmd !");
//				}
//			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		Channel channel = ctx.channel();
		if (channel.isActive())
			ctx.close();
	}

}