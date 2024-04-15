package edu.poly.medistock.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMINZ
 */
@XmlRootElement(name = "pharmeds")
@XmlAccessorType(XmlAccessType.FIELD)
public class PharMedXML {
    
    private List<PharMed> pharmed;

    public List<PharMed> getPharmed() {
        return pharmed;
    }

    public void setPharmed(List<PharMed> pharmed) {
        this.pharmed = pharmed;
    }
    
    
}
