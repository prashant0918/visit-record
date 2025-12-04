package org.servlet.ex.visitrecord.service;

import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Salesman;
import org.servlet.ex.visitrecord.domain.SalesmanStatus;
import org.servlet.ex.visitrecord.dto.CreateSalesmanRequest;
import org.servlet.ex.visitrecord.repository.SalesmanRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesmanService {

    private final SalesmanRepository salesmanRepository;

    public Salesman create(CreateSalesmanRequest req) {
        Salesman s = new Salesman();
        s.setName(req.getName());
        s.setPhone(req.getPhone());
        s.setEmail(req.getEmail());
        s.setStatus(req.getStatus() == null
                ? SalesmanStatus.ACTIVE
                : req.getStatus()
        );
        return salesmanRepository.save(s);
    }
}


