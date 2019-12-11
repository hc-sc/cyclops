package ca.gc.hc.nhpd.model.test;

import ca.gc.hc.nhpd.model.IngredientCategory;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.model.Units;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class SubPopulationTest {

    private static TreeSet<SubPopulation> subPopulationList;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // subPopulationList = new ArrayList<SubPopulation>(SubPopulation.);

        Comparator subPopulationComparator = new SubPopulation.SubPopulationComparator();
        
        subPopulationList = new TreeSet<SubPopulation>(subPopulationComparator);
        
        buildSubpopulationList();
        
        
        
        // Collections.sort(subPopulationList,new SubPopulation.SubPopulationComparator());
        
        displaySubPopulationList();
        
    }

    private static void buildSubpopulationList() {
        
        /*
        subPopulationList.add(buildSubPopulation("a14", 3, 3));
        subPopulationList.add(buildSubPopulation("a2", 0, 1));
        subPopulationList.add(buildSubPopulation("a4", 1, 1));
        subPopulationList.add(buildSubPopulation("a7", 2, 0));
        subPopulationList.add(buildSubPopulation("a5", 1, 2));
        subPopulationList.add(buildSubPopulation("a6", 1, 3));
        subPopulationList.add(buildSubPopulation("a3", 1, 0));
        subPopulationList.add(buildSubPopulation("a8", 2, 1));
        subPopulationList.add(buildSubPopulation("a9", 2, 2));
        subPopulationList.add(buildSubPopulation("a10", 2, 3));
        subPopulationList.add(buildSubPopulation("a11", 3, 0));
        subPopulationList.add(buildSubPopulation("a12", 3, 1));
        subPopulationList.add(buildSubPopulation("a1", 0, 0));
        subPopulationList.add(buildSubPopulation("a13", 3, 2));
        */
        
        subPopulationList.add(buildSubPopulation("CH13", 1, 3));
        subPopulationList.add(buildSubPopulation("ADU", 19, 0));
        subPopulationList.add(buildSubPopulation("CH48", 4, 8));
        subPopulationList.add(buildSubPopulation("INFO12", 0, 1));
        subPopulationList.add(buildSubPopulation("CH013", 0, 13));
        subPopulationList.add(buildSubPopulation("AD913", 9, 13));
        subPopulationList.add(buildSubPopulation("ADU14", 14, 0));
        subPopulationList.add(buildSubPopulation("AD1418", 14, 18));

    }
    
    private static void displaySubPopulationList() {
        for (SubPopulation subPopulation : subPopulationList) {
            System.out.println(subPopulation.getCode());
        }
    }
    
    private static SubPopulation buildSubPopulation(String code, int minimumAge, int maximumAge) {

        Units year = new Units();
        year.setCode(Units.UNIT_CODE_YEAR);
        
        SubPopulation subPopulation = new SubPopulation();
        subPopulation.setCode(code);
        subPopulation.setMinimumAge(minimumAge);
        subPopulation.setMinimumAgeUnits(year);
        subPopulation.setMaximumAge(maximumAge);
        subPopulation.setMaximumAgeUnits(year);
        
        return subPopulation;
        
    }
    
    
    
}
