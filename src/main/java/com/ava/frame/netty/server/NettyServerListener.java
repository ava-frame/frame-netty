package com.ava.frame.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by redredava on 2018/9/12.
 * email:zhyx2014@yeah.net
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyServerListener  {
    private static Logger log = LoggerFactory.getLogger(NettyServerListener.class);
    private int psize;
    private int csize;
    private int port;
    private int ctimeout;
    private Boolean nodelay;
    private EventLoopGroup parent = null;
    private EventLoopGroup child = null;
    private ChannelFuture channelFuture = null;

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        log.info("关闭服务器....");
        //优雅退出
        try {
            child.shutdownGracefully();
            parent.shutdownGracefully();
            channelFuture.channel().close().sync();
            log.info("shutdown success  " + port);
        } catch (InterruptedException e) {
            log.error("shutdown error", e);
        }
    }
    public ChannelFuture start() {
        try{
            parent = new NioEventLoopGroup(psize);
            child = new NioEventLoopGroup(csize);
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent, child);
            bootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, ctimeout);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, nodelay);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelHandlerRegister());
            channelFuture = bootstrap.bind(port).sync();
            log.info("netty start success on " + port);
        }catch (Exception e){
            log.error(" netty start fail");
        }
        return channelFuture;
    }



    public int getPsize() {
        return psize;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public int getCsize() {
        return csize;
    }

    public void setCsize(int csize) {
        this.csize = csize;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCtimeout() {
        return ctimeout;
    }

    public void setCtimeout(int ctimeout) {
        this.ctimeout = ctimeout;
    }

    public Boolean getNodelay() {
        return nodelay;
    }

    public void setNodelay(Boolean nodelay) {
        this.nodelay = nodelay;
    }

    public EventLoopGroup getParent() {
        return parent;
    }

    public void setParent(EventLoopGroup parent) {
        this.parent = parent;
    }

    public EventLoopGroup getChild() {
        return child;
    }

    public void setChild(EventLoopGroup child) {
        this.child = child;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }
}
