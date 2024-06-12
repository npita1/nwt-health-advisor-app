import React, { useRef, useState, useEffect } from 'react';
import { Flex, Button, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton, useDisclosure, Select } from '@chakra-ui/react';
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import '../styles/Event.css';
import L from 'leaflet';
import osm from '../resources/osm-providers';
import { getAllEvents, addReservation } from '../services/reservationService';

function Event() {
    const [center, setCenter] = useState({ lat: 43.8663, lng: 18.4031 });
    const [events, setEvents] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [numTickets, setNumTickets] = useState(1);
    const ZOOM_LEVEL = 13;
    const mapRef = useRef();
    const position = [43.854168, 18.392567];

    const customIcon = L.icon({
        iconUrl: "images/EventPage/location.png",
        iconSize: [32, 32], // size of the icon
        iconAnchor: [16, 32], // point of the icon which will correspond to marker's location
        popupAnchor: [0, -32] // point from which the popup should open relative to the iconAnchor
    });

    const { isOpen, onOpen, onClose } = useDisclosure();

    useEffect(() => {
        async function fetchEvents() {
            try {
                const eventsData = await getAllEvents();
                setEvents(eventsData);
            } catch (error) {
                console.error('Error fetching events:', error);
            }
        }
        fetchEvents();
    }, []);

    const handleReserveClick = (event) => {
        setSelectedEvent(event);
        onOpen();
    };

    const handleReservation = async () => {

        try {
        const userId = localStorage.getItem('userId');
        console.log('User ID:', userId);
        
        if(userId == null || userId === "") {
          alert("You need to be logged in to make a reservation. Please log in to continue.");
          return;
        }

        const eventReservationData = {
            id: 0,
            numOfTicket: parseInt(numTickets, 10),
            event: {
                id: parseInt(selectedEvent.id),
                location: "string",
                name: "string",
                description: "string",
                date: "12.12.2024",
                doctorInfo: {
                  id: 0,
                  about: "string",
                  specialization: "string",
                  user: {
                    id: 0,
                    email: "string",
                    firstName: "string",
                    lastName: "string",
                    type: 0,
                    password: "string"
                  }
                }
              },
              user: {
                id: userId,
                email: "string",
                firstName: "string",
                lastName: "string",
                type: 0,
                password: "string"
              }
          };
        
        //console.log(JSON.stringify(eventReservationData, null, 2));

        const addedReservation = await addReservation(eventReservationData);
        alert("Event reservation successful!");
        
        } catch (error) {
            console.error('Greška prilikom dodavanja rezervacije:', error);
            alert("Greska se desila");
        }
        
        onClose();
    };

    return (
        <div>
            <div className="kartaDiv">
                <MapContainer center={center} zoom={ZOOM_LEVEL} ref={mapRef} className="leaflet-container" scrollWheelZoom={false}>
                    <TileLayer url={osm.maptiler.url}></TileLayer>
                    <Marker position={position} icon={customIcon}>
                        <Popup>Importance of physical activity</Popup>
                    </Marker>
                </MapContainer>
            </div>
            <div className='eventiDiv'>
                <Flex direction="column" className='sviEventiFlex'>
                    {events.length > 0 ? (
                        events.map((event, index) => (
                            <Flex key={index} className='eventItem' position="relative">
                                <img src="images/EventPage/physical.jpg" className='slikaEvent' alt="Slika" style={{ width: '300px', height: 'auto' }} />
                                <Flex direction="column">
                                    <h3 className='naslovEventa'>{event.name}</h3>
                                    <Flex direction="column" className='informacije'>
                                        <Flex className='infoFlex'>
                                            <img src="images/EventPage/location.png" alt="Location Icon" style={{ width: '20px', height: 'auto' }} />
                                            <p className='lokDatDokTekst'>{event.location}</p>
                                        </Flex>
                                        <Flex className='infoFlex'>
                                            <img src="images/EventPage/calendar.png" alt="Calendar Icon" style={{ width: '20px', height: 'auto' }} />
                                            <p className='lokDatDokTekst'>{event.date}</p>
                                        </Flex>
                                        <Flex className='infoFlex'>
                                            <img src="images/EventPage/lecturer.png" alt="Lecturer Icon" style={{ width: '20px', height: 'auto' }} />
                                            <p className='lokDatDokTekst'>Dr. {event.doctorInfo.user.firstName} {event.doctorInfo.user.lastName}</p>
                                        </Flex>
                                    </Flex>
                                    <p className='opisEventa'>{event.description}</p>
                                </Flex>
                                <Button 
                                    position="absolute" 
                                    size='sm'
                                    bottom="20px" 
                                    right="20px" 
                                    colorScheme="#FF585F"
                                    className='dugmeRezervacijaEvent'
                                    onClick={() => handleReserveClick(event)}>
                                    Reserve Spot
                                </Button>
                            </Flex>
                        ))
                    ) : (
                        <p>No events available</p>
                    )}
                </Flex>
            </div>

            {selectedEvent && (
                <Modal isOpen={isOpen} onClose={onClose}>
                    <ModalOverlay />
                    <ModalContent>
                        <ModalHeader>Reserve Spot for {selectedEvent.name}</ModalHeader>
                        <ModalCloseButton />
                        <ModalBody>
                            <Select value={numTickets} onChange={(e) => setNumTickets(e.target.value)}>
                                {[...Array(10)].map((_, i) => (
                                    <option key={i} value={i + 1}>{i + 1}</option>
                                ))}
                            </Select>
                        </ModalBody>
                        <ModalFooter>
                            <Button colorScheme="blue" mr={3} onClick={handleReservation}>
                                Reserve
                            </Button>
                            <Button variant="ghost" onClick={onClose}>Cancel</Button>
                        </ModalFooter>
                    </ModalContent>
                </Modal>
            )}
        </div>
    );
}

export default Event;
