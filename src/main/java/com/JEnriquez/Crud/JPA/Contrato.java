package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcontrato")
    private int IdContrato;
    
    @JoinColumn(name = "idusuario")
    @ManyToOne
    public Usuario usuario;
    
    @JoinColumn(name = "idnodocomercialrecepcion")
    @ManyToOne
    public NodoComercialRecepcion IdnodoComercialRecepcion;
    
    @JoinColumn(name = "idnodocomercialentrega")
    @ManyToOne
    public NodoComercialEntrega IdnodoComercialEntrega;
    
    @Column(name = "clavecontrato")
    private String ClaveContrato;
}
