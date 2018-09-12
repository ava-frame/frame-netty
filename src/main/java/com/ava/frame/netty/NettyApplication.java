package com.ava.frame.netty;

import com.ava.frame.core.SpringApplicationContext;
import com.ava.frame.netty.server.NettyServerListener;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * Created by redredava on 2018/9/12.
 * email:zhyx2014@yeah.net
 */
@Configuration
@SpringBootApplication
@ComponentScan(basePackageClasses = {SpringApplicationContext.class, NettyServerListener.class}, basePackages = {"com.ava.frame.netty.handler"})
public class NettyApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

@Autowired
private  NettyServerListener nettyServerListener;
    @Override
    public void run(String... strings) throws Exception {

        ChannelFuture future =   nettyServerListener.start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                nettyServerListener.close();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
