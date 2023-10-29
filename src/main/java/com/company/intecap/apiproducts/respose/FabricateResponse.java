package com.company.intecap.apiproducts.respose;

import com.company.intecap.apiproducts.model.Fabricate;

import java.util.List;

public class FabricateResponse {

    private List<Fabricate> Fabricates;

    public List<Fabricate> getFabricates() {
        return Fabricates;
    }

    public void setFabricates(List<Fabricate> Fabricates) {
        this.Fabricates = Fabricates;
    }
}
