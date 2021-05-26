package com.iw2f.netty.decoder;

import java.util.List;


import io.netty.buffer.ByteBuf;
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
			while (in.isReadable()) {
				in.readByte();
				int i = in.readableBytes();
				byte[] ts = new byte[i];
				in.readBytes(ts);
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
			}
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