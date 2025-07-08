package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "UGTP_TBL_Nodocomercialrecepcion")
public class NodoComercialRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnodo")
    private int IdNodo;
    
    @JoinColumn(name = "idzonatarifainyeccion")
    @ManyToOne
    public Zona zona;
    
    @Column(name = "clavenodo")
    private String claveNodo;
    
    @Column(name = "descripcion")
    private String Descripcion;
}
