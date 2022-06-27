package com.triple.TripleSubject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@SuperBuilder
@Table(indexes = @Index(name="idx_uuid",columnList = "uuid"))
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String uuid;
}
