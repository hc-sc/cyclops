package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.TextMonographSourceMaterial;

public class TextMonographSourceMaterialWS implements Comparable<TextMonographSourceMaterialWS> {
    // The wrapped TextMonographSourceMaterial
    private TextMonographSourceMaterial textMonographSourceMaterial;

    public TextMonographSourceMaterialWS(
            TextMonographSourceMaterial textMonographSourceMaterial) {
        this.textMonographSourceMaterial = textMonographSourceMaterial;
    }

    public TextMonographSourceMaterialWS() {
        textMonographSourceMaterial = new TextMonographSourceMaterial();
    }

    /*
     * delegate to the contained? (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(TextMonographSourceMaterialWS obj) {
        // delegate to wrapped type
        return textMonographSourceMaterial
                .compareTo(obj.textMonographSourceMaterial);

    }

    public String getSourceMaterialName() {
        return textMonographSourceMaterial.getSourceMaterialName();
    }

    public void setSourceMaterialName(String sourceMaterialName) {
        //
    }

}
