package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This object represents what an ingredient combination may be used for. It is an
 * indication Synonym.
 */
public class NonTraditionalMedicinalUse extends Synonym {
	private Indication indication;

    /***************************************************************************
     */
	public Indication getIndication(){
		return indication;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setIndication(Indication newVal){
		indication = newVal;
	}

}