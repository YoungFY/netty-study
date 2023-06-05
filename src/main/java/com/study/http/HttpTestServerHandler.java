package com.study.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/***
 * @Author wenLong
 * @Description
 * @Date 2023-06-04 21:35
 **/
public class HttpTestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端发送的数据
     *
     * @param channelHandlerContext
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            //获取http请求
            HttpRequest httpRequest = (HttpRequest) httpObject;
            //获取URI 过滤指定资源
            URI uri = new URI(httpRequest.uri());
            ByteBuf copiedBuffer = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);
            //回复消息给浏览器
            HttpResponse httpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,copiedBuffer);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,copiedBuffer.readableBytes());
            channelHandlerContext.writeAndFlush(httpResponse);
        }

    }
}
