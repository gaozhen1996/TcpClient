package com.smartlab.tsu.test;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.smartlab.tsu.mina.MinaClientHandler;

public class TestMina {

	public static void main(String[] args) {
	   	//Create TCP/IP connection     
        NioSocketConnector connector = new NioSocketConnector();     
             
        //创建接受数据的过滤器     
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();     
             
        //设定这个过滤器将一行一行(/r/n)的读取数据     
        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));     
             
        //客户端的消息处理器：一个SamplMinaServerHander对象     
        connector.setHandler(new MinaClientHandler());     
             
        //set connect timeout     
        connector.setConnectTimeoutMillis(30*1000);     
         
        //连接到服务器：     
        ConnectFuture cf = connector.connect(new InetSocketAddress("192.168.31.208",9123));     
     
        
        
        //Wait for the connection attempt to be finished.     
        cf.awaitUninterruptibly();     
             
        cf.getSession().getCloseFuture().awaitUninterruptibly();     
        
        try {
			Thread.sleep(1000);
			cf.getSession().write("gaozhen");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
        
        connector.dispose();   
        
       
	}

}
