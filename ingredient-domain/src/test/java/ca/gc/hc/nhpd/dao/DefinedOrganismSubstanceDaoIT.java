package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;

/**
 * Integration test used to verify the CountryDao.
 */

public class DefinedOrganismSubstanceDaoIT extends ConfigurationIT {

    @Autowired
    DefinedOrganismSubstanceDao definedOrganismSubstanceDao;

    @Test
    public void validProbioticTests() {

        assertTrue(isProbioticTest(12135L));
        assertTrue(isProbioticTest(12136L));
        assertTrue(isProbioticTest(14868L));
        assertTrue(isProbioticTest(13311L));

    }

    @Test
    public void notValidProbioticTests() {

        assertFalse(isProbioticTest(1000L));
        assertFalse(isProbioticTest(1001L));
        assertFalse(isProbioticTest(1003L));
        assertFalse(isProbioticTest(1005L));

    }

    @Test
    public void nullTest() {

        assertNull(isProbioticTest(null));

    }

    private Boolean isProbioticTest(Long ingredientId) {

        return definedOrganismSubstanceDao.isProbiotic(ingredientId);

    }

}
