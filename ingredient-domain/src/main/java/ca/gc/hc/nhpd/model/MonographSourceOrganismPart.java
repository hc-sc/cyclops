package ca.gc.hc.nhpd.model;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.util.StringComparator;

public class MonographSourceOrganismPart extends PersistentObject implements MonographSourceMaterial, Comparable {

	private OrganismPart organismPart;
	private SourceOrganismPart sourceOrganismPart;
	private Set<Citation> citations;
	private MonographEntry monographEntry;
	private Set<MonographDoseCombination> monographDoseCombinations;

	public String getSourceMaterialName() {
		if (sourceOrganismPart == null)
			return null;

		return sourceOrganismPart.getName();
		/*
		 * if (isLanguageFrench()) { return getSourceMaterialNameF(); } return
		 * getSourceMaterialNameE();
		 */
		/*
		 * Commented out by Bruce Dempsey - to be deleted 2018-07-25 if (organismPart ==
		 * null) return null;
		 * 
		 * if (organismPart.getOrganismGroup() != null) return
		 * organismPart.getOrganismGroup().getName();
		 * 
		 * if (organismPart.getType() != null) return organismPart.getType().getName();
		 * 
		 * return null;
		 */
	}

	public Set<Citation> getCitations() {
		return citations;
	}

	/**
	 * Gets the citations associated with this Object ordered by the citation date.
	 * 
	 * @return the citations Set for this Object ordered by date.
	 */
	public Set<Citation> getCitationsByDate() {
		Comparator<Citation> citationComparator = new Citation.CitationComparator();
		Set<Citation> citationsByDate = new TreeSet<Citation>(citationComparator);
		citationsByDate.addAll(getCitations());
		return citationsByDate;
	}

	public void setCitations(Set<Citation> citations) {
		this.citations = citations;
	}

	// TODO - think i can get rid of the e/f stuff here...
	// return either the single part type name or the organism group type name
	public String getSourceMaterialNameE() {
		// return sourceMaterialNameE;

		if (organismPart == null)
			return null;

		if (organismPart.getType() != null)
			return organismPart.getType().getName();

		if (organismPart.getOrganismGroup() != null)
			return organismPart.getOrganismGroup().getName();

		return null;
	}

	public void setSourceMaterialNameE(String sourceMaterialNameE) {
		// this.sourceMaterialNameE = sourceMaterialNameE;
	}

	public String getSourceMaterialNameF() {
		// return sourceMaterialNameF;
		if (organismPart == null)
			return null;
		if (organismPart.getOrganismGroup() == null)
			return null;

		return organismPart.getOrganismGroup().getName();
	}

	public void setSourceMaterialNameF(String sourceMaterialNameF) {
		// this.sourceMaterialNameF = sourceMaterialNameF;
	}

	public OrganismPart getOrganismPart() {
		return organismPart;
	}

	public void setOrganismPart(OrganismPart organismPart) {
		this.organismPart = organismPart;
	}

	// This was added to leverage the MV.
	public SourceOrganismPart getSourceOrganismPart() {
		return sourceOrganismPart;
	}

	public void setSourceOrganismPart(SourceOrganismPart sourceOrganismPart) {
		this.sourceOrganismPart = sourceOrganismPart;
	}

	public int compareTo(Object obj) throws ClassCastException {

		if (obj == null)
			return -1;

		// Compare on organism part when different.
		if (!getOrganismPart().equals(((MonographSourceOrganismPart) obj).getOrganismPart())) {
			return getOrganismPart().compareTo(((MonographSourceOrganismPart) obj).getOrganismPart());
		}

		// Otherwise compare on the source material name.
		return StringComparator.compare(getSourceMaterialName(),
				((MonographSourceMaterial) obj).getSourceMaterialName());
	}

	/**
	 * Provides a handle to the parent monograph entries.
	 * 
	 * @return Monograph Entry that is connected to the MonographSourceOrganismPart.
	 */
	public MonographEntry getMonographEntry() {
		return monographEntry;
	}

	public void setMonographEntry(MonographEntry monographEntry) {
		this.monographEntry = monographEntry;
	}

	/**
	 * Provides a handle to the parent Monograph Doses.
	 * 
	 * @return Monograph Doses that are connected to this
	 *         MonographSourceOrganismPart.
	 */
	public Set<MonographDoseCombination> getMonographDoseCombinations() {
		return monographDoseCombinations;
	}

	public void setMonographDoseCombinations(Set<MonographDoseCombination> monographDoseCombinations) {
		this.monographDoseCombinations = monographDoseCombinations;
	}

}
