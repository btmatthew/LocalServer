/*
 SIGAR is licensed under the Apache License, Version 2.0
 */

package datacollection;

/**
 *
 * @author SirRat
 */

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.EncodeException;
import javax.websocket.server.ServerEndpoint;
import org.hyperic.sigar.*;
import java.io.IOException;




@ServerEndpoint("/data") 
public class DataCollection {
    private Session x;
    private Logging log = new Logging();
    
    
    @OnOpen
    public void onOpen(Session session){
        log.writeLog(" "+session.getId() + " has opened a connection");
    }
    @OnMessage
    public void onMessage(String message, Session session){
    }
    final private Thread thread = new Thread(new Message());
    @OnMessage
    public void onMessage(byte[] message, Session session) throws SigarException, InterruptedException{
        x=session;
        String request=new String(message);
        switch(request){
            case "data":
                thread.start();
                break;
            case "close":
            try{
                    thread.interrupt();
                    session.close();
                }catch(IOException e){
                    log.writeLog(" "+e);
                }   
                break;
        }
    }
    @OnClose
    public void onClose(Session session){
        log.writeLog(" "+"Session " +session.getId()+" has ended");
    }
    
    class Message implements Runnable{
        @Override
        public void run() {
            
            try {
                byte[] data=null;
                RawData localData=new RawData();
            for (Session sess : x.getOpenSessions()) {
                while(x.isOpen()){
                    data=localData.rawData().getBytes();
                        if (x.isOpen()){
                         x.getBasicRemote().sendObject(data);
                        }
                        Thread.sleep(1000);
                }
                        
            }
            
        } catch (IOException | SigarException | InterruptedException | EncodeException e){//find correct exception
           log.writeLog(" "+e);
        }
    }
    }
    
    
}
