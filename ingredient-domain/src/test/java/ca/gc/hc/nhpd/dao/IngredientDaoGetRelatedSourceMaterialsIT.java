package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;

/**
 * Test that verifies the IngredientDao.getRelatedSourceMaterials() using
 * different known ingredients.  Used by the NNHPD Forms (AN Tool) to populate
 * the Source Material list.
 */

public class IngredientDaoGetRelatedSourceMaterialsIT extends ConfigurationIT {

    @Autowired
    IngredientDao ingredientDao;
    
    @Autowired
    OrganismPartTypeDao organismPartTypeDao;
    
    @Autowired
    OrganismTypeGroupDao organismTypeGroupDao;
    
    
    // EHP_Arsenicum album - 7833
    @Test
    public void validateIngredient7833() {
        
    	List<String> sources =
                getRelatedSourceMaterials(7833L);
        
    	displayList(sources);
    	
        assertNotNull(sources);
        assertTrue(sources.size() == 2);
        assertEquals(sources.get(0), "Arsenic trioxide");
        assertEquals(sources.get(1), "Trioxyde d'arsenic");

        
    }

    
    // Ginkgo biloba - 6171
    @Test
    public void validateIngredient6171() {
        
    	List<String> sources =
                getRelatedSourceMaterials(6171L);
        
    	displayList(sources);
    	
        assertNotNull(sources);
        assertTrue(sources.size() == 236);
        assertEquals(sources.get(0), "Ginkgo biloba - Aged bark");
        assertEquals(sources.get(1), "Ginkgo biloba - Anther");
        assertEquals(sources.get(2), "Ginkgo biloba - Balsam");
        assertEquals(sources.get(3), "Ginkgo biloba - Bark and bud");
        assertEquals(sources.get(4), "Ginkgo biloba - Blade");
        
    }
	
    // Iron (II) gluconate - 16275
    @Test
    public void validateIngredient16275() {
        
    	List<String> sources =
                getRelatedSourceMaterials(16275L);
        
    	assertNotNull(sources);
    	assertTrue(sources.size() == 283);
    	assertEquals(sources.get(0), "Achyranthes japonica - Root");
    	assertEquals(sources.get(1), "Actaea racemosa - Root");
    	assertEquals(sources.get(2), "Agathosma betulina - Leaf");
    	assertEquals(sources.get(3), "Alisma plantago-aquatica - Rhizome");
    	assertEquals(sources.get(4), "Allium cepa - Bulb");
        
    }
    
    // Calamine - 2849
    @Test
    public void validateIngredient2849() {
        
        List<String> sources =
                getRelatedSourceMaterials(2849L);
        
        assertNull(sources);
        
    }

    // Beta-carotene - 1048
    @Test
    public void validateIngredient1048() {
        
        List<String> sources =
                getRelatedSourceMaterials(1048L);

        displayList(sources);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 186);
        assertEquals(sources.get(0), "Allium cepa - Bulb");
        assertEquals(sources.get(1), "Allium cepa - Flower");
        assertEquals(sources.get(2), "Allium cepa - Leaf");
        assertEquals(sources.get(3), "Allium sativum - Bulb");
        assertEquals(sources.get(4), "Allium sativum - Flower");
        
    }

    // Iron (II) Sulphate - 16277
    @Test
    public void validateIngredient16277() {
        
        List<String> sources =
                getRelatedSourceMaterials(16277L);

        displayList(sources);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 284);
        assertEquals(sources.get(0), "Achyranthes japonica - Root");
        assertEquals(sources.get(1), "Actaea racemosa - Root");
        assertEquals(sources.get(2), "Agathosma betulina - Leaf");
        assertEquals(sources.get(3), "Alisma plantago-aquatica - Rhizome");
        assertEquals(sources.get(4), "Allium cepa - Bulb");
        
    }

    // Lactobacillus johnsonii La1 - 12135
    @Test
    public void validateIngredient12135() {
        
        List<String> sources =
                getRelatedSourceMaterials(12135L);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 1);
        String source = sources.get(0);
        assertEquals(source, "Lactobacillus johnsonii La1 - Whole Cell");
        
    }

    // All organism plant parts.
    @Test
    public void validateIngredient16010() {
        
        List<String> sources =
                getRelatedSourceMaterials(16010L);
        
        assertNotNull(sources);
        assertTrue(sources.size() == 1);
        String source = sources.get(0);
        assertEquals(source, "Ginkgo biloba - Leaf");
        
    }

    private void displayList(List<String> sources) {
    	
    	if (sources == null) {
    		System.out.println("sources is null!");
    		return;
    	}
    	
    	System.out.println("list size: " + sources.size());
    	for (String source : sources) {
    		System.out.println(source);
    	}
    	
    }
    
	private List<String> getRelatedSourceMaterials(Long ingredId) {
	    return ingredientDao.findSourceMaterials(ingredId, false); 
	}
            

}
