package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;
import ca.gc.hc.nhpd.util.Utils;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;
import ca.gc.hc.nhpd.conf.SpringContext;
import ca.gc.hc.nhpd.dto.PersistentObjectSearchResult;
import ca.gc.hc.nhpd.exception.IngredientsException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/*******************************************************************************
 * Loosely based on org.hibernate.ce.auction.dao.hibernate.GenericHibernateDAO,
 * a class in the auction example at http://caveatemptor.hibernate.org.
 * Converted from Java 1.5 and adjusted to use HibernateUtil directly as a
 * source of Sessions rather than keep them in an instance variable. This allows
 * instances to be thread-safe and therefore re-usable rather than requiring
 * them to be instantiated and subsequently discarded for each request. -Dwight
 * Hubley, 2006-04-12<br>
 * <br>
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * 
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent
 * class. Of course, assuming that you have a traditional 1:1 appraoch for
 * Entity:DAO design.
 * 
 * <p>
 * You have to inject the <tt>Class</tt> object of the persistent class to
 * construct a DAO.<br>
 * <br>
 * Updated to support the following new features: 1 - LOAD_INTO_MEMORY flag
 * indicator telling ApplicationGlobals to load the Dao object in question into
 * memory. By default, the AbstractDao is set to false, and individual child Dao
 * objects can have their own settings. 2 - MODELCLASSNAMES[] Array of all the
 * modelClassNames used within the system. As new Dao are added, entries should
 * be added as static final in the format of <modelClassName>_MODELCLASSNAME,
 * and also added to the MODELCLASSNAME. 3 - The static initiator creates into
 * memory a Map (daoObjects) of all Dao objects and is accessible through the
 * getInstanceOf( <modelClassName> ).
 * 
 * @author christian.bauer@jboss.com
 */
// @Configuration
abstract public class AbstractDao {

	// @Autowired
	// ApplicationContext applicationContext;

	// ~ Static fields/initializers
	// ---------------------------------------------

	private static final Logger log = Logger.getLogger(AbstractDao.class);

	protected static final String EMPTY_STRING = "";
	protected static final String SPACE = " ";
	protected static final String DASH = "-";
	protected static final int DEFAULT_MAX_ROWS = 100000;

	static public boolean LOAD_INTO_MEMORY = false;

