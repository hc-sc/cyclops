/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DependantObject;
import ca.gc.hc.nhpd.model.DosageFormGroup;
import ca.gc.hc.nhpd.model.MonographDoseCombination;
import ca.gc.hc.nhpd.model.MonographPreparation;
import ca.gc.hc.nhpd.model.MonographUse;
import ca.gc.hc.nhpd.model.MonographSourceOrganismPart;
import ca.gc.hc.nhpd.model.TextMonographSourceMaterial;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author MRABE
 * 
 * First cut of MonographDependenciesWS. Will expose the dependencies as a
 * common contained object of each MDO. An MDO is a Monograph Dependent Object,
 * which is a subclass of DependentObject. When displaying data derived from an
 * MDO on the PLA form, depending on the context, the user will need to check
 * the dependencies contained herein.
 * 
 * For example, if a user has selected a Route of Admin as Oral, only the Risk
 * Statements associated with the selected Oral route of admin should be
 * displayed by the PLA. The form logic will need to check the dependencies of
 * the RiskStatements to filter out only appropriate RiskStatements, based on
 * the dependencies.
 * 
 * Basically, for the web service, we are changing the MDO to DependantObject
 * relationship from an inheritance relationship (in the model) to a containment
 * relationship in the web service. This hopefully will serve to simplify the
 * Web Service.
 * 
 */
public class MonographDependenciesWS {

    private String otherDependancy;
    private Set<MonographUseWS> monographUses;
    private Set<MonographSourceOrganismPartWS> monographSourceOrganismParts;
    private MonographPreparationWS monographPreparation;
    private MonographDoseCombinationWS monographDoseCombination;
    private MonographSourceIngredientWS monographSourceIngredient;
    private SubPopulationWS subPopulation;
    private Set<DosageFormGroupWS> dosageFormGroups;
    private Set<TextMonographSourceMaterialWS> otherMonographSources;
    // private boolean hasDependency;
    
    public boolean hasDependency() {
        // if (otherDependancy != null)
        if (otherDependancy != null
                || monographPreparation != null
                || monographDoseCombination != null
                || monographSourceIngredient != null
                || subPopulation != null) {
            return true;
        }
        if ((monographUses != null && monographUses.size() != 0)
                || (monographSourceOrganismParts != null && monographSourceOrganismParts.size() != 0) 
                || (dosageFormGroups != null && dosageFormGroups.size() != 0)
                || (otherMonographSources != null && otherMonographSources.size() != 0)) {
            return true;
        }
        return false;
    }
    
