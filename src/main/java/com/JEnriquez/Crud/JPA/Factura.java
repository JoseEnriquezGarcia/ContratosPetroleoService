package com.JEnriquez.Crud.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private int IdFactura;
    
    @Column(name = "fecha")
    private Date Fecha;
    
    @JoinColumn(name = "idnodocomercialrecepcion")
    @OneToMany
    public List<NodoComercialRecepcion> nodoComercialRecepcion;
    
    @JoinColumn(name = "idnodocomercialentrega")
    @OneToMany
    public List<NodoComercialEntrega> nodoComercialEntrega;
    
    @JoinColumn(name = "zonatarifainyeccion")
    @OneToMany
    public List<Zona> zonaTarifaInyecion;
    
    @JoinColumn(name = "zonatarifaextraccion")
    @OneToMany
    public List<Zona> zonaTarifaExtraccion;
    
    @JoinColumn(name = "idcontrato")
    @OneToOne
    public Contrato contrato;
    
    @JoinColumn(name = "idmontofactura")
    @OneToOne
    public MontosFactura montoFactura;
    
    @Column(name = "totalfactura")
    private double TotalFactura;
}
