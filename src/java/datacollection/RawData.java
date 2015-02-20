/*
SIGAR is licensed under the Apache License, Version 2.0
 */

package datacollection;


import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author SirRat
 */
public class RawData {
    public String rawData() throws SigarException, InterruptedException{
            Sigar sigar = new Sigar();
            NetworkStat network = new NetworkStat();
            Long[] n = network.getMetric();
            ServerPacket allData= new ServerPacket();
            allData.setCPU((int)((1.0-sigar.getCpuPerc().getIdle())*100));
            allData.setRAM((int)(sigar.getMem().getUsed()/1024/1024));
            allData.setNetworkUpload(n[1].intValue());
            allData.setNetworkDownload(n[0].intValue());
            return allData.getData();
    }
    
}
