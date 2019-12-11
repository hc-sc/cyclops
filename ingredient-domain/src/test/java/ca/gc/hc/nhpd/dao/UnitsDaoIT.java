package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.dao.UnitsDao;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.util.PagedList;

/**
 * Integration test used to verify the UnitsDao.
 */

public class UnitsDaoIT extends AbstractDaoIT<Units> {

	private static final String UNITTYPE_TIME = "TIME";
	
	private static final String UNITTYPE_MASS = "MASS";
	
	@Autowired
	UnitsDao unitsDao;

	@Test
    public void findByCodesLanguageOrderedEnglish() {
        String[] codes = {"h", "d", "wk", "mnths"}; //hours, days, weeks, months
        List<Units> units = unitsDao.findByCodesLanguageOrdered(codes,
            Locale.ENGLISH.getISO3Language());

        assertNotNull(units);
        assertTrue(units.size() == 4);
        
        assertEquals("Day", units.get(0).getNameE());
        assertEquals("Hour", units.get(1).getNameE());
        assertEquals("Month", units.get(2).getNameE());
        assertEquals("Week", units.get(3).getNameE());
    }

    @Test
    public void findByCodesLanguageOrderedFrench() {
        String[] codes = {"h", "d", "wk", "mnths"}; //hours, days, weeks, months
        List<Units> units = unitsDao.findByCodesLanguageOrdered(codes,
            Locale.CANADA_FRENCH.getISO3Language());

        assertNotNull(units);
        assertTrue(units.size() == 4);
        
        assertEquals("Heure", units.get(0).getNameF());
        assertEquals("Jour", units.get(1).getNameF());
        assertEquals("Mois", units.get(2).getNameF());
        assertEquals("Semaine", units.get(3).getNameF());

    }
	
	@Test
	public void findByType() {
		
		PagedList pagedList = unitsDao.findByType("Biological units", LANG_FR);
		
		assertNotNull(pagedList);
		assertTrue(pagedList.size() > 0);
		
	}
	
	@Test
	public void findByTypeCode() {
		
		PagedList pagedList = unitsDao.findByTypeCode("DOSF", LANG_FR);
		
		assertNotNull(pagedList);
		assertTrue(pagedList.size() > 0);
		
	}
	
	@Test
	public void findByTypeCodes() {
		
		String[] typeCodes = {"DOSF","INTU","QUAS"};
		
		PagedList pagedList = unitsDao.findByTypeCodes(typeCodes, LANG_FR);
		
		assertNotNull(pagedList);
		assertTrue(pagedList.size() > 0);
		
	}
	
	@Test
	public void findAllLanguageOrderedEnglish() {
		
		List<Units> units = unitsDao.findAllLanguageOrdered(Locale.ENGLISH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("100 milligrams/millilitre", units.get(0).getNameE());
		assertEquals("1/10 dilution", units.get(1).getNameE());
		assertEquals("Actinidin Units", units.get(2).getNameE());
		assertEquals("Alpha-amylase inhibiting units", units.get(3).getNameE());
		
	}
	
	@Test
	public void findAllLanguageOrderedFrench() {
		
		List<Units> units = unitsDao.findAllLanguageOrdered(Locale.CANADA_FRENCH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("100 milligrammes/millilitre", units.get(0).getNameF());
		assertEquals("Dilution 1/10", units.get(1).getNameF());
		assertEquals("Unit�s d'actinidine", units.get(2).getNameF());
		assertEquals("Unit�s d'Inhibition de l'Alpha-Amylase", units.get(3).getNameF());
		
	}

	@Test
	public void findByTypeCodeTypeLanguageOrderedEnglish() {
		
		List<Units> units = unitsDao.findByTypeCodeLanguageOrdered(UNITTYPE_TIME, Locale.ENGLISH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("Day", units.get(0).getNameE());
		assertEquals("Hour", units.get(1).getNameE());
		assertEquals("Minute", units.get(2).getNameE());
		assertEquals("Month", units.get(3).getNameE());
		
	}

	@Test
	public void findByTypeCodeTypeLanguageOrderedFrench() {
		
		List<Units> units = unitsDao.findByTypeCodeLanguageOrdered(UNITTYPE_TIME, Locale.CANADA_FRENCH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("Ann�e", units.get(0).getNameF());
		assertEquals("Heure", units.get(1).getNameF());
		assertEquals("Jour", units.get(2).getNameF());
		assertEquals("Minute", units.get(3).getNameF());
		
	}
	
	@Test
	public void findByTypeCodeMassLanguageOrderedEnglish() {
		
		List<Units> units = unitsDao.findByTypeCodeLanguageOrdered(UNITTYPE_MASS, Locale.ENGLISH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("Alpha-tocopherol", units.get(0).getNameE());
		assertEquals("Grams", units.get(1).getNameE());
		assertEquals("Grams/gram", units.get(2).getNameE());
		assertEquals("Grams/litre", units.get(3).getNameE());
		
	}

	@Test
	public void findByTypeCodeMassLanguageOrderedFrench() {
		
		List<Units> units = unitsDao.findByTypeCodeLanguageOrdered(UNITTYPE_MASS, Locale.CANADA_FRENCH.getISO3Language());
		
		assertNotNull(units);
		assertTrue(units.size() > 0);
		
		assertEquals("Alpha-tocoph�rol", units.get(0).getNameF());
		assertEquals("Equivalents d'activit� du r�tinol", units.get(1).getNameF());
		assertEquals("Grammes", units.get(2).getNameF());
		assertEquals("Grammes/gramme", units.get(3).getNameF());
		
	}


}
