package comparator;

import pcb.PCB;

import java.util.Comparator;

/**
 * @author sky
 */
public class SPNComparator implements Comparator<PCB> {
    @Override
    public int compare(PCB o1, PCB o2) {
        if(o1.getTime4Finish()<=o2.getTime4Finish()){
            return -1;
        }
        return 1;
    }

}
