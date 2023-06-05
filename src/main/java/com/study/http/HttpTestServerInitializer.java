package com.study.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/***
 * @Author wenLong
 * @Description
 * @Date 2023-06-04 22:36
 **/
public class HttpTestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加密- 解码
        //加入一个netty 提供的httpServerCodec codec ==>[coder-decoder]
        //HttpServerCodec 说明
        //1. HttpServerCodec 是netty提供的处理Http的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2 增加一个自定义处理器
        pipeline.addLast("MyHttpTestServerHandler", new HttpTestServerHandler());
    }
}
