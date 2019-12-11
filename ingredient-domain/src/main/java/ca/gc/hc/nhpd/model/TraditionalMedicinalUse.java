package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This object represents what an ingredient combination may be used for in
 * traditional medicine.
 */
public class TraditionalMedicinalUse extends Synonym { //TODO New Class
	private TraditionalParadigm paradigm;

    /***************************************************************************
     */
	public TraditionalParadigm getParadigm(){
		return paradigm;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setParadigm(TraditionalParadigm newVal){
		paradigm = newVal;
	}

}