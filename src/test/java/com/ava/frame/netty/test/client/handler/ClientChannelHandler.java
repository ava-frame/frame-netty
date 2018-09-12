package com.ava.frame.netty.test.client.handler;

import com.ava.frame.netty.proto.Template;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientChannelHandler extends SimpleChannelInboundHandler<Template.MessageInfo> {
    private static Logger log = LoggerFactory.getLogger(ClientChannelHandler.class);


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Template.MessageInfo msg) throws Exception {
        log.info("msg:\n" + msg);
    }
}
