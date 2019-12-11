package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.model.ProvinceState;
import ca.gc.hc.nhpd.model.Units;

/**
 * Integration test used to verify the UnitsDao.
 */

public class ProvinceStateDaoIT extends AbstractDaoIT<Units> {

	private static final String COUNTRY_CODE_CA = "CA";
	
	private static final String COUNTRY_CODE_US = "US";
	
	private static final String LOCALE_FRENCH = Locale.CANADA_FRENCH.getISO3Language();
	
	private static final String LOCALE_ENGLISH = Locale.ENGLISH.getISO3Language();
	
	@Autowired
	ProvinceStateDao provinceStateDao;
	
	@Test
	public void findByCountryCodeCAFrench() {
		
		List<ProvinceState> provinceStates = provinceStateDao.findByCountryCode(COUNTRY_CODE_CA, LOCALE_FRENCH);
		
		assertEquals("Alberta", provinceStates.get(0).getNameF());
		assertEquals("Colombie-britannique", provinceStates.get(1).getNameF());
		assertEquals("Ile-du-prince-&eacute;douard", provinceStates.get(2).getNameF());
		assertEquals("Manitoba", provinceStates.get(3).getNameF());
		
	}

	@Test
	public void findByCountryCodeCAEnglish() {
		
		List<ProvinceState> provinceStates = provinceStateDao.findByCountryCode(COUNTRY_CODE_CA, LOCALE_ENGLISH);
		
		assertEquals("Alberta", provinceStates.get(0).getNameE());
		assertEquals("British Columbia", provinceStates.get(1).getNameE());
		assertEquals("Manitoba", provinceStates.get(2).getNameE());
		assertEquals("New Brunswick", provinceStates.get(3).getNameE());
		
	}

	@Test
	public void findByCountryCodeUSFrench() {
		
		List<ProvinceState> provinceStates = provinceStateDao.findByCountryCode(COUNTRY_CODE_US, LOCALE_FRENCH);
		
		assertEquals("Alabama", provinceStates.get(0).getNameF());
		assertEquals("Alaska", provinceStates.get(1).getNameF());
		assertEquals("Arizona", provinceStates.get(2).getNameF());
		assertEquals("Arkansas", provinceStates.get(3).getNameF());
		assertEquals("Californie", provinceStates.get(4).getNameF());
		
	}

	@Test
	public void findByCountryCodeUSEnglish() {
		
		List<ProvinceState> provinceStates = provinceStateDao.findByCountryCode(COUNTRY_CODE_US, LOCALE_ENGLISH);
		
		assertEquals("Alabama", provinceStates.get(0).getNameE());
		assertEquals("Alaska", provinceStates.get(1).getNameE());
		assertEquals("Arizona", provinceStates.get(2).getNameE());
		assertEquals("Arkansas", provinceStates.get(3).getNameE());
		assertEquals("California", provinceStates.get(4).getNameE());
		
	}

}
