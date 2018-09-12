package com.ava.frame.netty.server;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangqian on 2016/6/16.
 */
public class ChannelManagement {
    private final static Logger log = LoggerFactory.getLogger(ChannelManagement.class);
    public static Map<Integer, Channel> channelMap = new HashMap<Integer, Channel>();
    public static Map<Integer, Integer> channelUidMap = new HashMap<Integer, Integer>();


    private final static String key_channel_prefix = "game_music:channel:";

    private static String key(Integer uid) {
        return key_channel_prefix + uid;
    }

    public static void setChannel(Integer uid, Channel channel) {
        channelMap.put(uid, channel);
        channelUidMap.put(channel.hashCode(), uid);
    }

    public static void removeChannel(Integer uid) {
        Channel channel = channelMap.get(uid);
        channelMap.remove(uid);
        channelUidMap.remove(channel.hashCode());
    }

    public static void removeChannel(Channel channel) {
        Integer uid = channelUidMap.get(channel.hashCode());
        channelUidMap.remove(channel.hashCode());
        channelMap.remove(uid);
    }

    public static Channel getChannel(Integer uid) {
        return channelMap.get(uid);
    }
}
