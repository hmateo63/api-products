package com.company.intecap.apiproducts.service;

import com.company.intecap.apiproducts.model.Producto;
import com.company.intecap.apiproducts.respose.ProductoResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductoService {
    //Interface para acceder a los datos de la tabla producto de la base de datos (CRUD) definicion de metodos
    //listar todos los productos
    public ResponseEntity<ProductoResponseRest> buscarProductos(); //metodo para buscar todos los productos de la base de datos

    //buscar por id
     public ResponseEntity<ProductoResponseRest> buscarProductoPorId(Long id); //metodo para buscar un producto por su id

    //guardar un producto
    public ResponseEntity<ProductoResponseRest> crear(Producto producto);

    //actualizar un producto

    public ResponseEntity<ProductoResponseRest> actualizar(Producto producto, Long id);

    //eliminar un producto
    public ResponseEntity<ProductoResponseRest> eliminar(Long id);


}
