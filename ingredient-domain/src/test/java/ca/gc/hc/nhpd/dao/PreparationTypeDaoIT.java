package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.ProvinceState;

/**
 * Integration test used to verify the CountryDao.
 */

public class PreparationTypeDaoIT extends AbstractDaoIT<PreparationType> {
	
	@Autowired
	PreparationTypeDao dao;
	
	@Test
	public void findPreparationTypeByCodeFreshTest() {
		
		PreparationType preparationType = dao.findByCode("FRESH");
		
		assertEquals(preparationType.getCode(), "FRESH");
		assertEquals(preparationType.getNameE(), "Fresh");
		assertEquals(preparationType.getNameF(), "Pr�paration fra�che");
		
		System.out.println(preparationType);
		
	}
	
	@Test
	public void findPreparationTypeByCodeDryTest() {
		
		PreparationType preparationType = dao.findByCode("DRY");
		
		assertEquals(preparationType.getCode(), "DRY");
		assertEquals(preparationType.getNameE(), "Dry");
		assertEquals(preparationType.getNameF(), "Pr�paration s�ch�e");
		
		System.out.println(preparationType);
		
	}
	
	
}
