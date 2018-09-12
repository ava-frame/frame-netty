package com.ava.frame.netty.test;

import com.ava.frame.netty.dto.MessageInfoHelper;
import com.ava.frame.netty.proto.Template;
import com.ava.frame.netty.test.client.NettyClient;
import io.netty.channel.Channel;
import org.junit.Test;

/**
 * Created by redredava on 2018/9/12.
 * email:zhyx2014@yeah.net
 */
public class NettyTest {
    @Test
    public void test(){
        Channel channel = new NettyClient().genChannel();
        try {
            channel.writeAndFlush(MessageInfoHelper.buildReq("chat", "")
                    .setReqChat(Template.ReqChat.newBuilder().setName("wwwwwwww"))).sync();

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
