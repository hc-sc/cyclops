package ca.gc.hc.nhpd.model;

/**
 * The DietaryAllowance class is used in conjunction with the RecommendedDietaryAllowances class
 * and helps associate individual threshold amounts with specific subPopulation.
 * 
 * Note:  This was created in response to CR-0047 (IMSD #2171)
 * 
 * @author GEBRUNET
 *
 */

public class DietaryAllowance extends PersistentObject {

    private Float minimumThreshold;
    private SubPopulation subPopulation;
    private RecommendedDietaryAllowance recommendedDietaryAllowance;
    
    public Float getMinimumThreshold() {
        return minimumThreshold;
    }
    
    public void setMinimumThreshold(Float minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }
    
    public RecommendedDietaryAllowance getRecommendedDietaryAllowance() {
        return recommendedDietaryAllowance;
    }
    
    public void setRecommendedDietaryAllowance(RecommendedDietaryAllowance recommendedDietaryAllowance) {
        this.recommendedDietaryAllowance = recommendedDietaryAllowance;
    }
    
    public SubPopulation getSubPopulation() {
        return subPopulation;
    }
    
    public void setSubPopulation(SubPopulation subPopulation) {
        this.subPopulation = subPopulation;
    }
    
}
