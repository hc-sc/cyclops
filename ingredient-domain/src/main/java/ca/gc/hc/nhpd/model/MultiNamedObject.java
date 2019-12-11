package ca.gc.hc.nhpd.model;
import java.util.Set;
/*
 * Marker interface for objects that have synonyms.
 * Introduced for the Controlled Vocabulary searches on code tables to provide
 * generic mechanism for displaying objects along with their synonyms.
 * Known implementing classes:
 * 	OrganismPartType, DosageForm, NonMedicinalPurpose
 * 
 */

public interface MultiNamedObject {
	public String getName();
	public Set getSynonyms();
}
