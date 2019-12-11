package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.ProvinceState;

/**
 * Integration test used to verify the CountryDao.
 */

public class CountryDaoIT extends AbstractDaoIT<Country> {

	@Test
	public void findCountriesEnglishOrder() {
		
		CountryDao countryDao = (CountryDao) dao;
		
		List<Country> countries = countryDao.findAllLanguageOrdered(LANG_EN);
		
		assertNotNull(countries);
		assertTrue(countries.size() > 0);
		
		assertTrue(countries.get(0).getCode().equals("CA"));
		assertTrue(countries.get(1).getCode().equals("US"));
		
		
	}

	@Test
	public void findCountriesFrenchOrder() {
		
		CountryDao countryDao = (CountryDao) dao;
		
		List<Country> countries = countryDao.findAllLanguageOrdered(LANG_FR);
		
		assertNotNull(countries);
		assertTrue(countries.size() > 0);
		
		assertTrue(countries.get(0).getCode().equals("CA"));
		assertTrue(countries.get(1).getCode().equals("US"));
		
		
	}
	
	
	@Test
	public void findCountriesEnglishRepeat() {
		
		CountryDao countryDao = (CountryDao) dao;
		
		List<Country> countries = countryDao.findAllLanguageOrdered(LANG_EN);
		
		System.out.println(countries);
		
		countries = countryDao.findAllLanguageOrdered(LANG_EN);
		
		System.out.println(countries);
		
		countries = countryDao.findAllLanguageOrdered(LANG_EN);
		
		System.out.println(countries);
		
		countries = countryDao.findAllLanguageOrdered(LANG_EN);
		
		System.out.println(countries);
		
		
	}

	@Test
	public void findCountryByCode() {
		
		CountryDao countryDao = (CountryDao) dao;
		
		Country canada = countryDao.findByCode("CA", LANG_EN);
		
		assertNotNull(canada);
		assertNotNull(canada.getProvincesStates());
		
		Set<ProvinceState> provincesStates = canada.getProvincesStates();
		
		assertTrue(provincesStates.size() > 0);
		
		
	}
	
	
	
}