	static public final String CREATION_DATE = "creationDate";
	static public final String LAST_UPDATE_ACCOUNT = "lastUpdateAccount";
	static public final String LAST_UPDATE_DATE = "lastUpdateDate";
	static public final String ID = "id";
	static public final String DELETE_ACTION = "delete";
	static public final String PERIOD = ".";
	static public final String SUCCESS = "success";
	static public final String CONSTRAINT_VIOLATION = "constraint_violation";
	static public final String HIBERNATE_EXCEPTION = "hibernate_exception";
	static public final String EXCEPTION = "exception";
	static public final String NO_OBJECT_FOUND = "no_object_found";
	static public final String APPLICATIONTEXT_MODELCLASSNAME = "ApplicationText"; // Request
	static public final String APPLICATIONTYPE_MODELCLASSNAME = "ApplicationType"; // Request
	static public final String ATTACHMENT_MODELCLASSNAME = "Attachment"; // Request
	static public final String AUDITENTRY_MODELCLASSNAME = "AuditEntry";
	static public final String BASICIMPURITYSUBSTANCE_MODELCLASSNAME = "BasicImpuritySubstance"; // Request
	static public final String BASICRESTRICTION_MODELCLASSNAME = "BasicRestriction"; // Request
	static public final String BOOK_MODELCLASSNAME = "Book"; // Request
	static public final String BOOKCHAPTER_MODELCLASSNAME = "BookChapter"; // Request
	static public final String CHEMICALCLASS_MODELCLASSNAME = "ChemicalClass"; // Request
	static public final String CHEMICALIMPURITY_MODELCLASSNAME = "ChemicalImpurity"; // Request
	static public final String CHEMICALIMPURITYSUBSTANCE_MODELCLASSNAME = "ChemicalImpuritySubstance"; // Request
	static public final String CHEMICALMONOGRAPHENTRY_MODELCLASSNAME = "ChemicalMonographEntry"; // Request
	static public final String CHEMICALSUBCLASS_MODELCLASSNAME = "ChemicalSubclass"; // Request
	static public final String CHEMICALSUBSTANCE_MODELCLASSNAME = "ChemicalSubstance"; // Request
	static public final String CITATION_MODELCLASSNAME = "Citation"; // Request
	static public final String CITATIONSPECIALIZATION_MODELCLASSNAME = "CitationSpecialization"; // Request
	static public final String COMMONTERM_MODELCLASSNAME = "CommonTerm"; // Request
	static public final String COMMONTERMTYPE_MODELCLASSNAME = "CommonTermType"; // Request
	static public final String COMPONENTROLE_MODELCLASSNAME = "ComponentRole"; // Request
	static public final String CONFERENCEPROCEEDINGS_MODELCLASSNAME = "ConferenceProceedings"; // Request
	static public final String COUNTRY_MODELCLASSNAME = "Country"; // Request
	static public final String CUSTOMORGANISMSUBSTANCE_MODELCLASSNAME = "CustomOrganismSubstance"; // Request
	static public final String DEFINEDORGANISMSUBSTANCE_MODELCLASSNAME = "DefinedOrganismSubstance"; // Request
	static public final String DOSAGEFORM_MODELCLASSNAME = "DosageForm"; // Request
	static public final String DOSAGEFORMGROUP_MODELCLASSNAME = "DosageFormGroup"; // Request
	static public final String EVIDENCETYPE_MODELCLASSNAME = "EvidenceType"; // Request
	static public final String FAMILY_MODELCLASSNAME = "Family"; // Request
	static public final String GENERICTEXT_MODELCLASSNAME = "Genus"; // Request
	static public final String GENUS_MODELCLASSNAME = "Genus"; // Request
	static public final String GEOGRAPHICAREA_MODELCLASSNAME = "GeographicArea"; // Request
	static public final String HOMEOPATHICROLE_MODELCLASSNAME = "HomeopathicRole"; // Request
	static public final String HOMEOPATHICSUBSTANCE_MODELCLASSNAME = "HomeopathicSubstance"; // Request
	static public final String ICHCLASS_MODELCLASSNAME = "IchClass"; // Request
	static public final String IMPURITYSUBSTANCE_MODELCLASSNAME = "ImpuritySubstance"; // Request
	static public final String INGREDIENT_MODELCLASSNAME = "Ingredient"; // Request
	static public final String INGREDIENTCATEGORY_MODELCLASSNAME = "IngredientCategory"; // Request
	static public final String INGREDIENTROLE_MODELCLASSNAME = "IngredientRole"; // Request
	static public final String INGREDIENTROLESPECIALIZATION_MODELCLASSNAME = "IngredientRoleSpecialization"; // Request
	static public final String INGREDIENTSOURCE_MODELCLASSNAME = "IngredientSource"; // Request
	static public final String INGREDIENTSPECIALIZATION_MODELCLASSNAME = "IngredientSpecialization"; // Request
	static public final String JOURNAL_MODELCLASSNAME = "Journal"; // Request
	static public final String JOURNALARTICLE_MODELCLASSNAME = "JournalArticle"; // Request
	static public final String MANUFACTURINGPROCESS_MODELCLASSNAME = "ManufacturingProcess"; // Request
	static public final String MEDDRAHIGHLEVELGROUPTERM_MODELCLASSNAME = "MeddraHighLevelGroupTerm"; // Request
	static public final String MEDDRAHIGHLEVELTERM_MODELCLASSNAME = "MeddraHighLevelTerm"; // Request
	static public final String MEDDRALOWESTLEVELTERM_MODELCLASSNAME = "MeddraLowestLevelTerm"; // Request
	static public final String MEDDRAPREFERREDTERM_MODELCLASSNAME = "MeddraPreferredTerm"; // Request
	static public final String MEDDRASYSTEMORGANCLASS_MODELCLASSNAME = "MeddraSystemOrganClass"; // Request
	static public final String MEDICINALRESTRICTION_MODELCLASSNAME = "MedicinalRestriction"; // Request
	static public final String MEDICINALROLE_MODELCLASSNAME = "MedicinalRole"; // Request
	static public final String MICROBIOLOGICALIMPURITY_MODELCLASSNAME = "MicrobiologicalImpurity"; // Request
	static public final String MONOGRAPH_MODELCLASSNAME = "Monograph"; // Request
	static public final String MONOGRAPHENTRY_MODELCLASSNAME = "MonographEntry"; // Request
	static public final String MONOGRAPHRISKSTATEMENT_MODELCLASSNAME = "MonographRiskStatement"; // Request
	static public final String MONOGRAPHROUTEOFADMINISTRATION_MODELCLASSNAME = "MonographRouteOfAdminstration"; // Request
	static public final String MONOGRAPHUSE_MODELCLASSNAME = "MonographUse"; // Request
	static public final String NAMED_ORGANISM_GROUP_MODELCLASSNAME = "NamedOrganismGroup"; // Request
	static public final String NHPCLASSIFICATION_MODELCLASSNAME = "NhpClassification"; // Request
	static public final String NONMEDICINALPURPOSE_MODELCLASSNAME = "NonMedicinalPurpose"; // Request
	static public final String NONMEDICINALRESTRICTION_MODELCLASSNAME = "NonMedicinalRestriction"; // Request
	static public final String NONMEDICINALROLE_MODELCLASSNAME = "NonMedicinalRole"; // Request
	static public final String NONNHPROLE_MODELCLASSNAME = "NonNHPRole"; // Request
	static public final String ORGANISM_MODELCLASSNAME = "Organism"; // Request
	static public final String ORGANISMGROUP_MODELCLASSNAME = "OrganismGroup"; // Request
	static public final String ORGANISMMONOGRAPHENTRY_MODELCLASSNAME = "OrganismMonographEntry"; // Request
	static public final String ORGANISMPART_MODELCLASSNAME = "OrganismPart"; // Request
	static public final String ORGANISMPARTTYPE_MODELCLASSNAME = "OrganismPartType"; // Request
	static public final String ORGANISMTYPE_MODELCLASSNAME = "OrganismType"; // Request
	static public final String ORGANISMTYPEGROUP_MODELCLASSNAME = "OrganismTypeGroup"; // Request
	static public final String PREPARATIONRULE_MODELCLASSNAME = "PreparationRule"; // Request
	static public final String PREPARATIONTYPE_MODELCLASSNAME = "PreparationType"; // Request
	static public final String PREPARATIONTYPEGROUP_MODELCLASSNAME = "PreparationTypeGroup"; // Request
	static public final String PERMISSION_MODELCLASSNAME = "Permission"; // Request
	static public final String PERSISTENTOBJECT_MODELCLASSNAME = "PersistentObject"; // Request
	static public final String PRODUCTTYPE_MODELCLASSNAME = "ProductType"; // Request
	static public final String PUBLISHEDWORK_MODELCLASSNAME = "PublishedWork"; // Request
	static public final String QUALIFIEDSYNONYM_MODELCLASSNAME = "QualifiedSynonym"; // Request
	static public final String QUANTITYTOLERANCEMODEL_MODELCLASSNAME = "QuantityToleranceModel"; // Request
	static public final String RATIONALE_MODELCLASSNAME = "Rationale"; // Request
	static public final String REFERENCE_MODELCLASSNAME = "Reference"; // Request
	static public final String REGISTRYENTRY_MODELCLASSNAME = "RegistryEntry"; // Request
	static public final String REPORT_MODELCLASSNAME = "Report"; // Request
	static public final String RESTRICTION_MODELCLASSNAME = "Restriction"; // Request
	static public final String RESTRICTIONTYPE_MODELCLASSNAME = "RestrictionType"; // Request
	static public final String RISK_MODELCLASSNAME = "Risk"; // Request
	static public final String RISKTYPE_MODELCLASSNAME = "RiskType"; // Request
	static public final String ROUTEOFADMINISTRATION_MODELCLASSNAME = "RouteOfAdministration"; // Request
	static public final String SIMPLEORGANISMGROUP_MODELCLASSNAME = "SimpleOrganismGroup"; // Request
	static public final String SPECIES_MODELCLASSNAME = "Species"; // Request
	static public final String SUBINGREDIENT_MODELCLASSNAME = "SubIngredient"; // Request
	static public final String SUBPOPULATION_MODELCLASSNAME = "SubPopulation"; // Request
	static public final String SUBSPECIES_MODELCLASSNAME = "SubSpecies"; // Request
	static public final String SUBTAXA_MODELCLASSNAME = "SubTaxa"; // Request
	static public final String SYNONYM_MODELCLASSNAME = "Synonym"; // Request
	static public final String TAXON_MODELCLASSNAME = "Taxon"; // Request
	static public final String TESTCATEGORY_MODELCLASSNAME = "TestCategory"; // Request
	static public final String TESTMETHOD_MODELCLASSNAME = "TestMethod"; // Request
	static public final String TESTSPECIFICATION_MODELCLASSNAME = "TestSpecification"; // Request
	static public final String THESES_MODELCLASSNAME = "Theses"; // Request
	static public final String UNITS_MODELCLASSNAME = "Units"; // Request
	static public final String UNPUBLISHEDWORK_MODELCLASSNAME = "UnpublishedWork"; // Request
	static public final String UNITSTYPE_MODELCLASSNAME = "UnitsType"; // Request
	static public final String USERACCOUNT_MODELCLASSNAME = "UserAccount"; // Request
	static public final String USERROLE_MODELCLASSNAME = "UserRole"; // Request
	static public final String USETYPE_MODELCLASSNAME = "UseType"; // Request
	static public final String WEBSITE_MODELCLASSNAME = "Website"; // Request

