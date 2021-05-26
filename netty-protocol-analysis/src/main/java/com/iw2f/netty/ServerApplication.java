package com.iw2f.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.iw2f.netty.server.Server;

import io.netty.channel.ChannelFuture;
@SpringBootApplication
public class ServerApplication implements CommandLineRunner{

//    @Value("${netty.port}")
    private int port;

//    @Value("${netty.url}")
    private String url;
    
    @Autowired
    private Server server;
    
	public static void main(String[] args) {
		new SpringApplicationBuilder()//
				.sources(ServerApplication.class)//
				.web(WebApplicationType.NONE) //
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
        ChannelFuture future = server.start(url,port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
	}

}