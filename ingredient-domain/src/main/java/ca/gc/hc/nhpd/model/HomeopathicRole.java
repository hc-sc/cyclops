package ca.gc.hc.nhpd.model;

import java.util.Set;


/**
 * An object that, when present, indicates that an ingredient can be used for
 * the defined non-medical purposes as long as it follows the defined
 * non-medical restrictions.
 *
 * @version  1.0
 */
public class HomeopathicRole extends IngredientRole {

    private static final String TYPE_E = "Homeopathic";
    private static final String TYPE_F = "Homéopathique";

    private Set<HomeopathicFormula> homeopathicFormulas;

    public Set<HomeopathicFormula> getHomeopathicFormulas() {
        return homeopathicFormulas;
    }


    /**
     * Gets the type in the language appropriate for the Locale from the class
     * constants.
     *
     * @return  the locale-specific type.
     */
    public String getType() {

        if (isLanguageFrench()) {
            return TYPE_F;
        }

        return TYPE_E;
    }

    public void setHomeopathicFormulas(
            Set<HomeopathicFormula> homeopathicFormulas) {
        this.homeopathicFormulas = homeopathicFormulas;
    }
}