	static public final String[] MODELCLASSNAMES = { APPLICATIONTEXT_MODELCLASSNAME, APPLICATIONTYPE_MODELCLASSNAME,
			ATTACHMENT_MODELCLASSNAME, AUDITENTRY_MODELCLASSNAME, BASICIMPURITYSUBSTANCE_MODELCLASSNAME,
			BASICRESTRICTION_MODELCLASSNAME, BOOK_MODELCLASSNAME, BOOKCHAPTER_MODELCLASSNAME,
			CHEMICALCLASS_MODELCLASSNAME, CHEMICALIMPURITY_MODELCLASSNAME, CHEMICALIMPURITYSUBSTANCE_MODELCLASSNAME,
			CHEMICALMONOGRAPHENTRY_MODELCLASSNAME, CHEMICALSUBCLASS_MODELCLASSNAME, CHEMICALSUBSTANCE_MODELCLASSNAME,
			CITATION_MODELCLASSNAME, CITATIONSPECIALIZATION_MODELCLASSNAME, COMMONTERM_MODELCLASSNAME,
			COMMONTERMTYPE_MODELCLASSNAME, COMPONENTROLE_MODELCLASSNAME, CONFERENCEPROCEEDINGS_MODELCLASSNAME,
			COUNTRY_MODELCLASSNAME, CUSTOMORGANISMSUBSTANCE_MODELCLASSNAME, DEFINEDORGANISMSUBSTANCE_MODELCLASSNAME,
			DOSAGEFORM_MODELCLASSNAME, DOSAGEFORMGROUP_MODELCLASSNAME, EVIDENCETYPE_MODELCLASSNAME,
			FAMILY_MODELCLASSNAME, GENERICTEXT_MODELCLASSNAME, GENUS_MODELCLASSNAME, GEOGRAPHICAREA_MODELCLASSNAME,
			HOMEOPATHICROLE_MODELCLASSNAME, HOMEOPATHICSUBSTANCE_MODELCLASSNAME, ICHCLASS_MODELCLASSNAME,
			IMPURITYSUBSTANCE_MODELCLASSNAME, INGREDIENT_MODELCLASSNAME, INGREDIENTCATEGORY_MODELCLASSNAME,
			INGREDIENTROLE_MODELCLASSNAME, INGREDIENTROLESPECIALIZATION_MODELCLASSNAME, INGREDIENTSOURCE_MODELCLASSNAME,
			INGREDIENTSPECIALIZATION_MODELCLASSNAME, JOURNAL_MODELCLASSNAME, JOURNALARTICLE_MODELCLASSNAME,
			MANUFACTURINGPROCESS_MODELCLASSNAME, MEDDRAHIGHLEVELGROUPTERM_MODELCLASSNAME,
			MEDDRAHIGHLEVELTERM_MODELCLASSNAME, MEDDRALOWESTLEVELTERM_MODELCLASSNAME,
			MEDDRAPREFERREDTERM_MODELCLASSNAME, MEDDRASYSTEMORGANCLASS_MODELCLASSNAME,
			MEDICINALRESTRICTION_MODELCLASSNAME, MEDICINALROLE_MODELCLASSNAME, MICROBIOLOGICALIMPURITY_MODELCLASSNAME,
			MONOGRAPH_MODELCLASSNAME, MONOGRAPHENTRY_MODELCLASSNAME, MONOGRAPHRISKSTATEMENT_MODELCLASSNAME,
			MONOGRAPHROUTEOFADMINISTRATION_MODELCLASSNAME, MONOGRAPHUSE_MODELCLASSNAME,
			NHPCLASSIFICATION_MODELCLASSNAME, NAMED_ORGANISM_GROUP_MODELCLASSNAME, NONNHPROLE_MODELCLASSNAME,
			NONMEDICINALPURPOSE_MODELCLASSNAME, NONMEDICINALRESTRICTION_MODELCLASSNAME, NONMEDICINALROLE_MODELCLASSNAME,
			ORGANISM_MODELCLASSNAME, ORGANISMGROUP_MODELCLASSNAME, ORGANISMMONOGRAPHENTRY_MODELCLASSNAME,
			ORGANISMPARTTYPE_MODELCLASSNAME, ORGANISMTYPE_MODELCLASSNAME, ORGANISMTYPEGROUP_MODELCLASSNAME,
			PERMISSION_MODELCLASSNAME, PREPARATIONRULE_MODELCLASSNAME, PREPARATIONTYPE_MODELCLASSNAME,
			PREPARATIONTYPEGROUP_MODELCLASSNAME, PERSISTENTOBJECT_MODELCLASSNAME, PRODUCTTYPE_MODELCLASSNAME,
			PUBLISHEDWORK_MODELCLASSNAME, QUALIFIEDSYNONYM_MODELCLASSNAME, QUANTITYTOLERANCEMODEL_MODELCLASSNAME,
			RATIONALE_MODELCLASSNAME, REFERENCE_MODELCLASSNAME, REGISTRYENTRY_MODELCLASSNAME, REPORT_MODELCLASSNAME,
			RESTRICTION_MODELCLASSNAME, RESTRICTIONTYPE_MODELCLASSNAME, RISK_MODELCLASSNAME, RISKTYPE_MODELCLASSNAME,
			ROUTEOFADMINISTRATION_MODELCLASSNAME, SIMPLEORGANISMGROUP_MODELCLASSNAME, SPECIES_MODELCLASSNAME,
			SUBINGREDIENT_MODELCLASSNAME, SUBPOPULATION_MODELCLASSNAME, SUBSPECIES_MODELCLASSNAME,
			SUBTAXA_MODELCLASSNAME, SYNONYM_MODELCLASSNAME, TAXON_MODELCLASSNAME, TESTCATEGORY_MODELCLASSNAME,
			TESTMETHOD_MODELCLASSNAME, TESTSPECIFICATION_MODELCLASSNAME, THESES_MODELCLASSNAME, UNITS_MODELCLASSNAME,
			UNITSTYPE_MODELCLASSNAME, UNPUBLISHEDWORK_MODELCLASSNAME, USERACCOUNT_MODELCLASSNAME,
			USERROLE_MODELCLASSNAME, USETYPE_MODELCLASSNAME, WEBSITE_MODELCLASSNAME };

