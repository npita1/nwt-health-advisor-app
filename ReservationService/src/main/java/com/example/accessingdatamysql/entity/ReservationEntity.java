
package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Table(name="reservation")

@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull(message = "NumOfTickets is mandatory")
    @Min(value = 1, message = "The number of ticket must be a positive integer")
    private Integer numOfTicket;
    @NotNull(message = "Event is mandatory")
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private EventEntity event;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public ReservationEntity(Integer numOfTicket) {
        this.numOfTicket = numOfTicket;
    }

    public ReservationEntity() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfTicket() {
        return numOfTicket;
    }

    public void setNumOfTicket(Integer numOfTicket) {
        this.numOfTicket = numOfTicket;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return String.format(
                "ReservationEntity[id=%d, numOfTicket=%d]",
                id, numOfTicket);
    }
}


