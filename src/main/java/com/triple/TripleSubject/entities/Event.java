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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column
    private int pointDelta;

    @Column(columnDefinition = "json")
    @Convert(converter = EventJsonConverter.class)
    private EventDto event;
}
