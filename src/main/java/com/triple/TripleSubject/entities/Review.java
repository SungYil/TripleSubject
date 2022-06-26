package com.triple.TripleSubject.entities;

import com.triple.TripleSubject.enums.ReviewState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@SuperBuilder
@Table(indexes = @Index(name="idx_creator_place",columnList = "creator_id,place_id"))
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String uuid;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User creator;

    @Column
    private ReviewState state;

    @Column
    long accessCount;
}
