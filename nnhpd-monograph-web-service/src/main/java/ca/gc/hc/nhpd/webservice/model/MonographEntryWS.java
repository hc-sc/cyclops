/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.CustomOrganismSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.MonographDoseCombination;
import ca.gc.hc.nhpd.model.MonographEntry;
import ca.gc.hc.nhpd.model.MonographSourceOrganismPart;
import ca.gc.hc.nhpd.model.TextMonographSourceMaterial;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographEntryWS {

	private static final Log log = LogFactory.getLog(MonographEntryWS.class);
	
    // The wrapped MonographEntry
    private MonographEntry monographEntry;

    public MonographEntryWS() {
    }

    public MonographEntryWS(MonographEntry monographEntry) {
        this.monographEntry = monographEntry;
    }

    public IngredientWS getIngredientWS() {
    	Ingredient ingredient = monographEntry.getIngredient();
    	return wrapIngredient(ingredient);
    }
    
    public void setIngredientWS(IngredientWS ingredient) {
        // null impl
    }
    
    private IngredientWS wrapIngredient(Ingredient ingredient) {

        try {

            if (ingredient instanceof ChemicalSubstance) {
                log.debug("Wrapping ChemicalSubstance Ingredient...");
                return new ChemicalSubstanceWS((ChemicalSubstance) ingredient,
                        false);
            }
            if (ingredient instanceof DefinedOrganismSubstance) {
                log.debug("Wrapping DefinedOrganismSubstance Ingredient: id: "
                        + ingredient.getId());
                return new DefinedOrganismSubstanceWS(
                        (DefinedOrganismSubstance) ingredient, false);
            }
            if (ingredient instanceof CustomOrganismSubstance) {
                log.debug("Wrapping CustomOrganismSubstance Ingredient...");
                return new CustomOrganismSubstanceWS(
                        (CustomOrganismSubstance) ingredient, false);
            }

        } catch (Exception ex) {
            log.error("CAUGHT EXCEPTION: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Throwable t) {
            log.error("CAUGHT THROWABLE: " + t.getMessage());
            t.printStackTrace();
        }

        log.error(" Unknown Ingredient subtype in WrapIngredient!");
        return new IngredientWS(ingredient);
    }
    
    public Set<MonographSourceOrganismPartWS> getMonographSourceOrganismParts() {
        Set<MonographSourceOrganismPartWS> retOrganismParts = new TreeSet<MonographSourceOrganismPartWS>();
        Set<MonographSourceOrganismPart> msops = monographEntry
                .getMonographSourceOrganismParts();

        if (msops != null) {
            for (MonographSourceOrganismPart msop : msops) {
                retOrganismParts.add(new MonographSourceOrganismPartWS(msop));
            }
        }
        return retOrganismParts;
    }

    public void setMonographSourceOrganismParts(
            Set<MonographSourceOrganismPartWS> organismParts) {
        // null impl
    }

    public Set<MonographDoseCombinationWS> getMonographDoseCombinations() {
        Set<MonographDoseCombinationWS> retMdcs = new TreeSet<MonographDoseCombinationWS>();

        Set<MonographDoseCombination> mdcs = monographEntry
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
        // null impl
    }

    public Set<TextMonographSourceMaterialWS> getOtherMonographSources() {
        Set<TextMonographSourceMaterialWS> testMonoSources = new TreeSet<TextMonographSourceMaterialWS>();
        Set<TextMonographSourceMaterial> tmsms = monographEntry
                .getOtherMonographSources();
        if (tmsms != null) {
            for (TextMonographSourceMaterial tmsm : tmsms) {
                testMonoSources.add(new TextMonographSourceMaterialWS(tmsm));
            }
        }

        return testMonoSources;
    }

    public void setOtherMonographSources(
            Set<TextMonographSourceMaterialWS> otherSources) {
        // no impl
    }
    
}
