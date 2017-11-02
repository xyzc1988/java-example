package com.etoak.nio;


import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.Encoder;
import sun.nio.cs.SingleByte;
import sun.nio.cs.ext.DoubleByte;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.Iterator;

/**
 * NIO服务端
 * Created by zhangcheng on 2016/5/23.
 */
public class NIOServer {
    //通道管理器
    private Selector selector;

    /**
     * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
     * @param port  绑定的端口号
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获得一个通道管理器
        this.selector = Selector.open();
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
        //当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        System.out.println("服务端启动成功！");
        // 轮询访问selector
        while (true) {
            //当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            selector.select();
            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();
                process(key);

            }

        }
    }
    /**
     * 处理读取客户端发来的信息 的事件
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException{
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到信息："+msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);// 将消息回送给客户端
    }

    /*
 * 根据不同的事件做处理
 * */
    protected void process(SelectionKey key) throws IOException{
        Charset charset = Charset.forName("utf-8");
        CharsetEncoder encoder = charset.newEncoder();
        // 接收请求
        if (key.isAcceptable()) {
            // 获得和客户端连接的通道
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        }
        // 读信息
        else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();

            // 创建读取的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务端收到信息："+msg);
            ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(outBuffer);// 将消息回送给客户端
            buffer.clear();
            // 读取信息获得读取的字节数
            long bytesRead=socketChannel.read(buffer);
            if(bytesRead==-1){
                // 没有读取到内容的情况
                socketChannel.close();
            }else {
                // 将缓冲区准备为数据传出状态
                buffer.flip();

                // 将字节转化为为UTF-16的字符串
                String receivedString = Charset.forName("UTF-16").newDecoder().decode(buffer).toString();

                // 控制台打印出来
                System.out.println("接收到来自" + socketChannel.socket().getRemoteSocketAddress() + "的信息:" + receivedString);

                // 准备发送的文本
                String sendString = "你好,客户端. @" + new Date().toString() + "，已经收到你的信息" + receivedString;
                buffer = ByteBuffer.wrap(sendString.getBytes("UTF-16"));
               /* socketChannel.write(buffer);

                // 设置为下一次读取或是写入做准备
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);*/
            }
        }
        // 写事件
        else if (key.isWritable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            String name = (String) key.attachment();

            ByteBuffer block = encoder.encode(CharBuffer.wrap("Hello " + name));
            if(block != null)
            {
                channel.write(block);
            }
            else
            {
                channel.close();
            }

        }
    }

    /**
     * 启动服务端测试
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }

}