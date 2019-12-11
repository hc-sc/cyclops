package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.ConfigurationIT;
import ca.gc.hc.nhpd.dto.MonographSearchResult;

/**
 * Integration test used to verify the MonographDao getMonographsByIngredientId function.
 * 
 * TODO - Fix the French when a properly configured French data source exists.
 */

public class MonographDaoGetMonographsByIngredientIdIT extends ConfigurationIT {

    @Autowired
    MonographDao monographDao;

    /**
     * Folium Ginkgo - 16010
     */
    @Test
    public void getMonographsByIngredientId16010() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(16010L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 1);
        assertEquals("Traditional Chinese Medicine Ingredients",
                monographSearchResults.get(0).getMonographName());

    }

    /**
     * Ginkgo biloba - 6171
     */
    @Test
    public void getMonographsByIngredientId6171() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(6171L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 2);
        assertEquals("Cognitive function products (Under consultation)",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Ginkgo Biloba",
                monographSearchResults.get(1).getMonographName());
        
    }

    /**
     * Iron (II) gluconate - 16275
     */
    @Test
    public void getMonographsByIngredientId16275() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(16275L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 0);

    }

    /**
     * Calamine - 2849
     */
    @Test
    public void getMonographsByIngredientId2849() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(2849L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 2);
        assertEquals("Diaper Rash Products",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Medicated Skin Care Products",
                monographSearchResults.get(1).getMonographName());

        
    }

    /**
     * Beta-carotene - 1048
     */
    @Test
    public void getMonographsByIngredientId1048() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(1048L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 4);
        assertEquals("Beta-Carotene",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Joint Health Products, Multiple Ingredient",
                monographSearchResults.get(1).getMonographName());
        assertEquals("Multi-Vitamin/Mineral Supplements",
                monographSearchResults.get(2).getMonographName());
        assertEquals("Workout Supplements",
                monographSearchResults.get(3).getMonographName());

    }

    /**
     * Iron (II) Sulphate - 16277
     */
    @Test
    public void getMonographsByIngredientId16277() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(16277L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 0);

    }

    /**
     * Lactobacillus johnsonii La1 - 12135
     */
    @Test
    public void getMonographsByIngredientId12135() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(12135L, Locale.ENGLISH.getISO3Language());

        displayMonographSearchResult(monographSearchResults);
        
        assertTrue(monographSearchResults.size() == 1);
        assertEquals("Probiotics",
                monographSearchResults.get(0).getMonographName());

    }
    
    /**
     * Checks that the (AC) is not in the list.
     */
    @Test
    public void getMonographsByIngredientId916EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(916L, Locale.ENGLISH.getISO3Language());

        assertEquals("Antioxidants (Under consultation)",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Joint Health Products, Multiple Ingredient",
                monographSearchResults.get(1).getMonographName());
        assertEquals("Multi-Vitamin/Mineral Supplements",
                monographSearchResults.get(2).getMonographName());
        assertEquals("Vitamin C",
                monographSearchResults.get(3).getMonographName());
        assertEquals("Workout Supplements",
                monographSearchResults.get(4).getMonographName());

        assertTrue(monographSearchResults.size() == 5);

    }

    /**
     * Checks that the (AC) is not in the list.
     */
    @Test
    public void getMonographsByIngredientId3199EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(3199L, Locale.ENGLISH.getISO3Language());

        assertEquals("3,3'-diindolylmethane (DIM) (Under consultation)",
                monographSearchResults.get(0).getMonographName());

        assertTrue(monographSearchResults.size() == 1);

    }

    /**
     * Checks that the "(AC)" and "- NMI Safety" are not in the list.
     */
    @Test
    public void getMonographsByIngredientId5EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(5L, Locale.ENGLISH.getISO3Language());

        assertEquals("Diaper Rash Products",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Medicated Skin Care Products",
                monographSearchResults.get(1).getMonographName());

        assertTrue(monographSearchResults.size() == 2);

    }

    
    @Test
    public void getMonographsByIngredientId916FR() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(916L, Locale.CANADA_FRENCH.getISO3Language());

        for (MonographSearchResult monographSearchResult : monographSearchResults) {

            System.out.println(monographSearchResult.getMonographName());

        }

        System.out.println(monographSearchResults.size());
        
        assertTrue(monographSearchResults.size() == 5);

    }
    
    @Test
    public void emptyListCheck() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(null, Locale.CANADA_FRENCH.getISO3Language());

        assertNotNull(monographSearchResults);
        assertTrue(monographSearchResults.size() == 0);

    }
    
    @Test
    public void nonExistantIngredientCheck() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(-1L, Locale.CANADA_FRENCH.getISO3Language());

        assertNotNull(monographSearchResults);
        assertTrue(monographSearchResults.size() == 0);

    }

    @Test
    public void defaultEnglishLanguageCheck() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(5L, "BLA!");

        assertEquals("Diaper Rash Products",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Medicated Skin Care Products",
                monographSearchResults.get(1).getMonographName());

        assertTrue(monographSearchResults.size() == 2);

    }
    
    private void displayMonographSearchResult(List<MonographSearchResult> monographSearchResult) {
        
        System.out.println("size: " + monographSearchResult.size());
        for (MonographSearchResult monograph : monographSearchResult) {
            System.out.println(monograph.getMonographName());
        }
        
    }

}
