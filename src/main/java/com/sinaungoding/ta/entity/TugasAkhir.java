package com.sinaungoding.ta.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TugasAkhir {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nim;
    @Column(columnDefinition = "text", nullable = false)
    private String judul;
    @Column(nullable = false)
    private String jenjang;
}
