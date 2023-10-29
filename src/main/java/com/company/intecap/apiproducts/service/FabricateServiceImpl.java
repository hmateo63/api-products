package com.company.intecap.apiproducts.service;

import com.company.intecap.apiproducts.model.Fabricate;
import com.company.intecap.apiproducts.model.dao.IFabricateDao;
import com.company.intecap.apiproducts.respose.FabricateResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class FabricateServiceImpl implements IFabricateService {

    private static final Logger log = Logger.getLogger(FabricateServiceImpl.class.getName());

    @Autowired //Inyeccion de dependencias
    private IFabricateDao FabricateDao; //Instancia de la interface IFabricateDao Jpa

    @Override
    @Transactional(readOnly = true) //Metodo para buscar todas las Fabricates de la base de datos
    public ResponseEntity<FabricateResponseRest> buscarFabricates() {
        log.info("Inicio metodo buscarFabricates()");

        FabricateResponseRest response = new FabricateResponseRest();

        try {
            List<Fabricate> listaFabricates = (List<Fabricate>) FabricateDao.findAll();
            response.getFabricateResponse().setFabricates(listaFabricates);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");

        } catch (Exception e) {
            log.info("Error al consultar Fabricates: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al consultar Fabricates");
            return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500
        }

        return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional(readOnly = true) //Metodo para buscar una Fabricate por su id
    public ResponseEntity<FabricateResponseRest> buscarFabricateId(Long id) {
        log.info("Inicio metodo buscarFabricateId()");

        FabricateResponseRest response = new FabricateResponseRest();
        List<Fabricate> listaFabricates = new ArrayList<>();

        try {
            Optional<Fabricate> Fabricate = FabricateDao.findById(id);

            if (Fabricate.isPresent()) {
                listaFabricates.add(Fabricate.get());
                response.getFabricateResponse().setFabricates(listaFabricates);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            } else {
                response.setMetadata("Respuesta no ok", "404", "Fabricate no encontrada");
                return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
            }
        } catch (Exception e) {
            log.info("Error al consultar Fabricate: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al consultar Fabricate");
            return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500
        }
        return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional
    public ResponseEntity<FabricateResponseRest> crear(Fabricate Fabricate) {
        log.info("Inicio metodo crear()");

        FabricateResponseRest response = new FabricateResponseRest();
        List<Fabricate> listaFabricates = new ArrayList<>();

        try {
            Fabricate FabricateGuardada = FabricateDao.save(Fabricate);
            if (FabricateGuardada != null) {
                listaFabricates.add(FabricateGuardada);
                response.getFabricateResponse().setFabricates(listaFabricates);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            } else {
                log.info("Error al guardar Fabricate");
                response.setMetadata("Respuesta no ok", "404", "Fabricate no encontrada");
                return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
            }
        } catch (Exception e) {
            log.severe("Error al guardar Fabricate: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al guardar Fabricate");
            return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500

        }
        return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional
    public ResponseEntity<FabricateResponseRest> actualizar(Fabricate Fabricate,Long id) {
        log.info("Inicio metodo actualizar()");

        FabricateResponseRest response = new FabricateResponseRest();
        List<Fabricate> listaFabricates = new ArrayList<>();

        try {
            Optional<Fabricate> FabricateBuscada = FabricateDao.findById(id);

            if (FabricateBuscada.isPresent()) {
                FabricateBuscada.get().setNombre(Fabricate.getNombre());
                FabricateBuscada.get().setDescripcion(Fabricate.getDescripcion());
                Fabricate FabricateActualizada = FabricateDao.save(FabricateBuscada.get());

                if (FabricateActualizada != null) { //Si la Fabricate se actualiza correctamente
                    listaFabricates.add(FabricateActualizada);
                    response.getFabricateResponse().setFabricates(listaFabricates);
                    response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
                } else {
                    log.info("Error al actualizar Fabricate");
                    response.setMetadata("Respuesta no ok", "404", "Fabricate no encontrada");
                    return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
                }
            } else {
                log.info("Error al actualizar Fabricate");
                response.setMetadata("Respuesta no ok", "404", "Fabricate no encontrada");
                return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
            }

        } catch (Exception e) {
            log.severe("Error al actualizar Fabricate: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al actualizar Fabricate");
            return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 50
        }

        return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional
    public ResponseEntity<FabricateResponseRest> eliminar(Long id) {
        log.info("Inicio metodo eliminar()");

        FabricateResponseRest response = new FabricateResponseRest();

        try {
            Optional<Fabricate> FabricateBuscada = FabricateDao.findById(id);

            if (FabricateBuscada.isPresent()) {
                FabricateDao.deleteById(id);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            } else {
                log.info("Error al eliminar Fabricate " + id);
                response.setMetadata("Respuesta no ok", "404", "Fabricate no encontrada " + id);
                return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
            }

        } catch (Exception e) {
            log.severe("Error al eliminar Fabricate: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al eliminar Fabricate");
            return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500

        }
        return new ResponseEntity<FabricateResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }
}
