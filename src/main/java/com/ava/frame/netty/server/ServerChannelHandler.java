package com.ava.frame.netty.server;

import com.ava.frame.core.SpringApplicationContext;
import com.ava.frame.netty.handler.BasicHandler;
import com.ava.frame.netty.proto.Template;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by redred on 2016/8/10.
 */
public class ServerChannelHandler extends SimpleChannelInboundHandler<Template.MessageInfo> {
    private static Logger LOG = LoggerFactory.getLogger(ServerChannelHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        LOG.info("exceptionCaught:" + cause);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ChannelManagement.removeChannel(ctx.channel());
        LOG.info("channelUnregistered");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        LOG.info("channelRegistered");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         /*心跳处理*/
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /*读超时*/
                LOG.info("READER_IDLE 读超时");
                ctx.channel().close();
//                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/
                LOG.info("WRITER_IDLE 写超时");
//                ChannelWrapper channelWrapper=serverRuntimeContext.getProtocolChannelRegistry().get(ctx.channel());
//                if(channelWrapper!=null){
//                    Replies replies=serverRuntimeContext.getMessageGatewayPipeline().messages(channelWrapper.getChannel(),channelWrapper.getEntity());
//                    serverRuntimeContext.getControlServerDeliver().deliver(replies,null);
//                }
//                ctx.channel().writeAndFlush(HEART_STR).sync();
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                LOG.info("ALL_IDLE 总超时");
//                ctx.disconnect();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
        LOG.info("userEventTriggered");
    }

    private Map<String, BasicHandler> handlerMap = new HashMap<String, BasicHandler>();

    private BasicHandler getHandler(String service) {
        BasicHandler handler = handlerMap.get(service);
        if (handler == null) {
            handler = SpringApplicationContext.getBean(service);
            handlerMap.put(service, handler);
        }
        return handler;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Template.MessageInfo request) throws Exception {
        try {
            LOG.info("request:\n" + request);
            BasicHandler handler = getHandler(request.getService());
            if (handler == null) {
                throw new Exception("== handler not define! request service: " + request.getCode() + " ==");
            }
            Template.MessageInfo.Builder resp = handler.handler(request, ctx.channel());
            if (resp != null) {
                ctx.channel().writeAndFlush(resp).sync();
            }
        } catch (Exception ex) {
            LOG.info("channelRead0:" + ex.getMessage(), ex);
        }
    }
}
