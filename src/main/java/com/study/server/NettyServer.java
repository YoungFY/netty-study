package com.study.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/***
 * @Author wenLong
 * @Description
 * @Date 2023-05-31 23:31
 **/
@SuppressWarnings("all")
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup和 workGroup
        //bossGroup 只是处理链接请求 真正和客户端业务处理的 会交给workGroup完成
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 创建服务器端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动链接数
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //对 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //将handler 加入到管道
                            //在这个位置处理时间较长的情况下
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workGroup的EventLoop对应的管道

            System.out.println("服务器 已经准备好");
            //启动服务(并邦定端口 生成一个ChannelFuture对象
            ChannelFuture chf = bootstrap.bind(9988).sync();
            //对关闭通道进行监听
            chf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }

    }


}
