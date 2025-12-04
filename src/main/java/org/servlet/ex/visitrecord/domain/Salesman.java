package org.servlet.ex.visitrecord.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private SalesmanStatus status;

}

