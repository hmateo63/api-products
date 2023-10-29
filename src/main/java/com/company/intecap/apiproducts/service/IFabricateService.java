package com.company.intecap.apiproducts.service;

import com.company.intecap.apiproducts.model.Fabricate;
import com.company.intecap.apiproducts.respose.FabricateResponseRest;
import org.springframework.http.ResponseEntity;

public interface IFabricateService {

    public ResponseEntity<FabricateResponseRest> buscarFabricates();

    public ResponseEntity<FabricateResponseRest> buscarFabricateId(Long id);

    public ResponseEntity<FabricateResponseRest> crear(Fabricate Fabricate);

    public ResponseEntity<FabricateResponseRest> actualizar(Fabricate Fabricate,Long id);

    public ResponseEntity<FabricateResponseRest> eliminar(Long id);
}
