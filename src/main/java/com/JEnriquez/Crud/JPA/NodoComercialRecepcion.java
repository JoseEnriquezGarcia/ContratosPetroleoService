package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class NodoComercialRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnodo")
    private int IdNodo;
    
    @JoinColumn(name = "idzonatarifainyeccion")
    @OneToMany
    private List<Zona> IdZonaTarifaInyeccion;
    
    @Column(name = "clavenodo")
    private String ClaveNodo;
    
    @Column(name = "descripcion")
    private String Descripcion;
}
