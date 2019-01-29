package cn.sock.base;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-18 09:40
 **/
public class BaseServerSocket extends Thread{
        // 和本线程相关的Socket
        Socket so = null;
        public BaseServerSocket(Socket socket) {// 初始化与本线程相关的Socket
            so = socket;
        }

        // 线程执行的操作，响应客户端的请求
        @Override
        public void run() {// 重写父类的run方法
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            BufferedWriter bw = null;
            try {
                // 3.获取一个输入流，并读取客户端信息
                is = so.getInputStream();// 字节输入流
                isr = new InputStreamReader(is);// 将字节输入流包装成字符输入流
                br = new BufferedReader(isr);// 加上缓冲流，提高效率
                String info = null;
                while ((info = br.readLine()) != null) {// 循环读取客户端信息
                    System.out.println("我是服务器，客户端说：" + info);

                }
                so.shutdownInput();// 关闭输入流
                // 4.获取一个输出流，向客户端输出信息,响应客户端的请求
                os = so.getOutputStream();// 字节输出流
                pw = new PrintWriter(os);// 字符输出流
                bw = new BufferedWriter(pw);// 缓冲输出流
                bw.write("欢迎您！");
                bw.newLine();
                bw.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // 5.关闭资源
                try {
                    if (os != null)
                        os.close();
                    if (pw != null)
                        pw.close();
                    if (bw != null)
                        bw.close();
                    if (br != null)
                        br.close();
                    if (isr != null)
                        isr.close();
                    if (is != null)
                        is.close();
                    if (!so.isClosed())
                        so.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    public static void main(String[] args) {
        try {
            //1.创建一个服务器端的Socket,即ServerSocket，指定绑定的端口
            ServerSocket ss= new ServerSocket(8888);
            System.out.println("服务器即将启动，等待客户端的连接...");
            Socket so=null;
            //记录客户端的数量
            int count=0;
            //循环侦听等待客户端的连接
            while(true){
                //2.调用accept方法开始监听，等待客户端的连接
                so=ss.accept();//accept方法返回Socket实例
                //创建一个新的线程
                BaseServerSocket st=new BaseServerSocket(so);
                //启动线程，执行与客户端的交互
                st.start();//注意是start不是run
                count++;
                System.out.println("此时客户端数量为："+count);
                InetAddress add=so.getInetAddress();
                System.out.println("当前客户端的ip地址为"+add.getHostAddress());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
