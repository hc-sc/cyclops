package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;

/**
 * Test that verifies the IngredientDao.getRelatedSourceIngredients() using
 * different known ingredients.  Used by the NNHPD Forms (AN Tool) to populate
 * the Source Ingredient list.
 */

public class IngredientDaoGetRelatedSourceIngredientsIT extends ConfigurationIT {

    @Autowired
    IngredientDao ingredientDao;

    // Folium Ginkgo - 16010
    @Test
    public void validateIngredient16010() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(16010L);
        
        assertNull(sources);
        
    }
    
    // Ginkgo biloba - 6171
    @Test
    public void validateIngredient6171() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(6171L);
        
        assertNull(sources);
        
    }

    // Iron (II) gluconate - 16275
    @Test
    public void validateIngredient16275() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(16275L);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 1);
        
        RelatedSourceIngredient source = sources.get(0);
        
        assertEquals(source.getIngredientNameEnglish(), "Iron (II) gluconate, dihydrate");
        
    }
    
    // Calamine - 2849
    @Test
    public void validateIngredient2849() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(2849L);
        
        assertNull(sources);
        
    }
    
    // Beta-carotene - 1048
    @Test
    public void validateIngredient1048() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(1048L);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 1);
        
        RelatedSourceIngredient source = sources.get(0);
        
        assertEquals(source.getIngredientNameEnglish(), "Mixed carotenoids");
        
    }
    
    // Iron (II) Sulphate - 16277
    @Test
    public void validateIngredient16277() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(16277L);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 2);
        
        RelatedSourceIngredient source1 = sources.get(0);
        assertEquals(source1.getIngredientNameEnglish(), "Iron (II) sulphate, heptahydrate");

        RelatedSourceIngredient source2 = sources.get(1);
        assertEquals(source2.getIngredientNameEnglish(), "Ferrous sulfate monohydrate");

    }
    
    // Lactobacillus johnsonii La1 - 12135
    @Test
    public void validateIngredient12135() {
        
        List<RelatedSourceIngredient> sources =
                getRelatedSourceIngredients(12135L);
        
        assertNull(sources);
        
    }
	
	private List<RelatedSourceIngredient> getRelatedSourceIngredients(Long ingredId) {
	    return ingredientDao.getRelatedSourceIngredients(ingredId, true); 
	}
            

}
