package com.bank.antifraud.Controllers;

import com.bank.antifraud.Services.SuspiciousTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@RequiredArgsConstructor
public abstract class AbstractController<D> {
    protected final SuspiciousTransferService<D> service;

    @PostMapping
    public ResponseEntity<D> create(@Valid @RequestBody D dto) {

        return ResponseEntity.ok(service.createTransfer(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable Long id, @Valid @RequestBody D dto) {
        return ResponseEntity.ok(service.updateTransfer(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        if (id == null || id == 0) {
            return ResponseEntity.noContent().build();
        }
        service.deleteTransfer(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public List<? extends D> getAll() {
        return service.getAllTransfers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable Long id) {
        if (id == null || id == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(service.getTransferById(id));
    }
}
