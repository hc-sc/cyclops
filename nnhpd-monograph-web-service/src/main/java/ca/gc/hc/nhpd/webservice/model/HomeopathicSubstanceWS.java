package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.HomeopathicFormula;
import ca.gc.hc.nhpd.model.HomeopathicGenericText;
import ca.gc.hc.nhpd.model.HomeopathicMethodOfPreparation;
import ca.gc.hc.nhpd.model.HomeopathicRole;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.IngredientRole;
import ca.gc.hc.nhpd.model.Synonym;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * This is a web service wrapper class used to represent a homeopathic substance
 * ingredient. It can be constructed by passing a homeopathic substance in the
 * constructor, or as a new homeopathic substance when the constructor is left
 * empty.
 */

public class HomeopathicSubstanceWS extends IngredientWS {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
    // ~ Static fields/initializers
    // ---------------------------------------------

    private HomeopathicSubstance homeopathicSubstance;

    private HomeopathicFormula homeopathicFormula;

    // ~ Constructors
    // -----------------------------------------------------------

    /**
     * Constructor used to instantiate this class with an instance of the
     * homeopathic substance.
     * 
     * @param homeopathicSubstance
     *            being used to instantiate this class.
     */
    public HomeopathicSubstanceWS(HomeopathicSubstance homeopathicSubstance,
            boolean bilingual) {
        super(homeopathicSubstance);
        if (homeopathicSubstance == null) {
            throw new IllegalArgumentException("homeopathicSubstance is null!");
        }
        this.homeopathicSubstance = homeopathicSubstance;
        this.bilingual = bilingual;
        init();
    }

    /**
     * Constructor used to instantiate this class. When called, the homeopathic
     * substance is instantiated as a new object.
     */
    public HomeopathicSubstanceWS() {
        super(new HomeopathicSubstance());
        this.homeopathicSubstance = new HomeopathicSubstance();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    // ATF Required
    public Boolean getAtfRequired() {
        return homeopathicFormula.getAtfRequired();
    }

    public void setAtfRequired(Boolean value) {
        // Not Implemented.
    }

    // ATF Required In Processing
    public Boolean getAtfRequiredInProcessing() {
        return homeopathicFormula.getAtfRequiredInProcessingMi();
    }

    public void setAtfRequiredInProcessing(Boolean value) {
        // Not Implemented.
    }

    // ATF Required In Processing
    public Boolean getKnownAsSynthetic() {
        return homeopathicFormula.getKnownAsSynthetic();
    }

    public void setKnownAsSynthetic(Boolean value) {
        // Not Implemented.
    }

    // Notes
    public String getNotes() {
        return homeopathicFormula.getNotes();
    }

    public void setNotes(String value) {
        // Not Implemented.
    }

    // Source Material
    public List<String> getSourceMaterials() {

        if (homeopathicFormula == null
                || homeopathicFormula.getSourceMaterials() == null) {
            return null;
        }

        List<String> sourceMaterials = new ArrayList<String>();
        for (HomeopathicGenericText homeopathicSourceMaterial : homeopathicFormula
                .getSourceMaterials()) {
            if (homeopathicSourceMaterial.getText() != null) {
                sourceMaterials.add(homeopathicSourceMaterial.getText());
            }
        }
        return sourceMaterials;
    }

    public void setSourceMaterials(List<String> value) {
        // Not Implemented.
    }

    // Reference Code
    public String getReferenceCode() {
        if (homeopathicFormula.getReference() != null
                && homeopathicFormula.getReference().getCode() != null) {
            return homeopathicFormula.getReference().getCode();
        }
        return null;
    }

    public void setReferenceCode(String value) {
        // Not Implemented.
    }

    // Reference Name
    public String getReferenceName() {
        if (homeopathicFormula.getReference() != null
                && homeopathicFormula.getReference().getName() != null) {
            return homeopathicFormula.getReference().getName();
        }
        return null;
    }

    public void setReferenceName(String value) {
        // Not Implemented.
    }

    // Authorized Name
    public String getAuthorizedName() {
        return homeopathicSubstance.getAuthorizedName();
    }

    public void setAuthorizedName(String name) {
        // Not Implemented.
    }

    // Toxic
    public String getToxic() {
        if (homeopathicFormula != null
                && homeopathicFormula.getToxic() != null
                && homeopathicFormula.getToxic() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setToxic(String toxic) {
        //
    }
    
    // Ingredient Type
    public String getType() {
        return homeopathicSubstance.getType();
    }

    public void setType(String value) {
        // Not Implemented.
    }

    // Proper Names
    public List<String> getProperNames() {
        List<String> properNames = new ArrayList<String>();
        if (homeopathicSubstance.getHomeopathicProperNames() != null) {
            for (HomeopathicGenericText homeopathicProperName : homeopathicSubstance
                    .getHomeopathicProperNames()) {
                properNames.add(homeopathicProperName.getText());
            }
        }
        return properNames;
    }

    public void setProperNames(Set<String> properNames) {
        // Not Implemented.
    }

    // Common Names
    public List<String> getCommonNames() {
        
        List<String> commonNames = new ArrayList<String>();
        if (homeopathicSubstance.getDisplayCommonNames() != null) {
            for (Synonym homeopathicCommonName : homeopathicSubstance
                    .getDisplayCommonNames()) {
                if (homeopathicCommonName != null && homeopathicCommonName.getName() != null) {
                    commonNames.add(homeopathicCommonName.getName());
                }
            }
            
            
        }
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        // Not Implemented.
    }

    // Homeopathic Dilution Code
    public String getHomeopathicDilutionCode() {
        if (homeopathicFormula.getHomeopathicDilution() == null) {
            return null;
        }
        return homeopathicFormula.getHomeopathicDilution().getCode();
    }

    public void setHomeopathicDilutionCode(String value) {
        // Not Implemented.
    }

    // HomeopathicMethodOfPreparation
    public Set<HomeopathicMethodOfPreparationWS> getHomeopathicMethodOfPreparations() {
        Set<HomeopathicMethodOfPreparationWS> homeopathicMethodOfPreparations = new TreeSet<HomeopathicMethodOfPreparationWS>();
        if (homeopathicFormula.getHomeopathicMethodOfPreparations() != null) {
            for (HomeopathicMethodOfPreparation homeopathicMethodOfPreparation : homeopathicFormula
                    .getHomeopathicMethodOfPreparations()) {
                HomeopathicMethodOfPreparationWS hmopWS = new HomeopathicMethodOfPreparationWS(
                        homeopathicMethodOfPreparation, homeopathicFormula);
                homeopathicMethodOfPreparations.add(hmopWS);
            }
        }
        return homeopathicMethodOfPreparations;
    }

    public void setHomeopathicMethodOfPreparations(
            Set<HomeopathicMethodOfPreparationWS> value) {
        // Not Implemented.
    }

    /**
     * This initialization method leverages an existing homeopathic substance to
     * extract it's homeopathic role and populate the homeopathic formula. In
     * the current implementation each homeopathic substance has a single
     * homeopathic formula.
     */
    private void init() {
        if (homeopathicSubstance != null) {
            Set<IngredientRole> roles = homeopathicSubstance.getRoles();
            if (roles != null) {
                HomeopathicRole homeopathicRole = (HomeopathicRole) roles
                        .iterator().next();
                Set<HomeopathicFormula> homeopathicFormulas = homeopathicRole
                        .getHomeopathicFormulas();
                this.homeopathicFormula = (HomeopathicFormula) homeopathicFormulas
                        .iterator().next();
            }
        }
    }

}
