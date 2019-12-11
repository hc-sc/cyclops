package ca.gc.hc.nhpd.servicepilot.ingredient;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import ca.gc.hc.nhpd.dao.ApplicationTypeDao;
import ca.gc.hc.nhpd.dao.CommonTermDao;
import ca.gc.hc.nhpd.dao.CountryDao;
import ca.gc.hc.nhpd.dao.DosageFormDao;
import ca.gc.hc.nhpd.dao.HomeopathicDilutionDao;
import ca.gc.hc.nhpd.dao.IngredientDao;
import ca.gc.hc.nhpd.dao.MonographDao;
import ca.gc.hc.nhpd.dao.NonMedicinalPurposeDao;
import ca.gc.hc.nhpd.dao.OrganismPartDao;
import ca.gc.hc.nhpd.dao.OrganismPartTypeDao;
import ca.gc.hc.nhpd.dao.OrganismTypeDao;
import ca.gc.hc.nhpd.dao.OrganismTypeGroupDao;
import ca.gc.hc.nhpd.dao.PreparationRuleDao;
import ca.gc.hc.nhpd.dao.PreparationTypeDao;
import ca.gc.hc.nhpd.dao.RouteOfAdministrationDao;
import ca.gc.hc.nhpd.dao.SolventDao;
import ca.gc.hc.nhpd.dao.StandardOrGradeReferenceDao;
import ca.gc.hc.nhpd.dao.SubPopulationDao;
import ca.gc.hc.nhpd.dao.UnitsDao;
import ca.gc.hc.nhpd.dto.IngredientSearchCriteria;
import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.PreClearedInfo;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ApplicationType;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.Citation;
import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.DosageUnit;
import ca.gc.hc.nhpd.model.HomeopathicDilution;
import ca.gc.hc.nhpd.model.HomeopathicFormula;
import ca.gc.hc.nhpd.model.HomeopathicGenericText;
import ca.gc.hc.nhpd.model.HomeopathicMethodOfPreparation;
import ca.gc.hc.nhpd.model.HomeopathicPreparationType;
import ca.gc.hc.nhpd.model.HomeopathicRole;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientId;
import ca.gc.hc.nhpd.model.IngredientRole;
import ca.gc.hc.nhpd.model.NamedOrganismGroup;
import ca.gc.hc.nhpd.model.NhpClassification;
import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.NonMedicinalRestriction;
import ca.gc.hc.nhpd.model.NonMedicinalRole;
import ca.gc.hc.nhpd.model.Organism;
import ca.gc.hc.nhpd.model.OrganismGroup;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.OrganismPartType;
import ca.gc.hc.nhpd.model.OrganismType;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.model.PreparationRule;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.ProvinceState;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.Reference;
import ca.gc.hc.nhpd.model.Restriction;
import ca.gc.hc.nhpd.model.RestrictionType;
import ca.gc.hc.nhpd.model.RouteOfAdministration;
import ca.gc.hc.nhpd.model.SimpleOrganismGroup;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.model.StandardOrGradeReference;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.model.TaxonomyNode;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * 
 * This class is the Ingredient service implementation. It is targetted for
 * deployment as an EJB Session Bean. It is called by the Ingredient Web Service
 * client
 * 
 * @author MRABE
 * 
 */
public class IngredientSessionBean {

	private static final Log log = LogFactory.getLog(IngredientSessionBean.class);

	public static final String COULD_NOT_FIND_INGREDIENT_ERROR_MESSAGE = "Could not find ingredient for id: ";

	/*
	 * - never read locally... TODO private static final int
	 * DEFINED_ORGANISM_SUBSTANCE = 1; private static final int
	 * CUSTOM_ORGANISM_SUBSTANCE = 2; private static final int CHEMICAL_SUBSTANCE =
	 * 3; private static final int HOMEOPATHIC_SUBSTANCE = 4;
	 */

