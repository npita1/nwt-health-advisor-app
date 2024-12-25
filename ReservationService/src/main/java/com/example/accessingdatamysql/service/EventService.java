package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.EventDTO;
import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.repository.EventRepository;
import com.example.accessingdatamysql.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    private static final List<String> VALID_LOCATIONS = Arrays.asList(
            "Banja Luka",
            "Bihać",
            "Bijeljina",
            "Bosanska Krupa",
            "Brčko",
            "Cazin",
            "Čapljina",
            "Derventa",
            "Doboj",
            "Goražde",
            "Gračanica",
            "Gradačac",
            "Gradiška",
            "Istočno Sarajevo",
            "Konjic",
            "Laktaši",
            "Livno",
            "Lukavac",
            "Ljubuški",
            "Mostar",
            "Novi Travnik",
            "Orašje",
            "Prijedor",
            "Prnjavor",
            "Sarajevo",
            "Srebrenik",
            "Stolac",
            "Široki Brijeg",
            "Trebinje",
            "Tuzla",
            "Visoko",
            "Zavidovići",
            "Zenica",
            "Zvornik",
            "Živinice",
            "Travnik"
    );

    public EventEntity addEvent(EventEntity event) {

        if (event.getDescription() == null || event.getDescription().length() < 10 || event.getDescription().length() > 255) {
            throw new IllegalArgumentException("Opis radionice/eventa mora biti dužine između 10 i 255 znakova.");
        }

        // Validacija datuma (format 'DD.MM.YYYY')
        if (event.getDate() == null || !event.getDate().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            throw new IllegalArgumentException("Datum mora biti u formatu 'DD.MM.YYYY'.");
        }

        if (event.getName() == null || event.getName().length() < 5 || event.getName().length() > 100) {
            throw new IllegalArgumentException("Ime radionice/eventa mora biti dužine između 5 i 100 znakova.");
        }

        if (event.getLocation() == null || !VALID_LOCATIONS.contains(event.getLocation().trim())) {
            throw new IllegalArgumentException("Lokacija mora biti validan grad/opština u Bosni i Hercegovini.");
        }
        return this.eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        // Pronađi event koje treba obrisati
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event s ID-om " + eventId + " ne postoji."));

        // Brisanje svih rezervacija povezanih s eventom
         reservationRepository.deleteAllByEvent(event);

        // Brisanje pitanja
        eventRepository.delete(event);
    }
    public List<EventDTO> getAllEventsAsDTO() {
        List<EventEntity> events = eventRepository.findAll();

        return events.stream().map(event -> {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setName(event.getName());
            dto.setLocation(event.getLocation());
            dto.setDate(event.getDate());
            dto.setDescription(event.getDescription());
            dto.setDoctorFirstName(event.getDoctorInfo().getUser().getFirstName());
            dto.setDoctorLastName(event.getDoctorInfo().getUser().getLastName());
            return dto;
        }).collect(Collectors.toList());
    }

}
