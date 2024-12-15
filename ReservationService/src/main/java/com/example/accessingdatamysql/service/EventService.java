package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.EventEntity;
import com.example.accessingdatamysql.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
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
            "Živinice"
    );

    public EventEntity addEvent(EventEntity event) {

        if (event.getDescription() == null || event.getDescription().length() < 10 || event.getDescription().length() > 1000) {
            throw new IllegalArgumentException("Opis radionice/eventa mora biti dužine između 10 i 1000 znakova.");
        }

        // Validacija datuma (format 'DD.MM.YYYY')
        if (event.getDate() == null || !event.getDate().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            throw new IllegalArgumentException("Datum mora biti u formatu 'DD.MM.YYYY'.");
        }

        if (event.getName() == null || event.getName().length() < 5 || event.getName().length() > 30) {
            throw new IllegalArgumentException("Ime radionice/eventa mora biti dužine između 5 i 30 znakova.");
        }

        if (event.getLocation() == null || !VALID_LOCATIONS.contains(event.getLocation().trim())) {
            throw new IllegalArgumentException("Lokacija mora biti validan grad/opština u Bosni i Hercegovini.");
        }
        return this.eventRepository.save(event);
    }

}
