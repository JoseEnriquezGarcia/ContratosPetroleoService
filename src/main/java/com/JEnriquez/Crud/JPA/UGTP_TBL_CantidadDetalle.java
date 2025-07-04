package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class UGTP_TBL_CantidadDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcantidaddetalle")
    private int IdCantidadDetalle;
    
    @Column(name = "nominadarecepcion")
    private double NominadaRecepcion;
    
    @Column(name = "asignadarecepcion")
    private double AsignadaRecepcion;
    
    @Column(name = "nominadaentrega")
    private double NominadaEntrega;
    
    @Column(name = "asignadaentrega")
    private double AsignadaEntrega;
}
