package com.company.intecap.apiproducts.model.dao;

import com.company.intecap.apiproducts.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoDao extends JpaRepository<Producto, Long> {

}
