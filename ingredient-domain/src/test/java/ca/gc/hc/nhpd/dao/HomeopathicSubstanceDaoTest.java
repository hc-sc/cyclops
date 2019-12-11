package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;

public class HomeopathicSubstanceDaoTest extends ConfigurationIT {

    @Autowired
    HomeopathicSubstanceDao homeopathicSubstanceDao;
    
    @Test 
    public void findHomeopathicSourceMaterialsTest() {
	
	List<String> homeopathicSourceMaterials = getSourceMaterials(7833L);
	
	assertTrue(homeopathicSourceMaterials.size() == 2);
	assertEquals(homeopathicSourceMaterials.get(0), "Arsenic trioxide");
	assertEquals(homeopathicSourceMaterials.get(1), "Trioxyde d'arsenic");
	
    }
    
    private List<String> getSourceMaterials(Long ingredientId) {
	
	return homeopathicSubstanceDao.findHomeopathicSourceMaterials(ingredientId, false);
	
    }
    
}
