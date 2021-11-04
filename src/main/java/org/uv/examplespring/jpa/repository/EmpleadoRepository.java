/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uv.examplespring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.examplespring.jpa.entity.Empleado;

/**
 *
 * @author JAHAZIEL BH
 */
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
}
