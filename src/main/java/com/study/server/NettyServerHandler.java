package com.study.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/***
 * @Author wenLong
 * @Description 入栈的Handler适配器
 * 1.自定义一个Handler 需要集成Netty 规定好的某个Handler适配器
 * 2.我们自定义一个Handler 才能称为一个handler
 *
 *
 * @Date 2023-06-03 16:42
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据实际（读取客户端发送到消息）
     *
     * @param ctx 上下文对象 含有管道pipeline 通道   pipeline 指的的是管道中handler 对数据进行读写？？  通道：业务逻辑处理
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //打印 ctx
        System.out.println(ctx);
        //将msg转成一个byteBuf  Netty 提供的ByteBuf 性能会更高
        ByteBuf buf = (ByteBuf) msg;
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println(LocalDateTime.now().getSecond());
//                    Thread.sleep(10 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 测试测试测试....", CharsetUtil.UTF_8));
//                    System.out.println("服务端已经向客户端发送信息....");
//                    System.out.println(LocalDateTime.now().getSecond());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        System.out.println("当前秒数:"+LocalDateTime.now().getSecond());
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前秒数:"+LocalDateTime.now().getSecond());
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 测试测试....",CharsetUtil.UTF_8));

            }
        },5, TimeUnit.SECONDS);
        System.out.println("开始处理服务端发送数据!");
    }

    /**
     * 数据读取完毕 将数据数返回给客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存 并刷新
        //wirteAndFlush 是wirte+flush
        //一般讲 我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8));
    }

    /**
     * 处理通道异常信息
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        //关闭通道
        ctx.close();


    }
}
