package com.bootcamp.cdd.controllers;

import com.bootcamp.cdd.models.Country;
import com.bootcamp.cdd.models.Shopping;
import com.bootcamp.cdd.models.State;
import com.bootcamp.cdd.request.ShoppingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/shopping")
public class ShoppingController {
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Shopping createShopping (@Valid @RequestBody ShoppingRequest request) {
        Assert.isTrue(verifyDocs(request.getDocumento()), "o documento foi preenchido de forma incorreta");
        Country pais = entityManager.find(Country.class, request.getPais());
        State estado = entityManager.find(State.class, request.getEstadoId());
        Assert.state(verifyState(estado, pais, request), "A localização foi preenchida incorretamente");
        Assert.state(pais != null, "Pais não encontrado");
        Shopping compra = request.toModel();
        compra.setEstadoAndPais(estado, pais);
        entityManager.persist(compra);
        return null;
    }

    public boolean verifyState (State estado, Country pais, ShoppingRequest request) {
        if (estado == null && request.getEstadoId() != null) {
            return false;
        }
        return estado == null || estado.getCountryId() == pais.getId();
    }

    public boolean verifyDocs (String document) {
        boolean isCpf = document.length() == 11;
        boolean isCnpj = document.length() == 14;
        return isCpf || isCnpj;
    }
}