package com.mxkj.econtrol.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;



public class ByteToCommadEncoder extends MessageToByteEncoder<String>{
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] b = msg.getBytes("UTF-8");
		out.writeBytes(new byte[2]);
		out.writeInt(b.length);
		out.writeBytes(b);
	}
	

	

}
