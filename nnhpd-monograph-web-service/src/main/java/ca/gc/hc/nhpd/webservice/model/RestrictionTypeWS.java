package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.RestrictionType;

public class RestrictionTypeWS {
    // the wrapped RestrictionType
    private RestrictionType restrictionType;

    public RestrictionTypeWS(RestrictionType restrictionType) {
        // create interim objects to avoid JAX-WS NullPointerExceptions...
        if (restrictionType == null) {
            this.restrictionType = new RestrictionType();
        } else
            this.restrictionType = restrictionType;
    }

    public RestrictionTypeWS() {
        restrictionType = new RestrictionType();
    }

    public String getCode() {
        return restrictionType.getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        return restrictionType.getName();
    }

    public void setName(String name) {
        //
    }

}
