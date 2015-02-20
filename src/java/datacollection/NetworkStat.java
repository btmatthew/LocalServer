/*
 * Based on audiolizer
 * https://code.google.com/p/audiolizer/source/browse/src/org/audiolizer/SigarNetworkPerformanceMonitor.java?r=3
 * 
 */

package datacollection;

import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
/**
 *
 * @author SirRat
 */
public class NetworkStat {
    
    public Long[] getMetric() throws SigarException{
        return currentTraffic();
    }
    
    private Long[] currentTraffic()throws SigarException{
        Sigar sigar = new Sigar();
        Long[] networkUtil= new Long[2];
        networkUtil[0]=0L;
        networkUtil[1]=0L;
        for (String ni : sigar.getNetInterfaceList()) {
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
            networkUtil[0]+=netStat.getRxBytes();
            
            networkUtil[1]+=netStat.getTxBytes();
        }
        networkUtil[0]=networkUtil[0]/1024;
        networkUtil[1]=networkUtil[1]/1024;
        return networkUtil;
    }
}
