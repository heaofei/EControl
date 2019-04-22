package com.mxkj.econtrol.net;


import android.text.TextUtils;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class APPClient {


//    public static String host = "127.0.0.1";
//    public static int port = 10088;


    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("_________client start_________________");

        System.out.println("ip:192.168.1.1");
        System.out.println("ip:");
        System.out.println(System.currentTimeMillis());
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new APPClientInitializer());

            // 连接服务端
            Channel ch = b.connect(Config.TCP_SERVER, Integer.parseInt(Config.TCP_SERVER_PORT)).sync().channel();

            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {

                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                System.out.println("____________________________________________________________________________________");
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * */
                line = "{'1','2','3'}";
                System.out.println("__________:" + line.getBytes().length);
                ByteBuf encoded = ch.alloc().buffer(6 + line.getBytes().length);
                //byte[] req = new byte[]{'1','2','3'};
//                encoded.writeBytes(new byte[2]); // 修改了新的tcp数据长度之后（能耗处修改）修改此处必须三个地方一起改，ByteToCommadDecoder类 ByteToCommadEncoder类
                encoded.writeBytes(new byte[2]);
                encoded.writeInt(line.getBytes().length);
                encoded.writeBytes(line.getBytes());
                ch.writeAndFlush(encoded);
                Thread.sleep(1000);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}