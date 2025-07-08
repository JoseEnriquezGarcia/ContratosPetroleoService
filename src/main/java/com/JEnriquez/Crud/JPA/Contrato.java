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
@Table(name = "UGTP_TBL_Contrato")
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
    public NodoComercialRecepcion nodoComercialRecepcion;
    
    @JoinColumn(name = "idnodocomercialentrega")
    @ManyToOne
    public NodoComercialEntrega nodoComercialEntrega;
    
    @Column(name = "clavecontrato")
    private String claveContrato;
}
