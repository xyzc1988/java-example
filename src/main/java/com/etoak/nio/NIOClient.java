package com.etoak.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO客户端
 * Created by zhangcheng on 2016/5/23.
 */
public class NIOClient {
    //通道管理器
    private Selector selector;
    private SocketChannel socketChannel;
    /**
     * 获得一个Socket通道，并对该通道做一些初始化的工作
     * @param ip 连接的服务器的ip
     * @param port  连接的服务器的端口号
     * @throws IOException
     */
    public void initClient(String ip,int port) throws IOException {
        // 获得一个Socket通道
        socketChannel = SocketChannel.open();
        // 设置通道为非阻塞
        socketChannel.configureBlocking(false);
        // 获得一个通道管理器
        this.selector = Selector.open();

        // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
        //用channel.finishConnect();才能完成连接
        socketChannel.connect(new InetSocketAddress(ip,port));
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
        //socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        // 轮询访问selector
        while (true) {
            selector.select();
            // 获得selector中选中的项的迭代器
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();
                // 连接事件发生
                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key
                            .channel();
                    // 如果正在连接，则完成连接
                    if(channel.isConnectionPending()){
                        channel.finishConnect();

                    }
                    // 设置成非阻塞
                    channel.configureBlocking(false);

                    //在这里可以给服务端发送信息哦
                    channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
                    //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                  //  channel.register(this.selector, SelectionKey.OP_READ);

                    // 获得了可读的事件
                } else if (key.isReadable()) {
                    read(key);
                }

            }

        }
    }
    /**
     * 处理读取服务端发来的信息 的事件
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException{
        //和服务端的read方法一样
    }

    /**
     * 发送字符串到服务器
     * @param message
     * @throws IOException
     */
    public void sendMsg(String message) throws IOException{
        ByteBuffer writeBuffer=ByteBuffer.wrap(message.getBytes("UTF-16"));
        socketChannel.register(selector,SelectionKey.OP_WRITE);
        socketChannel.write(writeBuffer);
    }



    /**
     * 启动客户端测试
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient();
        client.initClient("localhost",8000);
        client.sendMsg("你好!Nio!醉里挑灯看剑,梦回吹角连营");
       //client.listen();
    }

}