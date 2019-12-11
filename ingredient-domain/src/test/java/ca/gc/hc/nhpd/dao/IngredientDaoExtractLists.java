package ca.gc.hc.nhpd.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.dto.MonographSearchResult;
import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.Ingredient;

public class IngredientDaoExtractLists extends ConfigurationIT {

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    MonographDao monographDao;

    @Autowired
    DefinedOrganismSubstanceDao definedOrganismSubstanceDao;

    private String EMPTY = "[EMPTY]";

    @Test
    public void outputSelectedIngredients2849() {
	outputIngredient(2849L);
    }
    
    @Test
    public void outputSelectedIngredients() {
	outputIngredient(16010L);
	outputIngredient(6171L);
	outputIngredient(16275L);
	outputIngredient(2849L);
	outputIngredient(1048L);
	outputIngredient(16277L);
	outputIngredient(12135L);
    }

    @Test
    public void outputAll() {

	for (int i = 0; i < 30000; i++) {

	    outputIngredient(new Long(i));

	}

    }

    private void outputIngredient(Long id) {

	Ingredient ingredient = null;
	try {
	    ingredient = (Ingredient) ingredientDao.findById(id, false);
	} catch (ObjectNotFoundException onfe) {

	    System.out.println("Skipping: " + id);

	}

	if (ingredient != null) {

	    System.out.println("Working on: " + id);

	    outputIngredient(ingredient);

	}

    }

    private void outputIngredient(Ingredient ingredient) {

	System.out.println("Processing ingredient: " + ingredient.getAuthorizedName());

	try {
	    BufferedWriter out = new BufferedWriter(
		    new FileWriter("c:\\opt\\ingredients\\ingred_" + ingredient.getId() + ".txt"));

	    out.write("Ingredient:         " + ingredient.getAuthorizedName() + System.lineSeparator());
	    out.write("Ingredient ID:      " + ingredient.getId() + System.lineSeparator());
	    out.write(System.lineSeparator());
	    out.write("Monographs:         " + getMonographs(ingredient) + System.lineSeparator());
	    out.write("Source Ingredients: " + getSourceIngredients(ingredient) + System.lineSeparator());
	    out.write("Source Materials:   " + getSourceMaterials(ingredient) + System.lineSeparator());
	    out.write("Constituents:       " + getConstituents(ingredient) + System.lineSeparator());
	    out.write("Is Probiotic:       " + isProbiotic(ingredient) + System.lineSeparator());

	    out.close();

	    String sourceIngredients = getSourceIngredients(ingredient);
	    String sourceMaterials = getSourceMaterials(ingredient);
	    
	    
	    if (sourceIngredients.contains(EMPTY) && sourceMaterials.contains(EMPTY)) {

		BufferedWriter out2 = new BufferedWriter(
			new FileWriter("c:\\opt\\ingredients\\NO_SOURCE_ingred_" + ingredient.getId() + ".txt"));

		out.write("INGREDIENT HAS NO SOURCES: " + ingredient.getId());

		out2.close();

	    }

	} catch (IOException e) {
	    // do nothing.
	}

    }

    private String getMonographs(Ingredient ingredient) {

	List<MonographSearchResult> monographSearchResults = monographDao
		.getMonographsByIngredientId(ingredient.getId(), Locale.ENGLISH.getISO3Language());

	if (monographSearchResults == null || monographSearchResults.size() == 0) {
	    return EMPTY;
	}

	StringBuilder sb = new StringBuilder();
	for (MonographSearchResult monograph : monographSearchResults) {
	    if (sb.length() != 0) {
		sb.append(", ");
	    }
	    sb.append(monograph.getMonographName());
	}

	return "[" + sb.toString() + "]";

    }

    private String getSourceIngredients(Ingredient ingredient) {

	List<RelatedSourceIngredient> relatedSourceIngredients = ingredientDao
		.getRelatedSourceIngredients(ingredient.getId(), false);

	if (relatedSourceIngredients == null || relatedSourceIngredients.size() == 0) {
	    return EMPTY;
	}

	StringBuilder sb = new StringBuilder();
	for (RelatedSourceIngredient relatedSourceIngredient : relatedSourceIngredients) {
	    if (sb.length() != 0) {
		sb.append(", ");
	    }
	    sb.append(relatedSourceIngredient.getIngredientNameEnglish());
	}

	return "[" + sb.toString() + "]";

    }

    private String getSourceMaterials(Ingredient ingredient) {

	List<String> sourceMaterials = ingredientDao.findSourceMaterials(ingredient.getId(), false);

	if (sourceMaterials == null || sourceMaterials.size() == 0) {
	    return EMPTY;
	}

	return sourceMaterials.toString();

    }

    private String getConstituents(Ingredient ingredient) {

	List<String> sourceMaterials = ingredientDao.findConstituents(ingredient.getId(), false);

	if (sourceMaterials == null || sourceMaterials.size() == 0) {
	    return EMPTY;
	}

	return sourceMaterials.toString();

    }

    private String isProbiotic(Ingredient ingredient) {

	Long ingredId = ingredient.getId();

	Boolean probioticFlag = definedOrganismSubstanceDao.isProbiotic(ingredId);

	if (probioticFlag == null || probioticFlag == false) {
	    return "[no]";
	}

	return "[yes]";
    }

}
