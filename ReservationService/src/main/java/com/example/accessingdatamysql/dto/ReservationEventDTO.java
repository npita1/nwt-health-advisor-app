package com.example.accessingdatamysql.dto;

public class ReservationEventDTO {
    private Long reservationId;
    private String eventName;
    private String eventDate;
    private Integer numberOfTickets;

    private String location;

    // Konstruktor
    public ReservationEventDTO(Long reservationId, String eventName, String eventDate, Integer numberOfTickets, String location) {
        this.reservationId = reservationId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.numberOfTickets = numberOfTickets;
        this.location=location;
    }

    // Getteri i setteri
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
