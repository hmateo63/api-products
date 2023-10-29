package com.company.intecap.apiproducts.controller;

import com.company.intecap.apiproducts.model.Producto;
import com.company.intecap.apiproducts.respose.ProductoResponseRest;
import com.company.intecap.apiproducts.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") // @RequestMapping: prefijo de la ruta de la API REST  http://localhost:8083/api/v1
public class ProductoRestController {

    @Autowired // @Autowired: inyecta el servicio de productos para poder utilizarlo en este controlador REST
    private IProductoService productoService; // IProductoService: interface que contiene los metodos para acceder a los datos de la tabla producto de la base de datos (CRUD)

    @GetMapping("/productos") // localhost:8083/api/v1/productos
    // @GetMapping: indica que este metodo se encarga de recibir las peticiones GET a la ruta /v1/productos
    public ResponseEntity<ProductoResponseRest> buscarProductos() { //http://localhost:8083/api/v1/productos
        return productoService.buscarProductos(); // Retornamos la respuesta al cliente
    }

    @GetMapping("/productos/{id}")
    // @GetMapping: indica que este metodo se encarga de recibir las peticiones GET a la ruta /v1/productos/{id}
    public ResponseEntity<ProductoResponseRest> consultarPorId(@PathVariable Long id) {  //  http://localhost:8083/api/v1/productos/1
        return productoService.buscarProductoPorId(id);
    }

    @PostMapping("/productos")
    // @PostMapping: indica que este metodo se encarga de recibir las peticiones POST a la ruta /v1/productos
    public ResponseEntity<ProductoResponseRest> guardarProducto(@RequestBody Producto request) {
        return productoService.crear(request);
    }

    @PutMapping("/productos/{id}")
    // @PutMapping: indica que este metodo se encarga de recibir las peticiones PUT a la ruta /v1/productos
    public ResponseEntity<ProductoResponseRest> actualizarProducto(@RequestBody Producto request, @PathVariable Long id) {
        return productoService.actualizar(request, id);

    }

    @DeleteMapping("/productos/{id}")
    // @DeleteMapping: indica que este metodo se encarga de recibir las peticiones DELETE a la ruta /v1/productos/{id}
    public ResponseEntity<ProductoResponseRest> eliminarProducto(@PathVariable Long id) {
        return productoService.eliminar(id);
    }


}
