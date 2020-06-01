package comparator;

import pcb.PCB;

import java.util.Comparator;

/**
 * @author sky
 */
public class SRTComparator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getTime2Finish()<=o2.getTime2Finish()){
            return -1;
        }
        return 1;
    }
}
