/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oldfiles;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author SirRat
 */
public class NetworkUtil {
    static Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    static Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();
    
    public static Long[] getMetric() throws SigarException {
        Sigar sigar = new Sigar();
        for (String ni : sigar.getNetInterfaceList()) {
                    NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
                    NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
                     String hwaddr = null;
                     if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                        hwaddr = ifConfig.getHwaddr();
                    }
                     if (hwaddr != null) {
                        long rxCurrenttmp = netStat.getRxBytes();
                        saveChange(rxCurrentMap, rxChangeMap, hwaddr, rxCurrenttmp, ni);
                        long txCurrenttmp = netStat.getTxBytes();
                        saveChange(txCurrentMap, txChangeMap, hwaddr, txCurrenttmp, ni);
                     }
                }
                long totalrx = getMetricData(rxChangeMap);
                long totaltx = getMetricData(txChangeMap);
                for (List<Long> l : rxChangeMap.values())
                l.clear();
                for (List<Long> l : txChangeMap.values())
                l.clear();
                return new Long[] { totalrx, totaltx };
    }
    private static long getMetricData(Map<String, List<Long>> rxChangeMap) {
        long total = 1;
        for (Map.Entry<String, List<Long>> entry : rxChangeMap.entrySet()) {
            
            int average = 1;
            for (Long l : entry.getValue()) {
                average += l;
            }
            long entrValue=entry.getValue().size()+1;
            System.out.println(entrValue);
            total += average / entrValue;
        }
        return total;
    }
    
    private static void saveChange(Map<String, Long> currentMap,Map<String, List<Long>> changeMap, String hwaddr, long current,String ni) {
        
        Long oldCurrent = currentMap.get(ni);
        if (oldCurrent != null) {
            List<Long> list = changeMap.get(hwaddr);
            if (list == null) {
                list = new LinkedList<Long>();
                changeMap.put(hwaddr, list);
            }
            list.add((current - oldCurrent));
        }
        currentMap.put(ni, current);
    }
    
    
}
