package com.ava.frame.netty.dto;

import com.ava.frame.netty.proto.Template;

import static com.ava.frame.netty.constant.CodeDefinition.*;
import java.util.concurrent.atomic.AtomicInteger;
public class MessageInfoHelper {

    public static AtomicInteger globalSn = new AtomicInteger(1);

    public static Template.MessageInfo.Builder fail(Template.MessageInfo messageInfo, String msg) {
        return buildResp(messageInfo).setMsg(msg).setCode(FAIL_CODE).setSn(messageInfo.getSn());
    }

    public static Template.MessageInfo.Builder buildResp(Template.MessageInfo messageInfo) {
        return Template.MessageInfo.newBuilder().setService(messageInfo.getService()).setSn(messageInfo.getSn());
    }

    public static Template.MessageInfo.Builder buildReq(String service, String token) {
        return Template.MessageInfo.newBuilder().setService(service).setSn(globalSn.getAndIncrement()).setToken(token);
    }

    public static Template.MessageInfo.Builder success(Template.MessageInfo.Builder resp) {
        return resp.setCode(SUCCESS_CODE).setMsg(SUCCESS_DEFAULT_MESSAGE);
    }

    public static Template.MessageInfo.Builder empty(Template.MessageInfo.Builder resp) {
        return resp.setCode(NO_DATA_CODE).setMsg("NO_DATA_MESSAGE");
    }

    public static Template.MessageInfo.Builder fail(Template.MessageInfo.Builder resp) {
        return resp.setCode(FAIL_CODE).setMsg(FAIL_DEFAULT_MESSAGE);
    }

    public static Template.MessageInfo.Builder fail(Template.MessageInfo.Builder resp, String message) {
        return resp.setCode(FAIL_CODE).setMsg(message);
    }

    public static Template.MessageInfo.Builder fail(Template.MessageInfo.Builder resp, Integer code, String message) {
        return resp.setCode(code).setMsg(message);
    }
}
