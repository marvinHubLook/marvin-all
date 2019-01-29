package cn.sock.base;

import java.io.*;
import java.net.Socket;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-18 09:45
 **/
public class BaseClientSocket {

    public static void main(String[] args) throws IOException {
        //1.创建客户端Socket，指定服务器地址和端口
        Socket so=new Socket("localhost", 8888);//端口号要和服务器端相同
        //2.获取输出流，向服务器端发送登录的信息
        OutputStream os=so.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//字符输出流
        BufferedWriter bw=new BufferedWriter(pw);//加上缓冲流
        bw.write("用户名：admin;密码：123");
        bw.flush();
        so.shutdownOutput();//关闭输出流
        //3.获取输入流，得到服务端的响应信息
        InputStream is=so.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("我是客户端，服务器说:"+info);
        }


        //4.关闭资源
        bw.close();
        pw.close();
        os.close();
        so.close();
    }
}
