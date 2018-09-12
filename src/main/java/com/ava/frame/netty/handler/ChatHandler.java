package com.ava.frame.netty.handler;

import com.ava.frame.netty.dto.MessageInfoHelper;
import com.ava.frame.netty.proto.Template;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * Created by redred on 2016/8/10.
 */
@Service("chat")
public class ChatHandler extends BasicHandler<Template.ReqChat> {
    @Override
    public Template.ReqChat request(Template.MessageInfo message) {
        return message.getReqChat();
    }

    @Override
    public Template.MessageInfo.Builder service(Template.MessageInfo.Builder resp, Template.ReqChat reqChat, Channel channel) {
       log.info("-------------------------"+reqChat.getName());
        resp.setRespChat(Template.RespChat.newBuilder().setSign(reqChat.getName()+".resp"));
        return MessageInfoHelper.success(resp);
    }
}
