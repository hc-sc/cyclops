package ca.gc.unit.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.gc.hc.ingrdient.wsclient.consumer.IngredientWSClientConsumerInterFace;
import ca.gc.hc.ingrdient.wsclient.consumer.IngredientwsConsumerMain;
import ca.gc.hc.webservice.IngredientIdWS;
import ca.gc.hc.webservice.IngredientWS;
import ca.gc.hc.webservice.MonographIdWS;
import ca.gc.hc.webservice.MonographSearchCriteriaWS;
import ca.gc.hc.webservice.MonographSearchResultWS;
import ca.gc.hc.webservice.MonographWS;
import ca.gc.hc.webservice.NhpClassificationWS;
import ca.gc.hc.webservice.SearchCriterion;

public class TestIngredientById {

	// generic search text param - searches multiple columns
	public static final String NAMESTRING = "NAMESTRING";
	public static final String DISPLAY_ABLS = "DISPLAY_ABLS";
	public static final String DISPLAY_ABLS_INTERNAL = "DISPLAY_ABLS_INTERNAL";
	public static final String DISPLAY_PRODUCTS = "DISPLAY_PRODUCTS";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		// IngredientwsConsumerMain("http://localhost:9080/IngredientWS/IngredientService?wsdl");

		IngredientWSClientConsumerInterFace consumer = new IngredientwsConsumerMain(
				"https://nnhpddevdemo.canadaeast.cloudapp.azure.com/IngredientAndMonographWS/IngredientService?wsdl",
				"en");
		MonographSearchCriteriaWS monocriteria = new MonographSearchCriteriaWS();
		List<SearchCriterion> criteria_list = new ArrayList<SearchCriterion>(1);
		SearchCriterion criteria = new SearchCriterion();
		criteria.setAttributeName(DISPLAY_PRODUCTS);
		criteria.setAttributeValue("false");
		criteria.setOperator(0);
		criteria_list.add(criteria);
		monocriteria.getCriteria().add(criteria);
		try {
			if (consumer.initialize()) {
				List<MonographSearchResultWS> returnObj = consumer.searchMonographs(monocriteria);
				for (MonographSearchResultWS searchElement : returnObj) {

					MonographIdWS monoId = new MonographIdWS();
					monoId.setId(searchElement.getId());
					if (monoId.getId() != 182)
						continue;
					MonographWS monorecord = consumer.getMonographById(monoId);
					List<String> commonNames = monorecord.getCommonNames();
					List<String> properNames = monorecord.getProperNames();
					List<String> sourceMaterial = monorecord.getMonographSourceMaterials();

					System.out.println("Common names ...");
					for (String cn : commonNames) {

						System.out.println(cn);
					}
					System.out.println("Proper names ...");
					for (String cn : properNames) {

						System.out.println(cn);
					}

					System.out.println("source material names ...");
					for (String cn : sourceMaterial) {

						System.out.println(cn);
					}

				}
			} else {
				System.out.println("service initialize fail");
			}

		} catch (Throwable te) {

			System.out.println("service initialize exception");
		}

	}

	@Test
	public void testFR() {

		String testMessage = "French";
		String textExpected = "Une plante est un membre du r¨¨gne biologique v¨¦g¨¦tal, peu importe qu'il s'agisse de la plante enti¨¨re ou des parties la composant.  Une mati¨¨re v¨¦g¨¦tale est une mati¨¨re qui provient d'une plante, y compris, les pollens, les mati¨¨res nucl¨¦iques, les mitochondries, la chlorophylle et les exsudats, tels que la r¨¦sine.";

		boolean textFound = false;

		IngredientWSClientConsumerInterFace consumer = new IngredientwsConsumerMain(
				// "https://nnhpddevdemo.canadaeast.cloudapp.azure.com/IngredientAndMonographWS/IngredientService?wsdl"
				"http://10.148.181.248:8080/IngredientAndMonographWS/IngredientService?wsdl", "fr");

		IngredientWS ingredientWS = null;
		try {
			if (consumer.initialize()) {
				IngredientIdWS ingredientIdWS = new IngredientIdWS();
				ingredientIdWS.setId(11322L);
				ingredientWS = consumer.getIngredientById(ingredientIdWS, false);
				List<NhpClassificationWS> nhpClassifications = ingredientWS.getNhpClassifications();
				Iterator<NhpClassificationWS> nhpClassificationsIterator = nhpClassifications.iterator();
				while (nhpClassificationsIterator.hasNext()) {
					NhpClassificationWS tmpNhpClassification = nhpClassificationsIterator.next();
					if (textExpected.equals(tmpNhpClassification.getDescription())) {
						textFound = true;
					}
				}
			}

		} catch (Throwable te) {
			System.out.println("service initialize fail");
		} finally {
			assertTrue(testMessage, textFound);
		}

	}

	@Test
	public void testEN() {

		String testMessage = "English";
		String textExpected = "A plant is a member of the biological Kingdom Plantae, and is either the whole plant or parts thereof. A plant material is material obtained from a plant, including pollens, nucleic materials, mitochondria, chlorophyll and exudates such as resin.";

		boolean textFound = false;

		IngredientWSClientConsumerInterFace consumer = new IngredientwsConsumerMain(
				// "https://nnhpddevdemo.canadaeast.cloudapp.azure.com/IngredientAndMonographWS/IngredientService?wsdl"
				"http://10.148.181.248:8080/IngredientAndMonographWS/IngredientService?wsdl", "en");

		IngredientWS ingredientWS = null;
		try {
			if (consumer.initialize()) {
				IngredientIdWS ingredientIdWS = new IngredientIdWS();
				ingredientIdWS.setId(11322L);
				ingredientWS = consumer.getIngredientById(ingredientIdWS, false);
				List<NhpClassificationWS> nhpClassifications = ingredientWS.getNhpClassifications();
				Iterator<NhpClassificationWS> nhpClassificationsIterator = nhpClassifications.iterator();
				while (nhpClassificationsIterator.hasNext()) {
					NhpClassificationWS tmpNhpClassification = nhpClassificationsIterator.next();
					if (textExpected.equals(tmpNhpClassification.getDescription())) {
						textFound = true;
					}
				}
			}

		} catch (Throwable te) {
			System.out.println("service initialize fail");
		} finally {
			assertTrue(testMessage, textFound);
		}

	}

}
