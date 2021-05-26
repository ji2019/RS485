package com.iw2f.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iw2f.netty.decoder.AntCmdHandler;
import com.iw2f.netty.decoder.HeartbeatCmdlHandler;
import com.iw2f.netty.decoder.ProtocolDecode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Server {
	
	@Autowired
	private AntCmdHandler antCmdHandler;
	
	@Autowired
	private HeartbeatCmdlHandler heartbeatCmdlHandler;

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	
	private final EventLoopGroup workerGroup = new NioEventLoopGroup(16);
	
	private Channel channel;

	/**
	 * 启动服务
	 * 
	 * @param hostname
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public ChannelFuture start(String hostname, int port) throws Exception {
		ChannelFuture f = null;
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)//
              .option(ChannelOption.TCP_NODELAY, true)//
              .option(ChannelOption.SO_KEEPALIVE, true)//
              .option(ChannelOption.SO_REUSEADDR, true)//
              .option(ChannelOption.SO_RCVBUF, 32 * 1024)//
              .option(ChannelOption.SO_SNDBUF, 32 * 1024)//
              .option(EpollChannelOption.SO_REUSEPORT, true)//
              .childOption(ChannelOption.SO_KEEPALIVE, true)//
              .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			serverBootstrap//
					.group(bossGroup, workerGroup)//
					.channel(NioServerSocketChannel.class)//
					.childHandler(new ChannelInitializer<NioSocketChannel>() {
						protected void initChannel(NioSocketChannel ch) {
							ch.pipeline().addLast(
									new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7f })));
							ch.pipeline().addLast(new ProtocolDecode());
							ch.pipeline().addLast(antCmdHandler);
							ch.pipeline().addLast(heartbeatCmdlHandler);
						}
					});
			int inetPort = 9125;
			f = serverBootstrap.bind(inetPort).sync();
			channel = f.channel();
			// f.channel().closeFuture().sync();
			log.info("======Server启动成功!!! "+inetPort+" =========");
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		} finally {
			if (f != null && f.isSuccess()) {
				log.info("Netty server listening " + hostname + " on port " + port + " and ready for connections...");
			} else {
				log.error("Netty server start up Error!");
			}
		}
		return f;
	}

	/**
	 * 停止服务
	 */
	public void destroy() {
		log.info("Shutdown Netty Server...");
		if (channel != null) {
			channel.close();
		}
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		log.info("Shutdown Netty Server Success!");
	}
}
