package com.mxkj.econtrol.net;


import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 *
 *
 */

public class APPClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new ByteToCommadDecoder());
        pipeline.addLast("encoder", new ByteToCommadEncoder());
        pipeline.addLast("handler", new APPClientHandler());
        pipeline.addLast( new IdleStateHandler(0, 3*60, 0, TimeUnit.SECONDS));
        pipeline.addLast(new ClientIdleStateHandler());  // 配置了心跳包180秒发一次
    }
}