package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographDoseCombination;
import ca.gc.hc.nhpd.model.MonographDuration;
import ca.gc.hc.nhpd.model.MonographPhase;
import java.util.Set;
import java.util.TreeSet;

public class MonographPhaseWS implements Comparable<MonographPhaseWS> {

    // The wrapped MonographPhase
    private MonographPhase monographPhase;

    public MonographPhaseWS(MonographPhase monographPreparation) {
        this.monographPhase = monographPreparation;
    }

    public MonographPhaseWS() {
        monographPhase = new MonographPhase();
    }

    public Set<MonographDoseCombinationWS> getMonographDoseCombinations() {

        Set<MonographDoseCombinationWS> retMdcs = new TreeSet<MonographDoseCombinationWS>();
        Set<MonographDoseCombination> mdcs = monographPhase
                .getMonographDoseCombinations();

        if (mdcs != null) {
            for (MonographDoseCombination mdc : mdcs) {
                retMdcs.add(new MonographDoseCombinationWS(mdc, true));
            }
        }
        return retMdcs;
    }

    public void setMonographDoseCombinations(
            Set<MonographDoseCombinationWS> mdcs) {

    }

    public Set<MonographDurationWS> getMonographDurations() {
        Set<MonographDurationWS> retMonoDurations = new TreeSet<MonographDurationWS>();
        Set<MonographDuration> monoDurations = monographPhase
                .getMonographDurations();

        if (monoDurations != null) {
            for (MonographDuration monoDuration : monoDurations) {
                retMonoDurations.add(new MonographDurationWS(monoDuration));
            }
        }
        return retMonoDurations;
    }

    public void setMonographDurations(
            Set<MonographDurationWS> monographDurations) {

    }

    public String getName() {
        return monographPhase.getName();
    }

    public void setName(String name) {

    }

    public int getOrder() {
        return monographPhase.getOrder();
    }

    public void setOrder(int order) {

    }

    public String getCode() {
        return monographPhase.getCode();
    }
    
    public void setCode(String code) {
        
    }
    
    public int compareTo(MonographPhaseWS o) throws ClassCastException {
        // delegate to wrapped type
        return monographPhase.compareTo(o.monographPhase);
    }

}
