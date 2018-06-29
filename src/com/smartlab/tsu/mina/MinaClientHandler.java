package com.smartlab.tsu.mina;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.smartlab.tsu.factory.ConfigFactory;
import com.smartlab.tsu.util.ConvertFactory;
import com.smartlab.tsu.view.IndexView;


/**
 * 客户端业务处理逻辑
 * 
 * @author aniyo blog: http://aniyo.iteye.com
 */
public class MinaClientHandler extends IoHandlerAdapter {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
    // 当客户端连接进入时  
    @Override  
    public void sessionOpened(IoSession session) throws Exception {  
        System.out.println("incomming 客户端: " + session.getRemoteAddress());  
    }  
  
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause)  
            throws Exception {  
    	cause.printStackTrace();
        System.out.println("客户端发送信息异常....");  
    }  
  
    // 当客户端发送消息到达时  
    @Override  
    public void messageReceived(IoSession session, Object message)  
            throws Exception {  
		IoBuffer bbuf = (IoBuffer) message;
		byte[] byten = new byte[bbuf.limit()];
		bbuf.get(byten, bbuf.position(), bbuf.limit());

		String result = null;
		if(ConfigFactory.parseStyle.equals("String")) {
			result = new String(byten);
		}else {
			result = ConvertFactory.bytesToHexString(byten);
		}
		IndexView.recordArea.appendText( sdf.format(new Date()) +" 接收:\t"+result+"\n");
        
    }  
  
    @Override  
    public void sessionClosed(IoSession session) throws Exception {  
        System.out.println("客户端与服务端断开连接.....");  
    }  
  
    @Override  
    public void sessionCreated(IoSession session) throws Exception {  
        // TODO Auto-generated method stub  
        System.out  
                .println("one Client Connection" + session.getRemoteAddress());  
    }  
  
}  