	/*
	 * Search Ingredients by Role and an optional searchString. Use one of the Role
	 * strings specified in the IngredientService interface.
	 */
	public List<IngredientSearchResult> searchIngredients(IngredientSearchCriteria criteria) throws RemoteException {
		log.debug("IngredientSessionBean: searchIngredients ");
		// log.debug("IngredientSessionBean: searchIngredients ");

		List<IngredientSearchResult> ingredientSearchResults = null;

		IngredientDao dao = new IngredientDao();
		// converts client side literal to the db equivalents.
		criteria.convertToDBCriteria();
		Session session = null;
		try {
			// no need to set the session language for the search, its done by the DAO.
			session = HibernateUtil.startHibernateTransaction(false);
			ingredientSearchResults = dao.findBySearchCriteria((SearchCriteria) criteria,
					ThreadContext.getInstance().getUserLanguage(), true, true, false, false,
					IngredientServicePropertyManager.getPlaMaxSearchResultSize());
			if (ingredientSearchResults == null) {
				log.error("DAO returned Null Ingredient Search results");
			} else {
				log.debug("Retrieved " + ingredientSearchResults.size() + " Ingredients");
				/*
				 * NB. Search results are specially constructed at the DAO tier from the JDBC
				 * resultset - Hibernate ORM not used in this case to maximize performance.
				 */
				/*
				 * for (int x=0;x<ingredientSearchResults.size();x++) { searchResult =
				 * (IngredientSearchResult)ingredientSearchResults.get(x); }
				 */

			}
		} catch (Throwable ex) {
			log.error("Exception occured in searchIngredients ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return ingredientSearchResults;

	}

	/*
	 * Get all Dosage Forms
	 */
	public List<DosageForm> getDosageForms() throws RemoteException {

		List<DosageForm> dosageForms = null;
		DosageFormDao dao = new DosageFormDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			dosageForms = dao.findAll();
			if (dosageForms == null) {
				log.error("DAO returned Null solvents");
			} else {
				// log.debug ("Retrieved " + solvents.size() + " Solvents");
				for (DosageForm dosageForm : dosageForms) {

					if (dosageForm.getAcceptableRoutesOfAdmin() != null) {
						for (RouteOfAdministration routeOfAdministration : dosageForm.getAcceptableRoutesOfAdmin()) {
							routeOfAdministration.getCode();
						}
					}

					if (dosageForm.getDisplaySynonyms() != null) {
						for (Synonym synonym : dosageForm.getDisplaySynonyms()) {
							synonym.getName();
						}
					}

					if (dosageForm.getDosageUnits() != null) {
						for (DosageUnit dosageUnit : dosageForm.getDosageUnits()) {
							dosageUnit.getCode();
						}
					}
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getSolvents", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return dosageForms;

	}

	/*
	 * Get all Solvents
	 */
	public List<Solvent> getSolvents() throws RemoteException {

		// log.debug ("getSolvents ");
		List<Solvent> solvents = null;
		SolventDao dao = new SolventDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			solvents = dao.findAll(ThreadContext.getInstance().getUserLanguage());
			if (solvents == null) {
				log.error("DAO returned Null solvents");
			} else {
				// log.debug ("Retrieved " + solvents.size() + " Solvents");
				for (int x = 0; x < solvents.size(); x++) {
					Solvent solvent = (Solvent) solvents.get(x);
					solvent.getCode();
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getSolvents", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return solvents;

	}

	/*
	 * Get all Application Types
	 */
	public List<ApplicationType> getApplicationTypes() throws RemoteException {

		List<ApplicationType> applicationTypes = null;
		ApplicationTypeDao dao = new ApplicationTypeDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			applicationTypes = dao.findAll();
			if (applicationTypes == null) {
				log.error("DAO returned Null solvents");
			} else {
				// log.debug ("Retrieved " + solvents.size() + " Solvents");
				for (ApplicationType applicationType : applicationTypes) {
					applicationType.getCode();
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getSolvents", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return applicationTypes;

	}

	/*
	 * public List<CommonTermType> getCommonTermTypes() throws RemoteException {
	 * 
	 * List<CommonTermType> commonTermTypes = null; Session session = null;
	 * CommonTermTypeDao dao = new CommonTermTypeDao();
	 * 
	 * try { session =
	 * HibernateUtil.startHibernateTransaction(false,HibernateUtil.CANADIAN_FRENCH);
	 * commonTermTypes = dao.findAll();
	 * 
	 * if (commonTermTypes == null) {
	 * log.error("DAO returned Null CommonTermType Search results"); } else { for
	 * (CommonTermType commonTermType : commonTermTypes) { // Initialize (assuming
	 * Hibernate lazy initialization) if (commonTermType.getCommonTerms() != null) {
	 * Set<CommonTerm> commonTerms = commonTermType.getCommonTerms(); for
	 * (CommonTerm commonTerm : commonTerms) { commonTerm.getCode(); } } } } } catch
	 * (Throwable ex) { log.error("Exception occured in searchMonographs ", ex);
	 * ex.printStackTrace(); } finally {
	 * HibernateUtil.commitHibernateTransaction(session); } return commonTermTypes;
	 * }
	 */

	/*
	 * Search Common Terms by type.
	 */
	public List<CommonTerm> getCommonTermsByTypes(String[] commonTermTypes) throws RemoteException {

		// log.debug ("getUnitsByTypes ");
		List<CommonTerm> commonTerms = null;
		CommonTermDao dao = new CommonTermDao();
		Session session = null;

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			commonTerms = dao.findByTypes(commonTermTypes, ThreadContext.getInstance().getUserLanguage());
			if (commonTerms == null) {
				log.error("DAO returned Null Units");
			} else {
				for (CommonTerm commonTerm : commonTerms) {
					commonTerm.getName();
					commonTerm.getCode();
					commonTerm.getCommonTermType().getName();
				}

			}
		} catch (Throwable ex) {
			log.error("Exception occured in getCommonTermsByTypes ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return commonTerms;
	}

	public List<Country> getCountries() throws RemoteException {

		List<Country> countries = null;
		Session session = null;
		CountryDao dao = new CountryDao();

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			countries = dao.findAll();

			if (countries == null) {
				log.error("DAO returned Null Countries Search results");
			} else {
				for (Country country : countries) {
					// Initialize (assuming Hibernate lazy initialization)
					if (country.getProvincesStates() != null) {
						Set<ProvinceState> provinceStates = country.getProvincesStates();
						for (ProvinceState provinceState : provinceStates) {
							provinceState.getCode();
						}
					}
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getCountries ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}
		return countries;
	}

	public List<StandardOrGradeReference> getStandardOrGradeReferences() throws RemoteException {

		List<StandardOrGradeReference> standardOrGradeReferences = null;
		Session session = null;
		StandardOrGradeReferenceDao dao = new StandardOrGradeReferenceDao();

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			standardOrGradeReferences = dao.findAll();

			if (standardOrGradeReferences == null) {
				log.error("DAO returned Null Standard Or Grade Reference Search results");
			} else {
				for (StandardOrGradeReference standardOrGradeReference : standardOrGradeReferences) {
					// Initialize (assuming Hibernate lazy initialization)
					if (standardOrGradeReference.getReference() != null) {
						standardOrGradeReference.getReference().getCode();
					}
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}
		return standardOrGradeReferences;
	}

	/*
	 * Get all NonMedicinalPurposes
	 */
	public List<NonMedicinalPurpose> getNonMedicinalPurposes() throws RemoteException {

		// log.debug ("IngredientSessionBean: getNonMedicinalPurposes ");
		List<NonMedicinalPurpose> nmps = null;
		NonMedicinalPurposeDao dao = new NonMedicinalPurposeDao();
		Session session = null;

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			nmps = dao.findAll(ThreadContext.getInstance().getUserLanguage());
			if (nmps == null) {
				log.error("DAO returned Null NMPs");
			} else {
				for (NonMedicinalPurpose nonMedicinalPurpose : nmps) {
					checkNonMedicinalPurpose(nonMedicinalPurpose);
				}
				/*
				 * // log.debug ("Retrieved " + nmps.size() + " NMPs"); for (int x = 0; x <
				 * nmps.size(); x++) { // Initialize (assuming Hibernate lazy initialization)
				 * NonMedicinalPurpose nmp = (NonMedicinalPurpose) nmps.get(x); nmp.getName(); }
				 */
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getNonMedicinalPurposes ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return nmps;

	}

	/*
	 * Get OrganismPartTypes - if param is null, get all. Otherwise filter by type
	 * group
	 */
	public List<OrganismPartType> getOrganismPartTypes(String organismTypeGroup) throws RemoteException {

		// log.debug ("getOrganismPartTypes ");
		List<OrganismPartType> orgPartTypes = null;
		OrganismPartTypeDao dao = new OrganismPartTypeDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);

			// Organism Type Group is assumed to have been validated at this
			// point
			if (organismTypeGroup == null || organismTypeGroup.equals(""))
				orgPartTypes = dao.findAll(ThreadContext.getInstance().getUserLanguage());
			else
				orgPartTypes = dao.findByTypeGroup(organismTypeGroup, ThreadContext.getInstance().getUserLanguage());

			if (orgPartTypes == null) {
				log.error("DAO returned Null OrgPartTypes");
			} else {
				// log.debug ("Retrieved " + orgPartTypes.size() +
				// " OrgPartTypes");
				for (int x = 0; x < orgPartTypes.size(); x++) {
					// Initialize (assuming Hibernate lazy initialization)
					OrganismPartType orgPartType = (OrganismPartType) orgPartTypes.get(x);
					orgPartType.getName();
					orgPartType.getNameE();
					orgPartType.getNameF();
					Iterator<OrganismTypeGroup> grpIter = orgPartType.getTypeGroups().iterator();
					while (grpIter.hasNext()) {
						grpIter.next().getName();
					}
					/* EG :added this to avoid a LazyInitialization exception */
					for (Synonym synonym : orgPartType.getSynonyms()) {

						/*
						 * synonym.getDisplayName(); synonym.getDescription();
						 * synonym.getDescriptionE(); synonym.getDescriptionF(); synonym.getCommentE();
						 * synonym.getCommentF(); synonym.getNameE(); synonym.getNameF();
						 */
						synonym.getName();
						for (Citation c : synonym.getCitationsE()) {
							c.getShortDescription();
						}
						for (Citation c : synonym.getCitationsF()) {
							c.getShortDescription();
						}
					}
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getOrganismPartTypes ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return orgPartTypes;

	}

	/*
	 * Get all Organism PreparationTypes
	 */
	public List<PreparationType> getPreparationTypes() throws RemoteException {

		// log.debug ("getPreparationTypes ");
		List<PreparationType> prepTypes = null;
		PreparationTypeDao dao = new PreparationTypeDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			prepTypes = dao.findAll(ThreadContext.getInstance().getUserLanguage());
			if (prepTypes == null) {
				log.error("DAO returned Null PreparationType");
			} else {
				// log.debug ("ISB: Retrieved " + prepTypes.size() +
				// " prep types");
				checkPreparationType(prepTypes);
				/*
				 * for (PreparationType preparationType : prepTypes) { // Initialize (assuming
				 * Hibernate lazy initialization) preparationType.getName(); for (Solvent
				 * solvent : preparationType.getRestrictedSolvents()) { solvent.getCode(); } }
				 */
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getPreparationTypes ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return prepTypes;

	}

	private void checkPreparationType(Collection<PreparationType> preparationTypes) {
		for (PreparationType preparationType : preparationTypes) {
			// Initialize (assuming Hibernate lazy initialization)
			preparationType.getName();
			for (Solvent solvent : preparationType.getRestrictedSolvents()) {
				solvent.getCode();
			}
		}
	}

	/*
	 * Search Units by type. Supported types are: DURATION, DOSAGE, FREQUENCY Use
	 * one of the Role strings specified in the IngredientService interface. ePLA
	 * 1.4 functionality.
	 */
	public List<Units> getUnitsByTypes(String[] types) throws RemoteException {

		// log.debug ("getUnitsByTypes ");
		List<Units> units = null;
		UnitsDao dao = new UnitsDao();
		Session session = null;

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			units = dao.findByTypes(types, ThreadContext.getInstance().getUserLanguage());
			if (units == null) {
				log.error("DAO returned Null Units");
			} else {
				// log.debug ("Retrieved " + units.size() + " Units");
				for (int x = 0; x < units.size(); x++) {
					initializeUnits((Units) units.get(x));
					// log.debug ("Unit.code: " + unit.getCode());
					// log.debug ("Unit.name: " + unit.getName());
				}

			}
		} catch (Throwable ex) {
			log.error("Exception occured in getUnits ", ex);
			ex.printStackTrace();
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return units;

	}

	/*
	 * Search Units by type code. Supported types are: DURATION, DOSAGE, FREQUENCY
	 * Use one of the Role strings specified in the IngredientService interface.
	 * ePLA 2.0 functionality.
	 */
	public List<Units> getUnitsByTypeCodes(String[] typeCodes) throws RemoteException {

		List<Units> units = null;
		UnitsDao dao = new UnitsDao();
		Session session = null;

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			units = dao.findByTypeCodes(typeCodes, ThreadContext.getInstance().getUserLanguage());
			if (units == null) {
				log.error("DAO returned Null Units");
			} else {
				// log.debug ("Retrieved " + units.size() + " Units");
				for (int x = 0; x < units.size(); x++) {
					initializeUnits((Units) units.get(x));
					// log.debug ("Unit.code: " + unit.getCode());
					// log.debug ("Unit.name: " + unit.getName());
				}

			}
		} catch (Throwable ex) {
			log.error("Exception occured in getUnits ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return units;

	}

	/**
	 * Touches units members that are part of UnitsWS. This is done to help
	 * Hibernate lazy load the Units.
	 */
	private void initializeUnits(Units unit) {
		unit.getName();
		unit.getCode();
		unit.getType().getName();
	}

	/*
	 * Get all preferred SubPopulations
	 */
	public List<SubPopulation> getPreferredSubPopulations() throws RemoteException {

		List<SubPopulation> subPopulations = null;
		SubPopulationDao dao = new SubPopulationDao();
		Session session = null;

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			subPopulations = dao.findPreferred(ThreadContext.getInstance().getUserLanguage());
			if (subPopulations == null) {
				log.error("DAO returned Null subPopulations");
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getPreferredSubPopulations", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return subPopulations;
	}

	/*
	 * Get all HomeopathicDilutions
	 */
	public List<HomeopathicDilution> getHomeopathicDilutions() throws RemoteException {

		List<HomeopathicDilution> homeopathicDilutions = null;
		HomeopathicDilutionDao homeopathicDilutionDao = new HomeopathicDilutionDao();
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			homeopathicDilutions = homeopathicDilutionDao.findAll(ThreadContext.getInstance().getUserLanguage());
			if (homeopathicDilutions == null) {
				log.error("DAO returned Null solvents");
			} else {
				// log.debug ("Retrieved " + solvents.size() + " Solvents");
				for (HomeopathicDilution homeopathicDilution : homeopathicDilutions) {
					homeopathicDilution.getCode();
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in getSolvents", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}
		return homeopathicDilutions;
	}

	/*
	 * Get Ingredient by ID. TODO - revisit the "startTx" flag if/when we transition
	 * to a more elegant n-tier model.
	 */
	public Ingredient getIngredientById(IngredientId ingredientId, boolean startTx)
			throws RemoteException, IngredientsException {

		String ingredientName = ingredientId.getIngredientName();
		Long id = ingredientId.getId();
		Session session = null;
		/*
		 * Long initAttrsTime = 0l; Long startTxTime = 0l; Long commitTxTime = 0l; Long
		 * daoTime = 0l; Long startTime = System.currentTimeMillis();
		 */
		Ingredient ingredient = null;
		IngredientDao dao = new IngredientDao();

		try {
			if (startTx) {
				// Long txStart = System.currentTimeMillis();
				session = HibernateUtil.startHibernateTransaction(false);
				// startTxTime = System.currentTimeMillis() - txStart;
			}
			Long daoStart = System.currentTimeMillis();
			ingredient = (Ingredient) dao.findById(id, false, true);

			// daoTime = System.currentTimeMillis() - daoStart;
			if (ingredient == null) {
				log.error("DAO returned Null ingredient");
			} else {
				ingredient.getAuthorizedName();

				// Long initStart = System.currentTimeMillis();
				initializeIngredientAttrs(ingredient);
				// initAttrsTime = System.currentTimeMillis() - initStart;
			}
		} catch (IngredientsException ie) {
			// Note: In the previous version of the code, we did nothing and we allowed
			// the code to bottom and return a null value. Since the DAO layer is now
			// thowing an IngredietnsException, we had to modify the code to return
			// a trappable web service error. This error is trapped and leveraged by
			// the integration services.
			if (ie.getMessage().indexOf(IngredientsException.EXCEPTION_UNSUPPORTED_OBJECT) != -1) {
				// throw new IngredientsException(COULD_NOT_FIND_INGREDIENT_ERROR_MESSAGE +
				// ingredientId.getId(), ie);
				throw new IngredientsException(COULD_NOT_FIND_INGREDIENT_ERROR_MESSAGE + ingredientId.getId());
			}
			throw ie;
		} catch (Throwable ex) {
			log.error("Exception occured in getIngredientById: ", ex);
		} finally {
			if (startTx) {
				try {
					session.close();
				} catch (Throwable t) {
					/*
					 * Ignore exceptions on closing session. Although closing the session in this
					 * fashion is recommended by Hibernate, the close typically fails with a
					 * "Session was already closed" error in WebSphere/Linux envs.
					 */
				}
			}
		}

		/*
		 * Long totalTime = System.currentTimeMillis() - startTime; log("Total Time: " +
		 * totalTime); log("InitAttrs Time: " + initAttrsTime); log("TX start Time: " +
		 * startTxTime); log("TX commit Time: " + commitTxTime); log("Dao Time: " +
		 * daoTime);
		 */

		return ingredient;

	}

	/*
	 * public List getDosageFormsForRouteOfAdmin(String routeOfAdmin, int
	 * assessmentRequired) {
	 * 
	 * log.debug ("getDosageFormsForRouteOfAdmin ");
	 * 
	 * startHibernateTransaction(); DosageFormDao dao = new
	 * RouteOfAdministrationDao();
	 * 
	 * List routesOfAdmin = null; try { routesOfAdmin = dao.findAll();
	 * 
	 * if (routesOfAdmin == null) log.error ("DAO returned NULL RoutesOfAdmin");
	 * else { log.debug("Retrieved " + routesOfAdmin.size() + " Admin Routes");
	 * checkDosageForm(routesOfAdmin); } } catch (Throwable ex) {
	 * log.error("Exception occurred in IngredientSessionBean: ",ex);
	 * 
	 * finally { commitHibernateTransaction(); }
	 * 
	 * return routesOfAdmin; }
	 * 
	 * }
	 */
	/*
	 * Get all External monographs
	 */
	public List<PreClearedInfo> getPcis() throws RemoteException {

		MonographDao dao = new MonographDao();

		List<PreClearedInfo> pciList = null;
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			String lang = ThreadContext.getInstance().getUserLanguage();
			pciList = dao.getPciList(lang);

			if (pciList == null)
				log.error("DAO returned no pcis");
			else {
				log.debug("Retrieved " + pciList.size() + " PCIs");
			}
		} catch (Throwable ex) {
			log.error("Exception occurred in IngredientSessionBean: ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return pciList;
	}

	/*
	 * populates the attributes required for the getIngredientById method. Depending
	 * on the type of Ingredient (e.g. ChemicalSubstance or
	 * DefinedOrganismSubstance) the appropriate dependent objects are retrieved.
	 */
	private void initializeIngredientAttrs(Ingredient ingredient) {
		/*
		 * Approved name of SubIngredient is found via the contained Ingredient (and its
		 * subclassed types). This is not automatically loaded via Hibernate.
		 */
		// ingredient.getCommonNames();

		ingredient.getScientificNames();

		// ingredient.getProperNames();
		// loadCommonNames(ingredient);
		// logSys("COMMON NAMES FROM GETINGREDIENTBYID:");
		// logCommonNames(ingredient);
		logSubIngredientInfo(ingredient);
		initializeSubIngredient(ingredient);
		initializeNhpClassifications(ingredient);

		/*
		 * Load the attributes based on type: a) for ChemicalSubstances we retrieve the
		 * Organisms which are Source Materials b) for DefinedOrganismSubstances we
		 * retrieve the Chemicals that are Constituents, and the Part Preparations
		 */

		if (ingredient instanceof ChemicalSubstance) {
			initializeChemicalAttrs((ChemicalSubstance) ingredient);
		} else if (ingredient instanceof DefinedOrganismSubstance) {
			initializeOrganismAttrs((DefinedOrganismSubstance) ingredient);
		} else if (ingredient instanceof HomeopathicSubstance) {
			initializeHomeopathicAttr((HomeopathicSubstance) ingredient);
		}

		loadNonMedicinalRoles(ingredient);
		initializeIngredientNames(ingredient);
	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the ingredient names and explores the
	 * following object tree:
	 * 
	 * - ingredient.properName.qualifiedSynonyms -
	 * ingredient.commonName.qualifiedSynonyms
	 * 
	 * @param ingredient
	 */
	private void initializeIngredientNames(Ingredient ingredient) {
		if (ingredient.getProperNames() != null && ingredient.getProperNames().size() != 0) {
			initializeQualifiedSynonym(ingredient.getProperNames());
		}
		if (ingredient.getCommonNames() != null && ingredient.getCommonNames().size() != 0) {
			initializeQualifiedSynonym(ingredient.getCommonNames());
		}
	}

	private void loadNonMedicinalRoles(Ingredient ingredient) {
		Set<IngredientRole> ingredientRoles = ingredient.getRoles();
		for (IngredientRole ingredRole : ingredientRoles) {
			if (ingredRole instanceof NonMedicinalRole) {
				NonMedicinalRole nmr = (NonMedicinalRole) ingredRole;
				Set<NonMedicinalPurpose> nmps = nmr.getPurposes();
				for (NonMedicinalPurpose nmp : nmps) {
					nmp.getName();
					nmp.getSynonyms();
				}
				if (nmr.getRestrictions() != null && nmr.getRestrictions().size() != 0) {
					for (Restriction restriction : nmr.getRestrictions()) {
						if (restriction instanceof NonMedicinalRestriction) {
							NonMedicinalRestriction nmres = (NonMedicinalRestriction) restriction;
							nmres.getDetail();
							if (nmres.getType() != null) {
								RestrictionType rt = nmres.getType();
								rt.getName();
							}
							if (nmres.getRouteOfAdministration() != null) {
								RouteOfAdministration roa = nmres.getRouteOfAdministration();
								roa.getName();
							}
							if (nmres.getAllowedPurposes() != null && nmres.getAllowedPurposes().size() != 0) {
								for (NonMedicinalPurpose purpose : nmres.getAllowedPurposes()) {
									purpose.getName();
								}
							}
						}
						restriction.getType();
						restriction.getUnits();
					}
				}
			}
		}
	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the qualified synonyms and associated
	 * referenced anb explores the following object tree:
	 * 
	 * - qualifiedSynonym - qualifiedSynonym.references.code
	 * 
	 * @param ingredient
	 */
	private void initializeQualifiedSynonym(Set<QualifiedSynonym> qualifiedSynonyms) {
		if (qualifiedSynonyms == null || qualifiedSynonyms.size() == 0) {
			return;
		}
		for (QualifiedSynonym qualifiedSynonym : qualifiedSynonyms) {
			qualifiedSynonym.getDisplayName();
			if (qualifiedSynonym.getReferences() != null && qualifiedSynonym.getReferences().size() != 0) {
				for (Reference reference : qualifiedSynonym.getReferences()) {
					reference.getCode();
				}
			}
			initializeSynonym(qualifiedSynonym.getSynonym());
		}
	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the synonyms and explores the synonym
	 * objects:
	 * 
	 * - synonyms - synonyms.synonym
	 * 
	 * @param Set<Synonym>
	 *            synonyms
	 */
	private void initializeSynonyms(Set<Synonym> synonyms) {
		if (synonyms == null || synonyms.size() == 0) {
			return;
		}
		for (Synonym synonym : synonyms) {
			initializeSynonym(synonym);
		}
	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the synonym and exploresthe following
	 * attributes:
	 * 
	 * - synonym.displayName - synonym.name
	 * 
	 * @param Synonym
	 */
	private void initializeSynonym(Synonym synonym) {
		if (synonym != null) {
			synonym.getDisplayName();
			synonym.getName();
		}
	}

	/**
	 * Populate the attributes required for the getIngredientById method when
	 * retrieving a homeopathic substance. Homeopathic Substances have a one to one
	 * relationship with the homeopathic formula. This method traverses the
	 * homeopathic substance, touches the homeopathic formula and any associated
	 * homeopathic dilution and homeopathic method of preparation.
	 * 
	 * @param homeopathicSubstance
	 *            being retrieved.
	 */
	private void initializeHomeopathicAttr(HomeopathicSubstance homeopathicSubstance) {

		initializeHomeopathicNamesAttr(homeopathicSubstance);

		if (homeopathicSubstance != null && homeopathicSubstance.getRoles() != null) {

			Set<IngredientRole> roles = homeopathicSubstance.getRoles();

			// The only role should be the homeopathic one.
			HomeopathicRole homeopathicRole = (HomeopathicRole) roles.iterator().next();

			if (homeopathicRole != null && homeopathicRole.getHomeopathicFormulas() != null) {
				Set<HomeopathicFormula> homeopathicFormulas = homeopathicRole.getHomeopathicFormulas();

				// Their should only be one formula for the role.
				HomeopathicFormula homeopathicFormula = (HomeopathicFormula) homeopathicFormulas.iterator().next();

				if (homeopathicFormula != null) {
					// Pull the object through hibernate.
					homeopathicFormula.getValuesAsString();
					HomeopathicDilution homeopathicDilution = homeopathicFormula.getHomeopathicDilution();
					homeopathicDilution.getValuesAsString();
					Set<HomeopathicMethodOfPreparation> homeopathicMethodOfPreparations = homeopathicFormula
							.getHomeopathicMethodOfPreparations();
					if (homeopathicMethodOfPreparations != null) {
						Iterator<HomeopathicMethodOfPreparation> i = homeopathicMethodOfPreparations.iterator();

						while (i.hasNext()) {
							HomeopathicMethodOfPreparation homeopathicMethodOfPreparation = (HomeopathicMethodOfPreparation) i
									.next();
							if (homeopathicMethodOfPreparation != null
									&& homeopathicMethodOfPreparation.getHomeopathicPreparationType() != null) {
								HomeopathicPreparationType homeopathicPreparationType = homeopathicMethodOfPreparation
										.getHomeopathicPreparationType();
								if (homeopathicPreparationType.getValuesAsString() != null) {
									homeopathicPreparationType.getValuesAsString();
								}
							}
						}
					}
				}
			}
		}

	}

	private void initializeHomeopathicNamesAttr(HomeopathicSubstance homeopathicSubstance) {

		if (homeopathicSubstance == null) {
			return;
		}

		initializeQualifiedSynonym(homeopathicSubstance.getCommonNames());
		initializeQualifiedSynonym(homeopathicSubstance.getProperNames());
		initializeHomeopathicGenericTexts(homeopathicSubstance.getHomeopathicCommonNames());
		initializeHomeopathicGenericTexts(homeopathicSubstance.getHomeopathicProperNames());

	}

	private void initializeHomeopathicGenericTexts(Set<HomeopathicGenericText> genericTexts) {

		if (genericTexts == null || genericTexts.size() == 0) {
			return;
		}
		for (HomeopathicGenericText genericText : genericTexts) {
			genericText.getText();
		}
	}

	/*
	 * Retrieves the DefinedOrganismSubstance attributes: 1. Organism parts and
	 * their constituent ChemicalSubstances are retrieved. 2. Part Preparations for
	 * the DefinedOrganismSubstance 3. Taxonomy Node Names - e.g. for Fish Oil, the
	 * taxonomy node names are: Family: Ammodytidae Family: Carangidae Family:
	 * Clupeidae Family: Engraulidae Family: Osmeridae Family: Salmonidae Family:
	 * Scombridae
	 */
	private void initializeOrganismAttrs(DefinedOrganismSubstance dos) {
		// get the Organism Parts
		Set orgParts = dos.getParts();
		Set subIngreds = null;
		SubIngredient subIngred = null;
		Iterator<SubIngredient> subIter = null;
		Iterator<OrganismPart> orgPartsIter = null;

		/*
		 * Get the Approved name of the Subingredients of the organism parts These are
		 * the chemical constituents (NB. SubIngredients are only ChemicalSubstances,
		 * they do not model Organisms)
		 */
		if (orgParts != null) {
			orgPartsIter = orgParts.iterator();
			while (orgPartsIter.hasNext()) {
				OrganismPart op = (OrganismPart) orgPartsIter.next();
				subIngreds = op.getSubIngredients();
				if (subIngreds != null) {
					op.getName();
					// log.debug("ORG Part Name: " + op.getName()
					// + " No. of Subingredients: " + subIngreds.size());
					subIter = subIngreds.iterator();
					while (subIter.hasNext()) {
						subIngred = (SubIngredient) subIter.next();
						subIngred.getIngredient().getAuthorizedName();
					}
					// Ensures that the organism is instantiated.
					if (op.getOrganism() != null) {
						Organism organism = op.getOrganism();
						organism.getName();

						if (organism.getQualifiedSynonyms() != null) {
							initializeQualifiedSynonym(organism.getQualifiedSynonyms());
						}

					}
				}
				checkOrganismPartType(op.getType());
			}
		}

		// If the DOS is an Organism, we need to get the Organism Parts and
		// SubIngredients via the Organism.
		Organism org = null;
		Set<OrganismPart> orgParts2 = null;
		if (dos.getOrganismGroup() instanceof SimpleOrganismGroup) {
			orgParts2 = dos.getPartList();
			Iterator<OrganismPart> orgPartIter = orgParts2.iterator();
			while (orgPartIter.hasNext()) {

				// get the subingredients for the parts
				OrganismPart orgPart = (OrganismPart) (orgPartIter.next());
				Hibernate.initialize(orgPart);
				orgPart.getName();
				orgPart.getId();

				// Ensures that the organism is instantiated.
				if (orgPart.getOrganism() != null) {
					Organism organism = orgPart.getOrganism();

					if ((organism != null) && (organism.getQualifiedSynonyms() != null)) {
						initializeQualifiedSynonym(organism.getQualifiedSynonyms());
					}
				}

				// log.debug("OrganismPart " + orgPart.getName()
				// + " Id: " + orgPart.getId());
				Set opSubs = orgPart.getSubIngredients();
				if (opSubs != null) {
					Iterator<SubIngredient> subIter2 = opSubs.iterator();
					while (subIter2.hasNext()) {
						// get the subingredients for the parts
						SubIngredient subIngredient = (SubIngredient) (subIter2.next());
						Hibernate.initialize(subIngredient);
						subIngredient.getName();
						subIngredient.getId();

						Set<RouteOfAdministration> routes = subIngredient.getRoutesOfAdministration();
						for (RouteOfAdministration route : routes) {
							Hibernate.initialize(route);
						}

						Set<Reference> refs = subIngredient.getReferences();
						for (Reference ref : refs) {
							Hibernate.initialize(ref);
						}
						// log.debug("SubIngredient " + subIngredient.getName()
						// + " Id: " + subIngredient.getId());
					}
				}
			}

			// A SimpleOrganismGroup can only have included Organisms
			Iterator<Organism> orgIter = dos.getOrganismGroup().getIncludedOrganisms().iterator();
			String orgTypeGroup = null;
			while (orgIter.hasNext()) {
				orgTypeGroup = orgIter.next().getType().getGroup().getName();
				// log.debug("OrganismTypeGroup " + orgTypeGroup);
			}
		}

		/*
		 * Get the part preparations - need to iterate thru the collection such that all
		 * data is initialized
		 */
		checkPreparationType(dos.getPreparations());
		/*
		 * Set partPreps = dos.getPreparations(); if (partPreps != null) { Iterator
		 * partPrepIter = partPreps.iterator(); String prepTypeName = null;
		 * checkPreparationType(dos.get) while (partPrepIter.hasNext()) {
		 * PreparationType prepType = (PreparationType) partPrepIter .next();
		 * prepTypeName = prepType.getName(); // log.debug("PrepType: " + prepTypeName);
		 * } }
		 */

		/*
		 * Get the list of taxonomy nodes from the OrganismGroup. A TaxonomyNode is
		 * realized by one of the following classes: Family, Genus, Species, SubSpecies,
		 * Organism. See the NamedOrganismGroup and SimpleOrganismGroup classes, along
		 * with the OrganismGroup.hbm.xml, which defines the Hibernate mapping of the
		 * various TaxonomyNodes to the Included/Excluded organisms tables.
		 * 
		 * NamedOrganismGroup provides the getIncludedTaxonomyNodes method, which will
		 * return all taxonomyNodes, whereas SimpleOrganismGroup only provides the
		 * getIncludedOrganisms method.
		 */
		OrganismGroup orgGroup = dos.getOrganismGroup();
		if (orgGroup == null)
			return;

		Iterator iter = null;
		if (orgGroup instanceof NamedOrganismGroup) {
			NamedOrganismGroup namedOrgGroup = (NamedOrganismGroup) orgGroup;
			Set taxonomyNodes = namedOrgGroup.getIncludedTaxonomyNodes();
			if (taxonomyNodes != null) {
				namedOrgGroup.getName();
				// log.debug("Named Organism Group: " + namedOrgGroup.getName()
				// + "\n Taxonomy Nodes size: " + taxonomyNodes.size());
				iter = taxonomyNodes.iterator();
				while (iter.hasNext()) {
					TaxonomyNode taxNode = (TaxonomyNode) iter.next();
					taxNode.getTaxonomyType();
					taxNode.getName();
					taxNode.getDisplayName(); // loads Species -> Genus name
					// log.debug("Taxonomy Node Type/Name: "
					// + taxNode.getTaxonomyType() + ": "
					// + taxNode.getName());
				}
			}
		} else if (orgGroup instanceof SimpleOrganismGroup) {
			SimpleOrganismGroup simpleOrgGroup = (SimpleOrganismGroup) orgGroup;
			Set organisms = simpleOrgGroup.getIncludedOrganisms();
			// log.debug("Simple Organism Group: Included Organisms size: " +
			// organisms.size());
			if (organisms != null) {
				iter = organisms.iterator();
				while (iter.hasNext()) {
					Organism org3 = (Organism) iter.next();
					org3.getTaxonomyType();
					org3.getName();
					// log.debug("Included Organism Name: "
					// + org3.getTaxonomyType() + ": "
					// + org3.getName());
				}
			}
		}
	}

	/*
	 * Retrieves the ChemicalSubstance attributes. Organism parts that are Source
	 * Materials of the ChemicalSubstance are retrieved.
	 */
	private void initializeChemicalAttrs(ChemicalSubstance chemical) {

		/*
		 * MR Retrieval of Source Organism Parts and Related Source Ingredients Changed
		 * to use new Materialized Views Feb 12, 2010 for performance purposes. A
		 * cleaner way of doing this would be to provide Hibernate mappings to the new
		 * MVs, which we may wish to prototype when time permits.
		 */
		OrganismPartDao orgPartDao = new OrganismPartDao();
		List<SourceOrganismPart> orgPartResults = orgPartDao.getSourceOrganismParts(chemical.getId(), null,
				chemical.isLanguageFrench());

		if (orgPartResults != null && orgPartResults.size() > 0) {
			chemical.setSourceOrganismPartDtos(orgPartResults);
		}

		IngredientDao ingredientDao = new IngredientDao();
		chemical.setRelatedSourceIngredientDtos(
				ingredientDao.getRelatedSourceIngredients(chemical.getId(), chemical.isLanguageFrench()));
		chemical.getType();

		Set<OrganismPart> partList = chemical.getRelatedSourceOrganismParts();

		for (OrganismPart orgPart : partList) {
			if (orgPart.getOrganismGroup() != null) {
				Set<TaxonomyNode> tnSet = orgPart.getOrganismGroup().getIncludedTaxonomyNodes();
				for (TaxonomyNode tn : tnSet) {
					tn.getDisplayName();
				}
			}
		}

		// long elapsed2 = System.currentTimeMillis() - start2;
		// log("Load SOPs time (ms) = " + elapsed2 );

	}

	private void logSubIngredientInfo(Ingredient ingredient) {
		int len = ingredient.getSubIngredients().size();
		String ingredType = null;
		if (ingredient instanceof DefinedOrganismSubstance)
			ingredType = "DOS";
		else if (ingredient instanceof ChemicalSubstance)
			ingredType = "CS";
		// log.debug("Ingredient Type: " + ingredType
		// + " No. of Subingredients: " + len);

	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the ingredient's subIngredienton and
	 * explores the following object tree:
	 * 
	 * - ingredient.subIngredient.parentOrganismPart.name -
	 * ingredieng.subIngredient.commonNames
	 * 
	 * @param ingredient
	 */
	private void initializeSubIngredient(Ingredient ingredient) {
		if (ingredient == null || ingredient.getSubIngredients() == null) {
			return;
		}

		for (SubIngredient subIngredient : ingredient.getSubIngredients()) {
			if (subIngredient.getParentOrganismPart() != null) {
				OrganismPart orgPart = subIngredient.getParentOrganismPart();
				if (orgPart != null) {
					orgPart.getName();
				}
				NamedOrganismGroup orgGroup = orgPart.getOrganismGroup();

				Set<TaxonomyNode> tn = orgGroup.getIncludedTaxonomyNodes();
				for (TaxonomyNode n : tn) {
					Hibernate.initialize(n);
				}
			}
			if (subIngredient.getIngredient() != null) {
				Ingredient si = subIngredient.getIngredient();

				initializeQualifiedSynonym(si.getCommonNames());
				initializeQualifiedSynonym(si.getProperNames());
				initializeSynonyms(si.getDisplayProperNames());

			}
		}
	}

	/**
	 * Model objects need to be initialized so that they become available to the web
	 * services. This method loads into memory the ingredient's nhpClassifications
	 * and explores the following object tree:
	 * 
	 * - ingredient.nhpClassifications.name
	 * 
	 * @param ingredient
	 */
	private void initializeNhpClassifications(Ingredient ingredient) {
		if (ingredient == null || ingredient.getNhpClassifications() == null
				|| ingredient.getNhpClassifications().size() == 0) {
			return;
		}
		for (NhpClassification nhpClassification : ingredient.getNhpClassifications()) {
			nhpClassification.getName();
		}
	}

	/*
	 * Retrieves all Administration Routes
	 */
	public List<RouteOfAdministration> getRoutesOfAdministration() throws Exception {
		// log.debug ("getRoutesOfAdministration ");
		Session session = null;
		RouteOfAdministrationDao dao = new RouteOfAdministrationDao();

		List<RouteOfAdministration> routesOfAdmin = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			routesOfAdmin = dao.findAll(ThreadContext.getInstance().getUserLanguage());

			if (routesOfAdmin == null)
				log.error("DAO returned NULL RoutesOfAdmin");
			else {
				log.debug("Retrieved " + routesOfAdmin.size() + " Admin Routes");
				checkDosageForm(routesOfAdmin);
			}
		} catch (Throwable ex) {
			log.error("Exception occurred in IngredientSessionBean: ", ex);
		}

		finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return routesOfAdmin;
	}

	/*
	 * Retrieves all Organism Types
	 */
	public List getOrganismTypes() throws Exception {
		log.debug("getOrganismType");

		OrganismTypeDao dao = new OrganismTypeDao();
		Session session = null;
		List organismTypes = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			organismTypes = dao.findAll(ThreadContext.getInstance().getUserLanguage());

			if (organismTypes == null)
				log.error("DAO returned NULL OrganismTypes");
			else {
				log.debug("Retrieved " + organismTypes.size() + " Organism Types");
				checkOrganismTypeGroup(organismTypes);
			}
		} catch (Throwable ex) {
			log.error("Exception occurred in IngredientSessionBean: ", ex);
		}

		finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return organismTypes;
	}

	public List<PreparationRule> getPreparationRules() throws RemoteException {
		log.debug("MonographSessionBean: searchIngredients ");

		List<PreparationRule> preparationRules = null;
		Session session = null;
		PreparationRuleDao dao = new PreparationRuleDao();

		try {
			session = HibernateUtil.startHibernateTransaction(false);
			preparationRules = dao.findAll();

			if (preparationRules == null) {
				log.error("DAO returned Null PreparationRules Search results");
			} else {
				log.debug("Retrieved " + preparationRules.size() + " preparation rules.");
				// log.debug ("ISB: Retrieved " + prepTypes.size() +
				// " prep types");
				for (PreparationRule preparationRule : preparationRules) {
					// Initialize (assuming Hibernate lazy initialization)
					if (preparationRule.getRestrictedSolvents() != null) {
						Set<Solvent> solvents = preparationRule.getRestrictedSolvents();
						for (Solvent solvent : solvents) {
							solvent.getCode();
						}
					}
				}
			}
		} catch (Throwable ex) {
			log.error("Exception occured in searchMonographs ", ex);
		} finally {
			HibernateUtil.commitHibernateTransaction(session);
		}
		return preparationRules;
	}

	/*
	 * Retrieves all Organism Types
	 */
	public List<OrganismTypeGroup> getOrganismTypeGroups() throws Exception {
		log.debug("getOrganismTypeGroups");

		OrganismTypeGroupDao dao = new OrganismTypeGroupDao();

		List<OrganismTypeGroup> organismTypeGroups = null;
		Session session = null;
		try {
			session = HibernateUtil.startHibernateTransaction(false);
			organismTypeGroups = dao.findAll(ThreadContext.getInstance().getUserLanguage());

			if (organismTypeGroups == null)
				log.error("DAO returned NULL OrganismTypeGroups");
			else {
				log.debug("Retrieved " + organismTypeGroups.size() + " Organism Type Groups");
			}
		} catch (Throwable ex) {
			log.error("Exception occurred in IngredientSessionBean: ", ex);
		}

		finally {
			HibernateUtil.commitHibernateTransaction(session);
		}

		return organismTypeGroups;
	}

	private void checkOrganismTypeGroup(List organismTypes) {
		for (int x = 0; x < organismTypes.size(); x++) {
			OrganismType organismType = (OrganismType) organismTypes.get(x);
			OrganismTypeGroup organismTypeGroup = organismType.getGroup();
			organismTypeGroup.getName();
		}
	}

	private void checkDosageForm(List<RouteOfAdministration> routesOfAdministration) {
		for (RouteOfAdministration routeOfAdministration : routesOfAdministration) {
			Set<DosageForm> dosageForms = routeOfAdministration.getPossibleDosageForms();
			for (DosageForm dosageForm : dosageForms) {
				checkSynonyms(dosageForm.getSynonyms());
				for (DosageUnit dosageUnit : dosageForm.getDosageUnits()) {
					dosageUnit.getCode();
				}
			}
		}
	}

	/**
	 * This method tries to instantiate a Non Medicinal Purpose.
	 * 
	 * @param nonMedicinalPurpose
	 *            being instantiated.
	 */
	private void checkNonMedicinalPurpose(NonMedicinalPurpose nonMedicinalPurpose) {
		if (nonMedicinalPurpose == null) {
			return;
		}
		nonMedicinalPurpose.getName();
		checkSynonyms(nonMedicinalPurpose.getDisplaySynonyms());
		checkSynonyms(nonMedicinalPurpose.getSynonyms());
	}

	/**
	 * This method tries to instantiate Organism Part Types.
	 * 
	 * @param organismPartType
	 *            being instantiated.
	 */
	private void checkOrganismPartType(OrganismPartType organismPartType) {
		if (organismPartType == null) {
			return;
		}
		organismPartType.getName();
		checkSynonyms(organismPartType.getDisplaySynonyms());
		checkSynonyms(organismPartType.getSynonyms());
	}

	/**
	 * This method tries to instantiate Synonyms. These synonyms should have
	 * lazy=false when used for display purposes.
	 * 
	 * @param synonyms
	 *            being instantiated.
	 */
	private void checkSynonyms(Set<Synonym> synonyms) {
		if (synonyms == null) {
			return;
		}
		for (Synonym synonym : synonyms) {
			synonym.getDisplayName();
			synonym.getName();
		}
	}
}
