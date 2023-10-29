package com.company.intecap.apiproducts.service;

import com.company.intecap.apiproducts.model.Producto;
import com.company.intecap.apiproducts.model.dao.IProductoDao;
import com.company.intecap.apiproducts.respose.ProductoResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service //anotacion para indicar que es un servicio
public class ProductoServiceImpl implements IProductoService {

    private static final Logger log = Logger.getLogger(ProductoServiceImpl.class.getName());

    @Autowired //@Autowired: inyecta el repositorio de productos para poder utilizarlo en este servicio
    private IProductoDao productoDao; //variable de tipo IProductoDao para acceder a los metodos de la interface IProductoDao

    @Override
    @Transactional(readOnly = true) //anotacion para indicar que es un metodo de solo lectura
    public ResponseEntity<ProductoResponseRest> buscarProductos() {
        log.info("inicio metodo buscarProductos");

        ProductoResponseRest response = new ProductoResponseRest(); //creacion de un objeto de tipo ProductoResponseRest

        try {
            List<Producto> listProductos = (List<Producto>) productoDao.findAll(); //creacion de una lista de productos para guardar los productos que se encuentren en la base de datos
            response.getProductoResponse().setProductos(listProductos); //se asigna la lista de productos al objeto de tipo ProductoResponseRest
            response.setMetadata("Respuesta exitosa", "200", "Lista de Productos"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
        } catch (Exception e) {
            log.severe("Error en el metodo buscarProductos: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Respuesta no exitosa", "500", "Error al consultar la lista de productos"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest

            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500

        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductoResponseRest> buscarProductoPorId(Long id) {
        log.info("inicio metodo buscarProductoPorId");

        ProductoResponseRest response = new ProductoResponseRest(); //creacion de un objeto de tipo ProductoResponseRest
        List<Producto> list = new ArrayList<>();

        try {
            Optional<Producto> producto = productoDao.findById(id); //se busca un producto por su id
            if (producto.isPresent()) {
                list.add(producto.get()); //se agrega el producto a la lista
                response.getProductoResponse().setProductos(list); //se asigna la lista de productos al objeto de tipo ProductoResponseRest
                response.setMetadata("Respuesta exitosa", "200", "Producto encontrado"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            } else {
                log.severe("No se encontro el producto con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el producto
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el producto con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 404
            }
        } catch (Exception e) {
            log.severe("Errror al buscar el producto : " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al buscar el producto", "500", "Error al consultar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> crear(Producto producto) {
        log.info("Iniciando el metodo metodo crear()  ");

        ProductoResponseRest response = new ProductoResponseRest(); //creacion de un objeto de tipo ProductoResponseRest
        List<Producto> list = new ArrayList<>();

        try {
            Producto productoGuardado = productoDao.save(producto); //se guarda el producto en la base de datos
            if (productoGuardado != null) {
                list.add(productoGuardado); //se agrega el producto a la lista
                response.getProductoResponse().setProductos(list); //se asigna la lista de productos al objeto de tipo ProductoResponseRest
                response.setMetadata("Respuesta exitosa", "200", "Producto creado"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            } else {
                log.severe("No se pudo guardar el producto"); //se muestra un mensaje de error en caso de que no se pueda guardar el producto
                response.setMetadata("Error al guardar el producto", "500", "No se pudo guardar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500
            }

        } catch (Exception e) {
            log.severe("Error al guardar el producto: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al guardar el producto", "500", "Error al guardar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    //commmit y rollback: si ocurre un error en la transaccion se hace un rollback y si no se hace un commit
    public ResponseEntity<ProductoResponseRest> actualizar(Producto producto, Long id) {
        log.info("Iniciando el metodo actualizar()  ");

        ProductoResponseRest response = new ProductoResponseRest(); //creacion de un objeto de tipo ProductoResponseRest
        List<Producto> list = new ArrayList<>();

        try {
            Optional<Producto> productoBuscado = productoDao.findById(id); //se busca un producto por su id

            if (productoBuscado.isPresent()) {
                productoBuscado.get().setNombre(producto.getNombre()); //se asigna el nombre del producto que se quiere actualizar
                productoBuscado.get().setDescripcion(producto.getDescripcion()); //se asigna la descripcion del producto que se quiere actualizar

                Producto productoActualizado = productoDao.save(productoBuscado.get()); //se actualiza el producto en la base de datos

                if (productoActualizado != null) { // si el producto se actualizo correctamente
                    list.add(productoActualizado); //se agrega el producto a la lista
                    response.getProductoResponse().setProductos(list); //se asigna la lista de productos al objeto de tipo ProductoResponseRest
                    response.setMetadata("Respuesta exitosa", "200", "Producto actualizado"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRes
                } else {
                    log.severe("No se pudo actualizar el producto"); //se muestra un mensaje de error en caso de que no se pueda actualizar el producto
                    response.setMetadata("Error al actualizar el producto", "500", "No se pudo actualizar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
                    return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500
                }
            } else {
                log.severe("No se encontro el producto con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el producto
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el producto con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 404
            }


        } catch (Exception e) {
            log.severe("Error al actualizar el producto: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al actualizar el producto", "500", "Error al actualizar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500
        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> eliminar(Long id) {
        log.info("Iniciando el metodo eliminar()  ");

        ProductoResponseRest response = new ProductoResponseRest(); //creacion de un objeto de tipo ProductoResponseRest

        try {

            Optional<Producto> productoBuscado = productoDao.findById(id); //se busca un producto por su id

            if (productoBuscado.isPresent()) {
                productoDao.delete(productoBuscado.get()); //se elimina el producto de la base de datos
                response.setMetadata("Respuesta exitosa", "200", "Producto eliminado"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            } else {
                log.severe("No se encontro el producto con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el producto
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el producto con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 404
            }

        } catch (Exception e) {
            log.severe("Error al eliminar el producto: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al eliminar el producto", "500", "Error al eliminar el producto"); //se asigna el mensaje de respuesta al objeto de tipo ProductoResponseRest
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 500

        }

        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo ProductoResponseRest y el codigo de estado 200
    }

}
