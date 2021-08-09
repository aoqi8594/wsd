/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qzsoft.tah.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author liyong
 */
@Component
public class FreshShieldServer {

   
    
    @Resource
    private WsdShieldHandler wsdShieldHandler;

    public void run(int port) throws Exception {
        System.out.println("port:"+port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
      

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(wsdShieldHandler);
            //绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind("0.0.0.0",port).sync();
            //等待服务监听端口关闭
            f.channel().closeFuture().await();
        } finally {
            //退出，释放线程资源
            bossGroup.shutdownGracefully();
        }
    }
}
