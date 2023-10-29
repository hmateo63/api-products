package com.company.intecap.apiproducts.respose;

import com.company.intecap.apiproducts.model.Producto;

import java.util.List;

public class ProductoResponse {
    private List<Producto> productos;

    public List<Producto> getProductos() {
        // getProductos: obtiene la información de la respuesta.
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        // setProductos: establece la información de la respuesta.
        this.productos = productos;
    }
}
