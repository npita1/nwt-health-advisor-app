
package com.example.accessingdatamysql;

import jakarta.persistence.*;


@Table(name="reservation")

@Entity
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Integer numOfTicket;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private EventEntity event;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;


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
}


