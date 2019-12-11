package ca.gc.hc.nhpd.model;

import java.util.Set;

/* 
 * Interface for Source Materials which exposes accessors for the source
 * material name.
 * 
 * Known implementing classes: TextMonographSourceMaterial, MonographSourceIngredient,
 * MonographSourceOrganismPart  
 */
public interface MonographSourceMaterial {
       
    public String getSourceMaterialName();
    
    public String getSourceMaterialNameE();
    
    public String getSourceMaterialNameF();
    
    public Set<Citation> getCitations();
    
}
