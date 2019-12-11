package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.OrganismMonographEntry;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.PreparationTypeGroup;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class OrganismMonographEntryWS extends MonographEntryWS {
    // the wrapped OrganismMonographEntry
    private OrganismMonographEntry organismMonographEntry;

    public OrganismMonographEntryWS(
            OrganismMonographEntry organismMonographEntry) {
        super(organismMonographEntry);
        this.organismMonographEntry = organismMonographEntry;
    }

    public OrganismMonographEntryWS() {
        super(new OrganismMonographEntry());
        this.organismMonographEntry = new OrganismMonographEntry();
    }

    public Set<PreparationTypeGroupWS> getPreparationTypeGroups() {
        Set<PreparationTypeGroupWS> prepTypeGroupsWS = new TreeSet<PreparationTypeGroupWS>();
        // wrap the prepTypeGroups collection
        PreparationTypeGroup prepTypeGroup = null;
        Iterator<PreparationTypeGroup> iter = organismMonographEntry.getPreparationTypeGroups()
                .iterator();
        while (iter.hasNext()) {
            prepTypeGroup = (PreparationTypeGroup) (iter.next());
            prepTypeGroupsWS.add(new PreparationTypeGroupWS(prepTypeGroup));
        }

        return prepTypeGroupsWS;

    }

    public void setPreparationTypeGroups(
            Set<PreparationTypeGroupWS> preparationTypeGroups) {
        //
    }

    public Set<PreparationTypeWS> getPreparationTypes() {
        Set<PreparationTypeWS> prepTypesWS = new TreeSet<PreparationTypeWS>();
        // wrap the prepTypes collection
        PreparationType prepType = null;
        Iterator<PreparationType> iter = organismMonographEntry.getPreparationTypes().iterator();
        while (iter.hasNext()) {
            prepType = (PreparationType) (iter.next());
            prepTypesWS.add(new PreparationTypeWS(prepType));
        }

        return prepTypesWS;

    }

    public void setPreparationTypes(Set<PreparationTypeWS> preparationTypes) {
        //
    }

}