    public MonographDependenciesWS() {

    	
    	/*
        otherDependancy = new String();
        monographUses = new TreeSet<MonographUseWS>();
        monographSourceOrganismParts = new TreeSet<MonographSourceOrganismPartWS>(); // MrApr7
        monographPreparation = new MonographPreparationWS();
        subPopulation = new SubPopulationWS();
        monographDoseCombination = new MonographDoseCombinationWS();
        monographSourceIngredient = new MonographSourceIngredientWS();
        otherMonographSources = new TreeSet<TextMonographSourceMaterialWS>();
		*/

    }
    
    
   /** 
    * Creates a web service MongraphDependencies wrapper for a DependantObject.
    * Depending on the type, the appropriate wrappers for the dependencies are
    * created. 
    * 
    */
    public MonographDependenciesWS(DependantObject mdo) {
        
        // initialize all the contained WS dependencies from the DependantObject
        if (mdo == null) {
            return;
        }
        
        if (mdo.getOtherDependency() != null) {
            otherDependancy = mdo.getOtherDependency();
        }

        // NB. Don't load Uses for Uses, that recursion does not exist!
        if (!(mdo instanceof MonographUse) && mdo.getMonographUses() != null) {
        	
        	if (mdo instanceof MonographDoseCombination) {
        		MonographDoseCombination mdc = (MonographDoseCombination)mdo;
            	if (mdc.isDependenciesLoaded()) {
            		//log("MDC deps loaded - exiting!");
            		return;
            	}
        	}
        	
        	Set<MonographUse> mdoUses = mdo.getMonographUses();
            monographUses = new TreeSet<MonographUseWS>();
            for (MonographUse monoUse : mdoUses) {
                monographUses.add(new MonographUseWS(monoUse, monoUse.getDependencyLevel()));
            }
            
            //flag that this dependency is now populated to avoid 
            //doing this again in an infinite loop.
            if (mdo instanceof MonographDoseCombination) {
            	MonographDoseCombination mdc = (MonographDoseCombination)mdo;
            	mdc.setDependenciesLoaded(true);
            	//log("Setting MDC deps loaded true");
            }
        }

        // wrap up the OrganismParts with OrganismPartWS.
        if (mdo.getMonographSourceOrganismParts() != null) {
        	// hasDependency = true;
            Set<MonographSourceOrganismPart> sourceOrgParts = mdo
                    .getMonographSourceOrganismParts();
            monographSourceOrganismParts = new TreeSet<MonographSourceOrganismPartWS>();
            for (MonographSourceOrganismPart msop : sourceOrgParts) {
                monographSourceOrganismParts.add(new MonographSourceOrganismPartWS(
                    msop));
                }
        }

        if (mdo.getMonographPreparation() != null) {
            MonographPreparation monoPrep = mdo.getMonographPreparation();
            // TODO - should not have to do this check...
            if (monoPrep != null) {
                monographPreparation = new MonographPreparationWS(mdo
                        .getMonographPreparation());
            } else {
                monographPreparation = new MonographPreparationWS();
            }
        }

        if (mdo.getSubPopulation() != null) {
        	// hasDependency = true;
            subPopulation = new SubPopulationWS(mdo.getSubPopulation());
        }
        
        // NB. Don't load MDC for MDC, that recursion does not exist!
        if (!(mdo instanceof MonographDoseCombination) && 
        	  mdo.getMonographDoseCombination() != null) {
        	// hasDependency = true;
        	if (mdo instanceof MonographUse) {
        		MonographUse mu = (MonographUse)mdo;
                
                /**
                 * Monograph Use dependencies are called recursivly and multiple times
                 * for the web services.  It works with a setting of 1, 2, 3 and 5.
                 * I left it a 2 to support a 2nd level of recursive use dependency.
                 */
                if (mu.getDependencyLevel() > 2) {
                    
            		//log("MU deps loaded - exiting");
            		return;
            	}
        	}
        	
        	MonographDoseCombination mdc = mdo.getMonographDoseCombination();
            if (mdc != null) {
                // log.debug ("Creating monoDoseWS with monodose Id: " +
                // log.debug ("Dose min: " + md.getDoseMinimum());
                if (mdc.isVisible()) {
                    monographDoseCombination = new MonographDoseCombinationWS(mdo
                        .getMonographDoseCombination(), true);
                } else {
                    monographDoseCombination = new MonographDoseCombinationWS(mdo
                            .getMonographDoseCombination(), false);
                }
            }
            //flag that this dependency is now populated to avoid 
            //doing this again in an infinite loop.
            if (mdo instanceof MonographUse) {
            	MonographUse mu = (MonographUse)mdo;
            	//log("Setting MU deps loaded true");
            }

        }
        
        if (mdo.getMonographSourceIngredient() != null) {
            monographSourceIngredient = new MonographSourceIngredientWS(mdo
                    .getMonographSourceIngredient());
        }

        // Wrap the TextMonographSourceMaterials collection
        if (mdo.getOtherSources() != null) {
            Set<TextMonographSourceMaterial> textMonoSources = mdo
                .getOtherSources();
            if (textMonoSources != null) {
                otherMonographSources = new TreeSet<TextMonographSourceMaterialWS>();
                for (TextMonographSourceMaterial tmsm : textMonoSources) {
                    otherMonographSources.add(new TextMonographSourceMaterialWS(
                        tmsm));
                }
            }
        }

        
        if (mdo.getDosageFormGroups() != null) {
            dosageFormGroups = new TreeSet<DosageFormGroupWS>();
            for (DosageFormGroup dosageFormGroup : mdo.getDosageFormGroups()) {
                dosageFormGroups.add(new DosageFormGroupWS(dosageFormGroup));
            }
        }
        
    }

    public Set<MonographUseWS> getMonographUses() {
        return monographUses;
    }

    public void setMonographUses(Set<MonographUseWS> monographUses) {
        this.monographUses = monographUses;
    }

    public String getOtherDependancy() {
        return otherDependancy;
    }

    public void setOtherDependancy(String otherDependancy) {
        this.otherDependancy = otherDependancy;
    }

    public SubPopulationWS getSubPopulation() {
        return subPopulation;
    }

    public void setSubPopulation(SubPopulationWS subPopulation) {
        this.subPopulation = subPopulation;
    }

    public Set<DosageFormGroupWS> getDosageFormGroups() {
        return dosageFormGroups;
    }

    public void setDosageFormGroups(Set<DosageFormGroupWS> dosageFormGroups) {
        this.dosageFormGroups = dosageFormGroups;
    }

    public MonographDoseCombinationWS getMonographDoseCombination() {
        return monographDoseCombination;
    }

    public void setMonographDoseCombination(
            MonographDoseCombinationWS monographDoseCombination) {
        this.monographDoseCombination = monographDoseCombination;
    }

    public MonographPreparationWS getMonographPreparation() {
        return monographPreparation;
    }

    public void setMonographPreparation(
            MonographPreparationWS monographPreparation) {
        this.monographPreparation = monographPreparation;
    }

    public MonographSourceIngredientWS getMonographSourceIngredient() {
        return monographSourceIngredient;
    }

    public void setMonographSourceIngredient(
            MonographSourceIngredientWS monographSourceIngredient) {
        this.monographSourceIngredient = monographSourceIngredient;
    }

    public Set<MonographSourceOrganismPartWS> getMonographSourceOrganismParts() {
        return monographSourceOrganismParts;
    }

    public void setMonographSourceOrganismParts(
            Set<MonographSourceOrganismPartWS> monographSourceOrganismParts) {
        this.monographSourceOrganismParts = monographSourceOrganismParts;
    }

    public Set<TextMonographSourceMaterialWS> getOtherMonographSources() {
        return otherMonographSources;
    }

    public void setOtherMonographSources(
            Set<TextMonographSourceMaterialWS> otherMonographSources) {
        this.otherMonographSources = otherMonographSources;
    }
    
}
