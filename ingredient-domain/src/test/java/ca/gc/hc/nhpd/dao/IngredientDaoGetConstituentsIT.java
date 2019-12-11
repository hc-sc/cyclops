package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;

/**
 * Test that verifies the IngredientDao.getRelatedSourceIngredients() using
 * different known ingredients.  Used by the NNHPD Forms (AN Tool) to populate
 * the Source Ingredient list.
 */

public class IngredientDaoGetConstituentsIT extends ConfigurationIT {

    @Autowired
    IngredientDao ingredientDao;

    // Folium Ginkgo - 16010
    @Test
    public void validateIngredient16010() {
        
        List<String> constituents =
                findConstituents(16010L);
        
        displayConstituents(constituents);
        
        assertTrue(constituents.size() == 6);
        assertEquals(constituents.get(0), "Beta-carotene");
        assertEquals(constituents.get(1), "Flavonoid glycosides");
        assertEquals(constituents.get(2), "Ginkgolide A");
        assertEquals(constituents.get(3), "Iron");
        assertEquals(constituents.get(4), "Terpene lactones");
        assertEquals(constituents.get(5), "Vitamin C");
        
    }
    
    // Ginkgo biloba - 6171
    @Test
    public void validateIngredient6171() {
        
        List<String> constituents =
                findConstituents(6171L);
        
        displayConstituents(constituents);
        
        assertTrue(constituents.size() == 6);
        assertEquals(constituents.get(0), "Beta-carotene");
        assertEquals(constituents.get(1), "Flavonoid glycosides");
        assertEquals(constituents.get(2), "Ginkgolide A");
        assertEquals(constituents.get(3), "Iron");
        assertEquals(constituents.get(4), "Terpene lactones");
        assertEquals(constituents.get(5), "Vitamin C");
        
    }

    // Iron (II) gluconate - 16275
    @Test
    public void validateIngredient16275() {
        
        List<String> constituents =
                findConstituents(16275L);
        
        displayConstituents(constituents);
        
        displayConstituents(constituents);
        
        assertTrue(constituents.size() == 1);
        assertEquals(constituents.get(0), "Iron");
        
    }
    
    // Calamine - 2849
    @Test
    public void validateIngredient2849() {
        
        List<String> constituents =
                findConstituents(2849L);
        
        displayConstituents(constituents);
        
        assertNull(constituents);
        
    }
    
    // Beta-carotene - 1048
    @Test
    public void validateIngredient1048() {
        
        List<String> constituents =
                findConstituents(1048L);
        
        displayConstituents(constituents);
        
        assertNull(constituents);
        
    }
    
    // Iron (II) Sulphate - 16277
    @Test
    public void validateIngredient16277() {
        
        List<String> constituents =
                findConstituents(16277L);
        
        displayConstituents(constituents);
        
        assertTrue(constituents.size() == 2);
        assertEquals(constituents.get(0), "Iron");
        assertEquals(constituents.get(1), "Sulfur");

    }
    
    // Lactobacillus johnsonii La1 - 12135
    @Test
    public void validateIngredient12135() {
        
        List<String> constituents =
                findConstituents(12135L);
        
        displayConstituents(constituents);
        
        assertNull(constituents);
        
    }
	
    private void displayConstituents(List<String> constituents) {
        
        if (constituents == null) {
            System.out.println("No consituents to display!");
            return;
        }
        
        System.out.println("Consitutent size: " + constituents.size());
        
        for (String constituent : constituents) {
            System.out.println(constituent);
        }
        
    }
    
	private List<String> findConstituents(Long ingredId) {
	    return ingredientDao.findConstituents(ingredId, false); 
	}
            

}
