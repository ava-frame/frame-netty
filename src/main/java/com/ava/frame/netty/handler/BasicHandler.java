package com.ava.frame.netty.handler;

import com.ava.frame.netty.dto.MessageInfoHelper;
import com.ava.frame.netty.exception.CodeException;
import com.ava.frame.netty.proto.Template;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Created by redred on 2016/8/10.
 */
public abstract class BasicHandler<T> {
    protected static Logger log = LoggerFactory.getLogger(BasicHandler.class);
    abstract Template.MessageInfo.Builder service(Template.MessageInfo.Builder resp, T t, Channel channel);
    abstract T request(Template.MessageInfo message);

    public Template.MessageInfo.Builder handler(Template.MessageInfo messageInfo, Channel channel) {
        Template.MessageInfo.Builder resp = null;
        Long total = null;
        String exMessage = null;
        try {
//            checkUserValidate(messageInfo);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            T t = request(messageInfo);
            log.info("request:\n" + t);
            Template.MessageInfo.Builder respMessageInfo = MessageInfoHelper.buildResp(messageInfo);
            resp = service(respMessageInfo, t, channel);
            stopWatch.stop();
            total = stopWatch.getTotalTimeMillis();
            if (total > 500) {
                log.info(messageInfo.getService() + "===exec===" + total + "=ms=");
            }
            return resp;
        } catch (CodeException ex) {
            log.info("handler,ex=" + ex.getCode() + "," + ex.getMessage(), ex);
            exMessage = ex.toString();
            resp = MessageInfoHelper.buildResp(messageInfo).setCode(ex.getCode()).setMsg(ex.getMessage());
            return resp;

        } catch (Exception ex) {
            log.info("handler,ex=" + ex.getMessage(), ex);
            exMessage = ex.toString();
            resp = MessageInfoHelper.buildResp(messageInfo).setCode(1).setMsg("SERVER_ERROR_MESSAGE");
            return resp;
        } finally {
//            saveTcpInfo(messageInfo, resp, channel, total, exMessage);
        }
    }



}
