/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datacollection;
/**
 *
 * @author SirRat
 */
public class ServerPacket {
    private String request="dataFromLocalServer";
    private int cpuUse;
    private int memoryUse;
    private int networkUpload;
    private int networkDownload;
    
    protected void setCPU(int data){
        this.cpuUse=data;
    }
    protected void setRAM(int data){
        this.memoryUse=data;
    }
    protected void setNetworkUpload(int data){
        this.networkUpload=data;
    }
    protected void setNetworkDownload(int data){
        this.networkDownload=data;
    }
    protected String getData(){
        String data=request+"!"+cpuUse+"!"+memoryUse+"!"+networkUpload+"!"+networkDownload;
        return data;
    }
}
