package com.company.intecap.apiproducts.respose;

public class ProductoResponseRest extends ResponseRest { // ResponseRest: contiene toda la estructura de  la metada la respuesta de la api


    private ProductoResponse productoResponse = new ProductoResponse(); // devolvera una lista de productos en formato json con la estructura de la clase ProductoResponse y la metadata de la clase ResponseRest

    public ProductoResponse getProductoResponse() {
        return productoResponse;
    }

    public void setProductoResponse(ProductoResponse productoResponse) {
        this.productoResponse = productoResponse;
    }

}
