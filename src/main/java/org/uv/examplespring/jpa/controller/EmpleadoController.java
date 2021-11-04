/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uv.examplespring.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.examplespring.jpa.entity.Empleado;
import org.uv.examplespring.jpa.repository.EmpleadoRepository;

/**
 *
 * @author JAHAZIEL BH
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/crud")
public class EmpleadoController {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping("/empleado")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        try {
            List<Empleado> empleados = new ArrayList<Empleado>();
            
            empleadoRepository.findAll().forEach(empleados::add);

            if (empleados.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(empleados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable("id") long id) {
        Optional<Empleado> empleadoData = empleadoRepository.findById(id);

        if (empleadoData.isPresent()) {
            return new ResponseEntity<>(empleadoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado _empleado = empleadoRepository
                    .save(new Empleado(empleado.getId(),empleado.getNombre(), empleado.getDireccion(), empleado.getTelefono()));
            return new ResponseEntity<>(_empleado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/empleado/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable("id") long id, @RequestBody Empleado empleado) {
        Optional<Empleado> empleadoData = empleadoRepository.findById(id);

        if (empleadoData.isPresent()) {
            Empleado _empleado = empleadoData.get();
            _empleado.setNombre(empleado.getNombre());
            _empleado.setDireccion(empleado.getDireccion());
            _empleado.setTelefono(empleado.getTelefono());
            return new ResponseEntity<>(empleadoRepository.save(_empleado), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<HttpStatus> deleteEmpleado(@PathVariable("id") long id) {
        try {
            empleadoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
