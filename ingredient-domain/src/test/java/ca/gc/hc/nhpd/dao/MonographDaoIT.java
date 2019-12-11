package ca.gc.hc.nhpd.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.LogManager;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.MonographSearchResult;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.Monograph;
import ca.gc.hc.nhpd.model.ProvinceState;

/**
 * Integration test used to verify the CountryDao.
 */

public class MonographDaoIT extends AbstractDaoIT<Monograph> {

    @Autowired
    MonographDao monographDao;

    @Test
    public void findAllMonographs() {

        List<Monograph> monographs = monographDao.findAll();

        System.out.println(monographs.size());

        for (Monograph monograph : monographs) {

            System.out.println(
                    "id: " + monograph.getId() + ", " + monograph.getNameE());

        }

    }

    @Test
    public void findCountriesFrenchOrder() {

        List<MonographSearchResult> monographs =
                monographDao.listMonographs("en", false, false);

        System.out.println(monographs.size());

        for (MonographSearchResult monograph : monographs) {

            System.out.println("id: " + monograph.getMonographId() + ", "
                    + monograph.getMonographName());

        }

    }

    @Test
    public void findIngredientVitaminC() {

        SearchCriteria searchCriteria = new SearchCriteria();
        // searchCriteria.

        // List<IngredientSearchResult> ingredients =
        // ingredientDao.invokeCompendialSearch(searchCriteria, false, 100,
        // true, true, true, true);
        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(916L, "en");

        System.out.println(monographSearchResults.size());

        for (MonographSearchResult monographSearchResult : monographSearchResults) {
            System.out.println(monographSearchResult.getMonographName());
            Monograph monograph = (Monograph) monographDao
                    .findById(monographSearchResult.getMonographId(), false);
            System.out.println(monograph.toString());
        }

    }

    @Test
    public void getMonograph169() {

        Monograph monograph = (Monograph) monographDao.findById(169L, false);
        System.out.println(monograph.toString());
    }

    @Test
    public void getMonograph177() {

        System.setProperty("oracle.jdbc.Trace", Boolean.TRUE.toString());
        
        try {
            enableLogging(false);
        } catch (MalformedObjectNameException | AttributeNotFoundException
                | InstanceNotFoundException | InvalidAttributeValueException
                | NullPointerException | MBeanException | ReflectionException
                | SecurityException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Monograph monograph = (Monograph) monographDao.findById(177L, false);
        System.out.println(monograph.toString());
    }

    @Test
    public void getMonograph88() {

        Monograph monograph = (Monograph) monographDao.findById(88L, false);
        System.out.println(monograph.toString());
    }

    @Test
    public void getMonographsByIngredientId916EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(916L,
                        Locale.ENGLISH.getISO3Language());

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

        // for (MonographSearchResult monographSearchResult :
        // monographSearchResults) {
        //
        // System.out.println(monographSearchResult.getMonographName());
        //
        // }

        assertTrue(monographSearchResults.size() == 5);

    }

    @Test
    public void getMonographsByIngredientId3199EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(3199L,
                        Locale.ENGLISH.getISO3Language());

        assertEquals("3,3'-diindolylmethane (DIM) (Under consultation)",
                monographSearchResults.get(0).getMonographName());

        assertTrue(monographSearchResults.size() == 1);

    }

    @Test
    public void getMonographsByIngredientId5EN() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(5L,
                        Locale.ENGLISH.getISO3Language());

        assertEquals("Diaper Rash Products",
                monographSearchResults.get(0).getMonographName());
        assertEquals("Medicated Skin Care Products",
                monographSearchResults.get(1).getMonographName());

        assertTrue(monographSearchResults.size() == 2);

    }

    @Test
    public void getMonographsByIngredientId916FR() {

        List<MonographSearchResult> monographSearchResults =
                monographDao.getMonographsByIngredientId(916L,
                        Locale.CANADA_FRENCH.getISO3Language());

        // assertEquals("Antioxidants (Under consultation)",
        // monographSearchResults.get(0).getMonographName());
        // assertEquals("Joint Health Products, Multiple Ingredient",
        // monographSearchResults.get(1).getMonographName());
        // assertEquals("Multi-Vitamin/Mineral Supplements",
        // monographSearchResults.get(2).getMonographName());
        // assertEquals("Vitamin C",
        // monographSearchResults.get(3).getMonographName());
        // assertEquals("Workout Supplements",
        // monographSearchResults.get(4).getMonographName());

        // for (MonographSearchResult monographSearchResult :
        // monographSearchResults) {
        //
        // System.out.println(monographSearchResult.getMonographName());
        //
        // }

        assertTrue(monographSearchResults.size() == 5);

    }

    static private void enableLogging(boolean logDriver)
            throws MalformedObjectNameException, NullPointerException,
            AttributeNotFoundException, InstanceNotFoundException,
            MBeanException, ReflectionException, InvalidAttributeValueException,
            SecurityException, FileNotFoundException, IOException {
        oracle.jdbc.driver.OracleLog.setTrace(true);

        // compute the ObjectName
        String loader = Thread.currentThread().getContextClassLoader()
                .toString().replaceAll("[,=:\"]+", "");
        javax.management.ObjectName name = new javax.management.ObjectName(
                "com.oracle.jdbc:type=diagnosability,name=" + loader);

        // get the MBean server
        javax.management.MBeanServer mbs =
                java.lang.management.ManagementFactory.getPlatformMBeanServer();

        // find out if logging is enabled or not
        System.out.println(
                "LoggingEnabled = " + mbs.getAttribute(name, "LoggingEnabled"));

        // enable logging
        mbs.setAttribute(name,
                new javax.management.Attribute("LoggingEnabled", true));

        File propFile = new File("C:\\Users\\hcuser\\git\\ingredient-domain\\src\\test\\resources\\logging.properties");
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(new FileInputStream(propFile));

        if (logDriver) {
            DriverManager.setLogWriter(new PrintWriter(System.err));
        }
    }

}
