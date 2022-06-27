package com.triple.TripleSubject.entities;

import com.triple.TripleSubject.converters.EventJsonConverter;
import com.triple.TripleSubject.dtos.EventDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@SuperBuilder
@Table(indexes = @Index(name="idx_user_place",columnList = "user_id,place_id"))
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column
    private int pointDelta;

    @Column(columnDefinition = "json")
    @Convert(converter = EventJsonConverter.class)
    private EventDto event;
}
