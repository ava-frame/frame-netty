package com.ava.frame.netty.test.client;

import com.ava.frame.netty.proto.Template;
import com.ava.frame.netty.test.client.handler.ClientChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by redred on 2016/8/11.
 */
public class NettyClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    //        static final String HOST = System.getProperty("host", "172.16.4.196");
//        static final String HOST = System.getProperty("host", "121.199.74.219");
    static final int PORT = Integer.parseInt(System.getProperty("port", "22223"));
    private static Logger log = LoggerFactory.getLogger(NettyClient.class);
    public Channel genChannel() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(
                                            Template.MessageInfo.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new ClientChannelHandler());
                        }
                    });
            //发起异步连接操作
            Channel channel = b.connect(HOST, PORT).sync().channel();
            return channel;
        } catch (Exception e) {
            log.info("" + e.getMessage(), e);
        }
        return null;
    }
}