	static public final String ERROR_DUPLICATE_RECORD = "duplicateRecord";

	static public final String ERROR_GENERIC = "genericError";

	static public final String ERROR_REQUIRED_FIELD = "requiredField";

	static private HashMap daoObjects = new HashMap();

	/**
	 * Static method that prepopulates the daoObjects map with an instance of each
	 * of the DAO accessors, using the modelClassName as key.
	 */
	static {
		addToDaoObjects(new ApplicationTextDao());
		addToDaoObjects(new ApplicationTypeDao());
		addToDaoObjects(new AttachmentDao());
		addToDaoObjects(new AuditEntryDao());
		addToDaoObjects(new BasicImpuritySubstanceDao());
		addToDaoObjects(new BookDao());
		addToDaoObjects(new BookChapterDao());
		addToDaoObjects(new RegistryEntryDao());
		addToDaoObjects(new ChemicalClassDao());
		addToDaoObjects(new ChemicalImpurityDao());
		addToDaoObjects(new ChemicalSubstanceDao());
		addToDaoObjects(new ChemicalMonographEntryDao());
		addToDaoObjects(new ChemicalSubclassDao());
		addToDaoObjects(new CitationDao());
		addToDaoObjects(new CitationSpecializationDao());
		addToDaoObjects(new CommonTermDao());
		addToDaoObjects(new CommonTermTypeDao());
		addToDaoObjects(new ComponentRoleDao());
		addToDaoObjects(new ConferenceProceedingsDao());
		addToDaoObjects(new CountryDao());
		addToDaoObjects(new CustomOrganismSubstanceDao());
		addToDaoObjects(new DefinedOrganismSubstanceDao());
		addToDaoObjects(new DosageFormDao());
		addToDaoObjects(new DosageFormGroupDao());
		addToDaoObjects(new EvidenceTypeDao());
		addToDaoObjects(new FamilyDao());
		addToDaoObjects(new GenericTextDao());
		addToDaoObjects(new GenusDao());
		addToDaoObjects(new GeographicAreaDao());
		addToDaoObjects(new HomeopathicSubstanceDao());
		addToDaoObjects(new IchClassDao());
		addToDaoObjects(new IndicationTypeDao());
		addToDaoObjects(new IngredientDao());
		addToDaoObjects(new IngredientCategoryDao());
		addToDaoObjects(new IngredientRoleDao());
		addToDaoObjects(new IngredientRoleSpecializationDao());
		addToDaoObjects(new IngredientSpecializationDao());
		addToDaoObjects(new IngredientSourceDao());
		addToDaoObjects(new JournalDao());
		addToDaoObjects(new JournalArticleDao());
		addToDaoObjects(new ImpuritySubstanceDao());
		addToDaoObjects(new ManufacturingProcessDao());
		addToDaoObjects(new MeddraHighLevelGroupTermDao());
		addToDaoObjects(new MeddraHighLevelTermDao());
		addToDaoObjects(new MeddraLowestLevelTermDao());
		addToDaoObjects(new MeddraPreferredTermDao());
		addToDaoObjects(new MeddraSystemOrganClassDao());
		addToDaoObjects(new MedicinalRoleDao());
		addToDaoObjects(new MicrobiologicalImpurityDao());
		addToDaoObjects(new MonographDao());
		addToDaoObjects(new MonographEntryDao());
		addToDaoObjects(new MonographRiskStatementDao());
		addToDaoObjects(new MonographRouteOfAdministrationDao());
		addToDaoObjects(new MonographUseDao());
		addToDaoObjects(new NamedOrganismGroupDao());
		addToDaoObjects(new NhpClassificationDao());
		addToDaoObjects(new NonNHPRoleDao());
		addToDaoObjects(new NonMedicinalPurposeDao());
		addToDaoObjects(new NonMedicinalRoleDao());
		addToDaoObjects(new OrganismDao());
		addToDaoObjects(new OrganismGroupDao());
		addToDaoObjects(new OrganismMonographEntryDao());
		addToDaoObjects(new OrganismPartDao());
		addToDaoObjects(new OrganismPartTypeDao());
		addToDaoObjects(new OrganismTypeDao());
		addToDaoObjects(new OrganismTypeGroupDao());
		addToDaoObjects(new PermissionDao());
		addToDaoObjects(new PreparationRuleDao());
		addToDaoObjects(new PreparationTypeDao());
		addToDaoObjects(new PreparationTypeGroupDao());
		addToDaoObjects(new ProductTypeDao());
		addToDaoObjects(new QualifiedSynonymDao());
		addToDaoObjects(new QuantityToleranceModelDao());
		addToDaoObjects(new RationaleDao());
		addToDaoObjects(new ReferenceDao());
		addToDaoObjects(new RegistryEntryDao());
		addToDaoObjects(new ReportDao());
		addToDaoObjects(new RestrictionDao());
		addToDaoObjects(new RestrictionTypeDao());
		addToDaoObjects(new RiskDao());
		addToDaoObjects(new RiskTypeDao());
		addToDaoObjects(new RouteOfAdministrationDao());
		addToDaoObjects(new SimpleOrganismGroupDao());
		addToDaoObjects(new SolventDao());
		addToDaoObjects(new SpeciesDao());
		addToDaoObjects(new SubIngredientDao());
		addToDaoObjects(new SubPopulationDao());
		addToDaoObjects(new SubTaxaDao());
		addToDaoObjects(new SynonymDao());
		addToDaoObjects(new TaxonDao());
		addToDaoObjects(new TestCategoryDao());
		addToDaoObjects(new TestMethodDao());
		addToDaoObjects(new ThesesDao());
		addToDaoObjects(new UnitsDao());
		addToDaoObjects(new UnitsTypeDao());
		addToDaoObjects(new UnpublishedWorkDao());
		addToDaoObjects(new UserAccountDao());
		addToDaoObjects(new UserRoleDao());
		addToDaoObjects(new UseTypeDao());
		addToDaoObjects(new WebsiteDao());
	}

