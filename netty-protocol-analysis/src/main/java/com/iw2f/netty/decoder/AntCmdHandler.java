package com.iw2f.netty.decoder;

import org.springframework.stereotype.Component;

import com.iw2f.netty.bo.AntRequestCmd;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据包业务处理
 *
 */
@Slf4j
@Component
@Sharable
public class AntCmdHandler extends SimpleChannelInboundHandler<AntRequestCmd> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AntRequestCmd msg) throws Exception {
		log.info("data : {} result : {}", ByteBufUtil.hexDump(msg.getData()), msg.toString());
		ctx.writeAndFlush(Unpooled.copiedBuffer("".getBytes()));
		ctx.flush();
		// ctx.close();
	}

}