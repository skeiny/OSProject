package comparator;

import pcb.PCB;
import java.util.Comparator;

/**
 * @author sky
 */
public class PRIComparator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getPriority()>=o2.getPriority()){
            return -1;
        }
        return 1;
    }
}