	// ~ Instance fields
	// --------------------------------------------------------

	private Class modelClass;

	// ~ protected
	// --------------------------------------------------------------

	/**
	 * Constructs a data access object that deals with instances of the passed Class
	 * in the model.
	 * 
	 * @param modelClass
	 *            DOCUMENT ME!
	 */
	protected AbstractDao(Class modelClass) {
		this.modelClass = modelClass;
	}

	// ~ public
	// -----------------------------------------------------------------

	/**
	 * Returns the Dao Object based on the modelClassName provided.
	 * 
	 * @param modelClassName
	 * 
	 * @return
	 */
	static public AbstractDao getInstanceOf(String modelClassName) {

		return (AbstractDao) daoObjects.get(modelClassName);

	}

	/**
	 * Method that reads the content of a clob and returns it as a string. This
	 * method is used by the ApplicationText model object.
	 * 
	 * Note: This method has been renamed to old to find all the code that should no
	 * longer use this method.
	 * 
	 * @param inputClob
	 *            containing the clob we want to convert.
	 * 
	 * @return the clob value as a string.
	 */
	static public String readClobOld(Clob inputClob) {

		// Abort if the clob is empty.
		if (inputClob == null) {
			return "";
		}

		// Walk through the clob as an AsciiStream.
		String outputText = "";

		try {
			InputStream ip = inputClob.getAsciiStream();
			int c = ip.read();

			while (c > 0) {
				outputText = outputText + (char) c;
				c = ip.read();
			}
		} catch (Exception e) {
			return "";
		}

		return outputText;
	}

	/**
	 * Converts a string to an SQL Clob value using the Hibernate createClob method.
	 * This method is used by the ApplicationText model object.
	 * 
	 * Note: This method has been renamed to old to find all the code that should no
	 * longer use this method.
	 * 
	 * @param text
	 *            that we want to convert to a Clob.
	 * 
	 * @return clob containing the converted text.
	 */
	public Clob textToClobOld(String text) {

		if (text == null) {
			return null;
		}

		Session session = getCurrentSession();
		// Session session = HibernateUtil.getCurrentSession();
		return Hibernate.getLobCreator(session).createClob(text);
	}

	/**
	 * Although this is primarily intended to be used by methods that support
	 * autoCommit, it may be called externally. This commits the open database
	 * transaction and starts a new one. If a problem occurs when the transaction is
	 * being committed, it is rolled back and a new one is started. This is used to
	 * force any problems to occur when the action is done so a meaningful error
	 * message can be displayed to the user. Otherwise, the Filter will close the
	 * Transaction when the Request is finishing and any problems encountered will
	 * cause the error page to be displayed.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public void commit() throws HibernateException {

		try {
			getSession().getTransaction().commit();
			getSession().beginTransaction();

		} catch (HibernateException ex) {

			if (getSession().getTransaction().isActive()) {
				getSession().getTransaction().rollback();
				getSession().beginTransaction();
			}

			throw ex;
		}
	}

	/**
	 * Deletes the passed object from the persistent store. Note that this does not
	 * remove the model equivalent Object from memory, it simply makes it transient
	 * (not have an equivalent in the persistent store).
	 * 
	 * @param entity
	 *            the entity to be deleted from the persistent store.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public void delete(Object entity) throws HibernateException {
		getSession().delete(entity);
	}

	/**
	 * Gets a collection of all the Units in the persistent store. Note that these
	 * are sorted by their codes.
	 * 
	 * @return all instances of the Units that are in the persistent store.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public List findAll() throws HibernateException {
		return findByCriteria(null, null);
	}

	/**
	 * Gets a collection of all the instances of the model's Class from persistent
	 * store. Returns an empty List if none are found.
	 * 
	 * @param order
	 *            a Collection of Orders that determine the sort of the returned
	 *            instances. If empty or null, they will not be sorted.
	 * 
	 * @return all instances of the model's Class that are in the persistent store.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public List findAll(Collection order) throws HibernateException {
		return findByCriteria(null, order);
	}

	/**
	 * Gets a collection of instances of the model's Class from the persistent store
	 * using query by example. Returns an empty List if none are found that match.
	 * 
	 * @param exampleInstance
	 *            DOCUMENT ME!
	 * @param excludeProperty
	 *            DOCUMENT ME!
	 * 
	 * @return instances of the model's Class that are in the persistent store that
	 *         match the query criteria.
	 */
	public List findByExample(Object exampleInstance, String[] excludeProperty) {
		Criteria crit = getSession().createCriteria(getModelClass());
		Example example = Example.create(exampleInstance);

		if ((excludeProperty != null) && (excludeProperty.length > 0)) {

			for (int i = 0; i < excludeProperty.length; i++) {
				example.excludeProperty(excludeProperty[i]);
			}
		}

		log.error("Example: " + example);
		crit.add(example);

		return crit.list();
	}

	/**
	 * Gets the Units with the passed ID from the persistent store. Returns null if
	 * none is found.
	 * 
	 * @param id
	 *            the unique key of the Units in the persistent store.
	 * @param lock
	 *            true if the corresponding record should automatically be locked
	 *            from updates in the persistent store.
	 * 
	 * @return the Units, if any, with the passed ID in the persistent store.
	 */
	public Object findById(Long id, boolean lock) {
		return null;
	}

	public PagedList findForFilter(String name, String language) {
		List emptyList = null;

		return new PagedList(emptyList);
	}

	/**
	 * Gets an appropriate error key by parsing through the passed exception to try
	 * and determine the problem. The calling method would typically create an
	 * ActionError with the returned String. These keys must be in the
	 * ApplicationResources.properties file for this to work properly.
	 * 
	 * @param e
	 *            the problem that was encountered.
	 * 
	 * @return one of the error constants.
	 */
	/*
	 * - old code... deprecated with hibernate 3.3.2 public String
	 * getErrorKey(HibernateException e) { String errorMessages = "";
	 * 
	 * for (int i = 0; i < e.getMessages().length; i++) { errorMessages +=
	 * e.getMessages()[i]; }
	 * 
	 * log.error("Error Messages: " + errorMessages); if
	 * (errorMessages.indexOf("NULL") != -1) { return ERROR_REQUIRED_FIELD; } else
	 * if ((errorMessages.indexOf("insert") != -1) &&
	 * (errorMessages.indexOf("unique constraint") != -1)) { return
	 * ERROR_DUPLICATE_RECORD; }
	 * 
	 * return ERROR_GENERIC; }
	 */

