package com.smartlab.tsu.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;     
    
/**   
 * mina客户端   
 * @author aniyo    
 * blog:http://aniyo.iteye.com 
 */    
public class MinaClient {     
    
	private IoSession session;
	
	private NioSocketConnector connector;
	
	public IoSession getSession(){
		return session;
	}
	
	public NioSocketConnector getConnector(){
		return connector;
	}
	
	/**
	 * 连接服务端
	 * @param host
	 * @param port
	 */
    public void config(String host,int port){
    	//Create TCP/IP connection     
    	connector = new NioSocketConnector();     
             
        //客户端的消息处理器：一个SamplMinaServerHander对象     
        connector.setHandler(new MinaClientHandler());     
             
        //set connect timeout     
        connector.setConnectTimeoutMillis(30*1000);     
        
        
        //连接到服务器：     
        ConnectFuture cf = connector.connect(new InetSocketAddress(host,port));     
            
        //Wait for the connection attempt to be finished.     
        cf.awaitUninterruptibly();     
         
        session=cf.getSession();
        
        cf.getSession().getCloseFuture().awaitUninterruptibly();     
            
        connector.dispose();     
    }
    
    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(byte[] message){
    	session.write(IoBuffer.wrap(message));
    }
    
    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message){
    	session.write(message);
    }

    
}  