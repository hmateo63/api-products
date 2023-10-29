package com.company.intecap.apiproducts.controller;

import com.company.intecap.apiproducts.model.Fabricate;
import com.company.intecap.apiproducts.respose.FabricateResponseRest;
import com.company.intecap.apiproducts.service.IFabricateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1") // prefijo de la ruta  http://localhost:8080/api/v1
public class FabricateRestController {

    @Autowired // Inyectamos el servicio de fabricates para poder utilizarlo en este controlador REST
    private IFabricateService fabricateService;

    @GetMapping("/fabricates") // localhost:8080/api/v1/fabricates
    public ResponseEntity<FabricateResponseRest> consultarFabricates(){
        return fabricateService.buscarFabricates(); // Invocamos el metodo buscarFabricates del servicio de fabricates para obtener las info de la base de datos
    }

    @GetMapping("/fabricates/{id}") // Indica que este metodo se encarga de recibir las peticiones GET a la ruta /v1/fabricates/{id}
    public ResponseEntity<FabricateResponseRest> consultarFabricateId(@PathVariable  Long id){
        return fabricateService.buscarFabricateId(id); // Invocamos el metodo buscarFabricateId del servicio de fabricates para obtener la info de la base de datos
    }

    @PostMapping("/fabricates") // Indica que este metodo se encarga de recibir las peticiones POST a la ruta /v1/fabricates
    public ResponseEntity<FabricateResponseRest> guardarFabricate(@RequestBody Fabricate request){
        return fabricateService.crear(request); // Invocamos el metodo crear del servicio de fabricates para guardar la info en la base de datos
    }

    @PutMapping("/fabricates/{id}")
    public ResponseEntity<FabricateResponseRest> actualizarFabricate(@RequestBody Fabricate request, @PathVariable Long id) {
        return fabricateService.actualizar(request, id);

    }
    @DeleteMapping("/fabricates/{id}")
    public ResponseEntity<FabricateResponseRest> eliminarFabricate(@PathVariable Long id) {
        return fabricateService.eliminar(id);
    }

}
