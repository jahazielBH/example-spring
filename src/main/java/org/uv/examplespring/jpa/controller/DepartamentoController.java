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
import org.uv.examplespring.jpa.entity.Departamento;
import org.uv.examplespring.jpa.repository.DepartamentoRepository;

/**
 *
 * @author JAHAZIEL BH
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/crud")
public class DepartamentoController {
    
    @Autowired
    DepartamentoRepository departamentoRepository;

    @GetMapping("/departamento")
    public ResponseEntity<List<Departamento>> getAllDepartamentos() {
        try {
            List<Departamento> departamentos = new ArrayList<Departamento>();
            
            departamentoRepository.findAll().forEach(departamentos::add);

            if (departamentos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(departamentos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/departamento/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable("id") long id) {
        Optional<Departamento> departamentoData = departamentoRepository.findById(id);

        if (departamentoData.isPresent()) {
            return new ResponseEntity<>(departamentoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/departamento")
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) {
        try {
            Departamento _departamento = departamentoRepository
                    .save(new Departamento(departamento.getId(),departamento.getNombre()));
            return new ResponseEntity<>(_departamento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/departamento/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable("id") long id, @RequestBody Departamento departamento) {
        Optional<Departamento> departamentoData = departamentoRepository.findById(id);

        if (departamentoData.isPresent()) {
            Departamento _departamento = departamentoData.get();
            _departamento.setNombre(departamento.getNombre());
            return new ResponseEntity<>(departamentoRepository.save(_departamento), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/departamento/{id}")
    public ResponseEntity<HttpStatus> deleteDepartamento(@PathVariable("id") long id) {
        try {
            departamentoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
