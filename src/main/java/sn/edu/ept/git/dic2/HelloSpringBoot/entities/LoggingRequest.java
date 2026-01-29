package sn.edu.ept.git.dic2.HelloSpringBoot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LoggingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "status")
    private Integer status;

    @Column(name = "query_params")
    private String queryParams;

    @Column(name = "adresse_ip")
    private String adresseIP;
}