package cn.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/8/1 9:45
 **/
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }
    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();                  //#1
            b.group(group)                                              //#2
                    .channel(NioServerSocketChannel.class)              //#2
                    .localAddress(new InetSocketAddress(port))          //#2
                    .childHandler(new ChannelInitializer<SocketChannel>() { //#3
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());                  //#4
                        }
                    })
                    ;

            ChannelFuture f = b.bind(port).sync();              //#5
            System.out.println(EchoServer.class.getSimpleName() + " started and listener on " + f.channel().localAddress());
            f.channel().closeFuture().sync();               //#6
        } finally {
            group.shutdownGracefully().sync();              //#7
        }

    }


    public static void main(String[] args) throws Exception {
        new EchoServer(6379).start();
    }

}