	/**
	 * Gets the model's Class that this deals with in the persistence layer.
	 * 
	 * @return the model's Class that this deals with in the persistence layer.
	 */
	public Class getModelClass() {
		return modelClass;
	}

	public Object getModelClassInstance() {

		Class c = getModelClass();

		Object o = null;

		try {
			o = c.newInstance();
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return o;
	}

	/**
	 * Flag determining if the list of items for this DAO object should be loaded
	 * into the ApplicationGlobal's resident memory. This is used for system pick
	 * lists, and other code list objects. This method returns the static value
	 * stored in the LOAD_INTO_MEMORY that should be located at the top of the
	 * individual DAO classes. By default, the AbstractDAO ensures that none of the
	 * individual DAO classes are loaded unless specifically set.
	 * 
	 * @return LOAD_INTO_MEMORY static variable.
	 */
	public boolean loadIntoMemory() {
		return LOAD_INTO_MEMORY;
	}

	/**
	 * Ensures that the passed model object is in a state that allows it to be
	 * worked with (initialized, deleted, or modified). This should be used for any
	 * objects that were created during a previous request. Note that the passed
	 * object should be replaced with the one returned from this method. This is
	 * required since Hibernate cannot lazy initialize or otherwise work with
	 * detached objects (those created by a Session other than the current one).
	 * Since Sessions are maintained over the life of a request, objects spanning
	 * requests become detached. Note that if this object is actually detached, this
	 * causes a single select statement to be sent to the database to reload the
	 * object (although any changes that had been made to it should be preserved).
	 * 
	 * @param entity
	 *            the model object to be attached to the current Session.
	 * 
	 * @return an equivalent object that is attached to the current Session.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public Object prepForUse(Object entity) throws HibernateException {

		if (getSession().contains(entity)) {
			return entity;
		}

		return getSession().merge(entity);
	}

	/**
	 * Ensures that the passed model objects are in a state that allows them to be
	 * worked with (initialized, deleted, or modified). This should be used for any
	 * objects that were created during a previous request. Note that this will
	 * modify the passed list "in place", replacing all detached objects with
	 * equivalent attached ones. This is required since Hibernate cannot lazy
	 * initialize or otherwise work with detatched objects (those created by a
	 * Session other than the current one). Since Sessions are maintained over the
	 * life of a request, objects spanning requests become detached. Note that for
	 * each object that is actually detached, this will cause a single select
	 * statement to be sent to the database to reload the object (although any
	 * changes that had been made to it should be preserved).
	 * 
	 * @param entities
	 *            a List of model objects to be attached to the current Session.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public void prepForUse(List entities) throws HibernateException {

		for (int i = 0; i < entities.size(); i++) {

			if (!getSession().contains(entities.get(i))) {
				entities.set(i, getSession().merge(entities.get(i)));
			}
		}
	}

	/**
	 * Adds or updates the passed object in the persistent store. To add a new
	 * object, simply create a new instance of the model Object (which makes a
	 * transient one), update its values, then pass it to this method. It will be
	 * inserted into the persistent store with a newly assigned unique ID. Note that
	 * if you pre-populate the unique ID and another object already exists with that
	 * ID, this will update the existing object rather than sensing a unique
	 * constraint failure. In that case, use save() instead. This can also be used
	 * to update detached objects (those retrieved by a previous Session).
	 * 
	 * @param entity
	 *            the entity to be updated or added to the persistent store.
	 * @param autoCommit
	 *            true if this should commit the transaction (it also subsequently
	 *            starts a new one for future actions). Done so that problems are
	 *            immediately apparent.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public void save(Object entity, boolean autoCommit) throws HibernateException {
		getSession().save(entity);

		if (autoCommit) {
			commit();
		}
	}

	/**
	 * Adds or updates the passed object in the persistent store. To add a new
	 * object, simply create a new instance of the model Object (which makes a
	 * transient one), update its values, then pass it to this method. It will be
	 * inserted into the persistent store with a newly assigned unique ID. Note that
	 * if you pre-populate the unique ID and another object already exists with that
	 * ID, this will update the existing object rather than sensing a unique
	 * constraint failure. In that case, use save() instead. This can also be used
	 * to update detached objects (those retrieved by a previous Session).
	 * 
	 * @param entity
	 *            the entity to be updated or added to the persistent store.
	 * @param autoCommit
	 *            true if this should commit the transaction (it also subsequently
	 *            starts a new one for future actions). Done so that problems are
	 *            immediately apparent.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	public void saveOrUpdate(Object entity, boolean autoCommit) throws HibernateException {
		getSession().saveOrUpdate(entity);

		if (autoCommit) {
			commit();
		}
	}

	/**
	 * Deletes the object. This function tries to encapsulate the transaction and
	 * return with catchable errors that can then be provided back to the client.
	 * 
	 * @param object
	 *            being deleted
	 * 
	 * @return any error message. (SUCCESS is returned when the deletion works)
	 */
	public String objectDelete(Object object) {
		try {
			// SessionFactory sf = HibernateUtil.getSessionFactory();
			// Session session = sf.getCurrentSession();
			Session session = getCurrentSession();
			session.beginTransaction();
			session.merge(object);
			session.delete(object);
			session.getTransaction().commit();
			return SUCCESS;
		} catch (ConstraintViolationException e) {
			return CONSTRAINT_VIOLATION;
		} catch (HibernateException e) {
			return HIBERNATE_EXCEPTION;
		} catch (Exception e) {
			return EXCEPTION;
		}
	}

	public boolean isNewObject(PersistentObject persistentObject) {
		if (persistentObject == null) {
			return true;
		}
		if (persistentObject.isPersisted()) {
			return false;
		}
		return true;
	}

	// ~ protected
	// --------------------------------------------------------------

	// +++++ PROTECTED
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * A convenience method used by subclasses to return the instances of the
	 * model's Class that match the passed critera.
	 * 
	 * @param criterion
	 *            a Collection of Criterion that determine the instances to be
	 *            returned. If empty or null, this will return all instances.
	 * @param order
	 *            a Collection of Orders that determine the sort of the returned
	 *            instances. If empty or null, they will not be sorted.
	 * 
	 * @return a collection of the model objects that match the criteria in the
	 *         persistent store, if any. Returns an empty PagedList if none are
	 *         found.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered.
	 */
	protected List findByCriteria(Collection criterion, Collection order) throws HibernateException {
		Criteria crit = getSession().createCriteria(getModelClass());

		if (criterion != null) {
			Iterator it = criterion.iterator();

			while (it.hasNext()) {
				crit.add((Criterion) it.next());
			}
		}

		if (order != null) {
			Iterator it = order.iterator();

			while (it.hasNext()) {
				crit.addOrder((Order) it.next());
			}
		}

		return crit.list();
	}

	/***************************************************************************
	 * A convenience method used to return a specific instance of the model's Class.
	 * It can only return pure objects, so typically subclasses will implement their
	 * own findById() calling this and casting the result to the appropriate model
	 * Class.
	 * 
	 * @param id
	 *            the unique key of the desired object in the persistent store.
	 * @param lock
	 *            true if the corresponding record should automatically be locked
	 *            from updates in the persistent store.
	 * 
	 * @return the model object, if any, with the passed unique ID in the persistent
	 *         store. Returns null if none is found.
	 * 
	 * @throws HibernateException
	 *             if a problem is encountered. IngredientsException when an object
	 *             that can not be cast as a Persistent Object is encountered.
	 */
	protected Object findByIdBase(Serializable id, boolean lock) throws HibernateException, IngredientsException {

		Object persistentObj;

		if (lock) {

			persistentObj = getSession().load(getModelClass(), id, LockMode.UPGRADE);

		} else {

			persistentObj = getSession().load(getModelClass(), id);

		}

		try {

			/*
			 * The following code was modified form the original toString() implementation.
			 * This change has resulted in a performance improvement and in a reduction of
			 * the number of GC objects. GB - 2011/06/02
			 */
			if (persistentObj instanceof PersistentObject) {
				((PersistentObject) persistentObj).getId();
			} else {
				throw new IngredientsException(IngredientsException.EXCEPTION_UNSUPPORTED_OBJECT + persistentObj);
			}
			return persistentObj;
		} catch (ObjectNotFoundException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/*
	 * Extracts the values of the search criteria name/value pairs for a specific
	 * name as a String array
	 */
	protected String[] getCriteriaValuesForName(String name, SearchCriteria searchCriteria) {

		List criteriaValues = new ArrayList();

		List criteria = searchCriteria.getCriteria();
		SearchCriterion criterion = null;
		for (int x = 0; x < criteria.size(); x++) {
			criterion = (SearchCriterion) (criteria.get(x));
			if (criterion.getAttributeName().equals(name))
				criteriaValues.add(criterion.getAttributeValue());
		}

		int numValues = criteriaValues.size();
		if (numValues < 1)
			return null;

		String[] retValues = new String[numValues];
		for (int x = 0; x < criteriaValues.size(); x++) {
			retValues[x] = (String) (criteriaValues.get(x));
		}
		return retValues;

	}

	/***************************************************************************
	 * Gets the current Hibernate Session. Typically used by clients in cases where
	 * they know that a transaction has already been started and the associated
	 * session can be retrieved.
	 * 
	 * @return the current Hibernate Session.
	 */
	protected Session getSession() {

		// Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Session session = getCurrentSession();

		/*
		 * Sometimes the transaction is closed by a save or delete event. This ensures
		 * that the transaction is restarted. TODO: this should only be required if
		 * doing updates, which the current IDB web app (our primary client) does not
		 * need. Do we need this?
		 */
		if (!session.getTransaction().isActive()) {
			session.beginTransaction();
		}

		return session;

	}

	// ~ private
	// ----------------------------------------------------------------

	/**
	 * Add a dao to the daoObjects Map, using the modelClassName as the key.
	 * 
	 * @param daoObject
	 *            being added to the daoObjects Map.
	 */
	static private void addToDaoObjects(Object daoObject) {
		AbstractDao abstractDao = (AbstractDao) daoObject;
		daoObjects.put(Utils.getShortName(abstractDao.modelClass.toString()), abstractDao);
	}

	/***************************************************************************
	 * Gets the OrganismPart with the passed ID from the persistent store. Returns
	 * null if none is found.
	 * 
	 * @param id
	 *            the unique key of the OrganismPart in the persistent store.
	 * @param lock
	 *            true if the corresponding record should automatically be locked
	 *            from updates in the persistent store.
	 * @return the OrganismPart, if any, with the passed ID in the persistent store.
	 */
	public List<PersistentObjectSearchResult> findBySearchCriteria(String searchString, String modelClassName,
			String language) {

		boolean isFrench = isFrench(language);

		StringBuffer queryText = getSearchResultSQLStatement(isFrench, searchString);

		if (queryText == null) {
			return null;
		}

		// Refresh the materialized view.
		refreshMaterializedView();

		// Get the data from the list.
		List results = executeQuery(queryText);

		// convert the search results to IngredientSearchResults objects.
		return convertToSearchResults(results, modelClassName, isFrench);

	}

	/***************************************************************************
	 * Converts the result set returned by the INGREDIENT_SYNONYMS_MV Materialized
	 * View into a List of IngredientSearchResult objects. Each row is represented
	 * as an Object[], which contains the 7 columns we are interested in. The last 3
	 * columns are used to determine if the Ingredient is associated to a Generated
	 * Monograph.
	 * 
	 */

	private List<PersistentObjectSearchResult> convertToSearchResults(List results, String modelClassName,
			boolean isFrench) {
		// List ingredientSearchResults = new ArrayList();
		ArrayList<PersistentObjectSearchResult> persistentObjectSearchResults = new ArrayList<PersistentObjectSearchResult>();
		Object[] columns = null;
		PersistentObjectSearchResult persistentObjectSearchResult = null;
		int dups = 0;

		for (int x = 0; x < results.size(); x++) {
			columns = (Object[]) results.get(x);
			persistentObjectSearchResult = new PersistentObjectSearchResult(columns, modelClassName, isFrench);

			// Temp hack to test for duplicates - need to refine query
			// This will be refactored as part of Monograph object model
			// refactoring.
			if (!persistentObjectSearchResults.contains(persistentObjectSearchResult)) {
				persistentObjectSearchResults.add(persistentObjectSearchResult);
			} else {
				dups++;
			}
		}

		// log ("found " + dups + " dups");
		return persistentObjectSearchResults;
	}

	public List<PersistentObjectSearchResult> convertToPersistentObjectSearchResults(List results, boolean isFrench) {
		// List ingredientSearchResults = new ArrayList();
		ArrayList<PersistentObjectSearchResult> persistentObjectSearchResults = new ArrayList<PersistentObjectSearchResult>();
		Object[] columns = null;
		PersistentObjectSearchResult persistentObjectSearchResult = null;
		int dups = 0;

		for (int x = 0; x < results.size(); x++) {
			columns = (Object[]) results.get(x);
			persistentObjectSearchResult = new PersistentObjectSearchResult(columns, isFrench);

			// Temp hack to test for duplicates - need to refine query
			// This will be refactored as part of Monograph object model
			// refactoring.
			if (!persistentObjectSearchResults.contains(persistentObjectSearchResult)) {
				persistentObjectSearchResults.add(persistentObjectSearchResult);
			} else {
				dups++;
			}
		}

		// log ("found " + dups + " dups");
		return persistentObjectSearchResults;
	}

	/**
	 * Defines the custom SQL query required to return a record set from the
	 * database. This is used to populate a list of simplified
	 * PersistentObjectSearchResults objects used in place of the model objects for
	 * list display purposes, search purposes, etc.
	 * 
	 * <p>
	 * In the AbstractDAO, the default implementation returns a null value. This
	 * indicates that no implementation is available for the object in question.
	 * When attempts to retreive the object list through the ApplicationGlobals, the
	 * default object list will be retreived.
	 * 
	 * <p>
	 * If implemented, then the appropriate list of PersistentObjectSearchResults
	 * will be returned.
	 * 
	 * @param isFrench
	 *            is the query in French or English? Will impact the search results
	 * @param searchString
	 *            to help narrow the query
	 * @return a SQL statement that when executed will return the appropriate
	 *         resultset to populate the PersistentObjectSearchResults.
	 */
	// public StringBuffer getSearchResultSQLStatement(boolean isFrench,
	// String searchString) {
	// return null;
	// }
	public List executeQuery(StringBuffer queryText) {
		List results = null;

		log.debug("WS Materialized View query: " + queryText);
		long startTime = System.currentTimeMillis();

		results = getSession().createSQLQuery(queryText.toString()).list();

		long endTime = System.currentTimeMillis();
		long execTime = endTime - startTime;
		// log.debug("WS Materialized View execution time(ms):" + execTime);
		return results;
	}

	/**
	 * Getter that returns the SQL discriminator used by this object. This method
	 * exposes the discriminator for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of
	 * PersistentObjectSearchResults.
	 */
	abstract String getDiscriminator();

	/**
	 * Getter that returns the SQL English column used by this object. This method
	 * exposes the table name for use in building the searchResultsSqlStatement in
	 * the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	abstract String getEnglishColumnName();

	/**
	 * Getter that returns the SQL French column used by this object. This method
	 * exposes the table name for use in building the searchResultsSqlStatement in
	 * the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	abstract String getFrenchColumnName();

	/**
	 * Getter that returns the SQL id column used by this object. This method
	 * exposes the table name for use in building the searchResultsSqlStatement in
	 * the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	abstract String getIdColumnName();

	/**
	 * Getter that returns the SQL table name used by this object. This method
	 * exposes the table name for use in building the searchResultsSqlStatement in
	 * the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	abstract String getTableName();

	public StringBuffer getSearchResultSQLStatement(boolean isFrench, String searchString) {

		if (getIdColumnName() == null) {
			return null;
		}

		StringBuffer queryText = new StringBuffer();

		// need a nested select to support rownum check at end.
		queryText.append("SELECT * from (");
		queryText.append("SELECT DISTINCT ");
		queryText.append("t." + getIdColumnName() + ", ");

		if (isFrench) {
			queryText.append("t." + getFrenchColumnName() + SPACE);
		} else {
			queryText.append("t." + getEnglishColumnName() + SPACE);
		}

		queryText.append("FROM ");
		queryText.append(getTableName() + " t ");

		boolean hasDiscriminator = false;
		if (getDiscriminator() != null && !getDiscriminator().equals(EMPTY_STRING)) {
			hasDiscriminator = true;
		}

		// Add the WHERE if we are doing a search or if we have a discriminator.
		if ((searchString != null && !searchString.equals(EMPTY_STRING)) || hasDiscriminator) {
			queryText.append("WHERE ");
		}

		// Add the discriminator if available.
		if (hasDiscriminator) {
			queryText.append("t." + getDiscriminator() + SPACE);
		}

		// NB. Search terms are language specific
		if (searchString != null && !searchString.equals(EMPTY_STRING)) {
			if (hasDiscriminator) {
				queryText.append("AND ");
			}
			if (isFrench) {
				queryText.append("LOWER(t." + getFrenchColumnName() + ") ");
			} else {
				queryText.append("LOWER(t." + getEnglishColumnName() + ") ");
			}
			String likeClause = (SqlUtil.createQueryExpression(searchString));
			queryText.append(likeClause);
		}

		if (isFrench) {
			queryText.append("ORDER BY LOWER(t." + getFrenchColumnName() + ") ");
		} else {
			queryText.append("ORDER BY LOWER(t." + getEnglishColumnName() + ") ");
		}
		queryText.append(") WHERE ROWNUM <= " + DEFAULT_MAX_ROWS);

		return queryText;
	}

	private boolean isTableMaterializedView() {
		String key = "_mv";
		if (getTableName() != null && getTableName().indexOf(key) != -1) {
			return true;
		}
		return false;
	}

	private void refreshMaterializedView() {

		if (isTableMaterializedView()) {

			log.info("Refreshing table: " + getTableName());

			getSession().createSQLQuery("execute DBMS_SNAPSHOT.REFRESH( '" + getTableName() + "','C');");
		}

	}

	/**
	 * Wire in the spring context into the AbstractDAO to support legacy code.
	 * 
	 * @return current session
	 */
	private Session getCurrentSession() {
		SessionFactory sessionFactory = (SessionFactory) SpringContext.getApplicationContext()
				.getBean("hibernateSessionFactory");
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	

	/***************************************************************************
     * Returns whether the abbreviated form of language is French or not.
     * Works with both 2 character and 3 character ISO forms.
     * 
     * @param language
     *            The abbreviated language
     * @return boolean whether or not the language provided was French.
     */
	protected boolean isFrench(String language) {
	    return Locale.CANADA_FRENCH.getISO3Language().equals(language.toLowerCase()) || Locale.CANADA_FRENCH.getLanguage().equals(language.toLowerCase());
	}
}
