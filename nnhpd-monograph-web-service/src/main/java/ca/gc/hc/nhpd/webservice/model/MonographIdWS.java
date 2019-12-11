/**

 */

package ca.gc.hc.nhpd.webservice.model;

public class MonographIdWS {
    private Float version;

    private String monographName;

    private Long id;

    public MonographIdWS() {
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonographName() {
        return monographName;
    }

    public void setMonographName(String monographName) {
        this.monographName = monographName;
    }

}
