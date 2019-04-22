package com.mxkj.econtrol.net;

import com.mxkj.econtrol.utils.LogUtil;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


public class ByteToCommadDecoder extends ByteToMessageDecoder  {



	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		int readableBytesLen =  buf.readableBytes();
		if(readableBytesLen<=6){
			return;
		}

		buf.markReaderIndex();
 		buf.readBytes(new byte[2]);
 		int msgLen = buf.readInt();


        if (msgLen < 0) {
 			// 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
 			LogUtil.e("msgLen[{}] <0,close ctx."+msgLen);
            ctx.close();
        }

 		if((msgLen+6)>readableBytesLen){
            LogUtil.e("readableBytesLen[{"+readableBytesLen+"}]<msgLen[{"+msgLen+"}]+6 is error,return.");
 			buf.resetReaderIndex();
 			return;
 		}

 		byte[] msg = new byte[msgLen];
 		buf.readBytes(msg);
 		out.add(new String(msg,"UTF-8"));
	}
}
