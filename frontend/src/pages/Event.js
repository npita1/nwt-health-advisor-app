import React, { useRef, useState, useEffect } from 'react';
import { Flex, Button, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, Text,ModalCloseButton, useDisclosure, Select } from '@chakra-ui/react';
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import '../styles/Event.css';
import L from 'leaflet';
import osm from '../resources/osm-providers';
import { getAllEvents, addReservation,deleteEvent } from '../services/reservationService';
import AddEvent from "../components/AddEvent";
function Event() {
    const [center, setCenter] = useState({ lat: 43.8663, lng: 18.4031 });
    const [events, setEvents] = useState([]);
    const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
    const [showAddEventForm, setShowAddEventForm] = useState(false); // Dodano stanje za prikaz AddEvent forme
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [numTickets, setNumTickets] = useState(1);
    const ZOOM_LEVEL = 13;
    const mapRef = useRef();
    const position = [43.854168, 18.392567];
    const [deleteModal, setDeleteModal] = useState({ isOpen: false, type: null, id: null });
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
                date: "73K68a4424",
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
                password: "string",
                userServiceId: 0

              }
          };
        
        //console.log(JSON.stringify(eventReservationData, null, 2));

        const addedReservation = await addReservation(eventReservationData);
        alert("Event reservation successful!");
        localStorage.setItem('selectedTabIndex', "5");
        window.location.reload();
        } catch (error) {
            console.error('Greška prilikom dodavanja rezervacije:', error);
            
        }
        
        onClose();
    };
    const handleDelete = async () => {
        const { type, id } = deleteModal;
        try {
          if (type === 'event') {
           
            const response = await deleteEvent(id);
            console.log(response.message); 
            setEvents(prev => prev.filter(event => event.id !== id));
          } 
          // Zatvaranje modala nakon uspješnog brisanja
          setDeleteModal({ isOpen: false, type: null, id: null });
        } catch (error) {
          // Obrada greške
          console.error('Error deleting item:', error.message || error);
        }
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

            {(userRole==="DOCTOR") ? (
                <>
            {/* Dugme za otvaranje forme AddEvent */}
            <div style={{ position: 'absolute', left: '45px', zIndex: 10 }}>
                <Button colorScheme="teal" onClick={() => setShowAddEventForm(true)}>
                    Add New Event
                </Button>
            </div>

            {/* Prikazivanje AddEvent forme u modalu */}
            {showAddEventForm && (
                <Modal isOpen={showAddEventForm} onClose={() => setShowAddEventForm(false)}>
                    <ModalOverlay />
                    <ModalContent>
                        <ModalCloseButton />
                        <ModalBody>
                            <AddEvent onEventAdded={() => setShowAddEventForm(false)} />
                        </ModalBody>
                    </ModalContent>
                </Modal>
            )}
            </>
        ):null}
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
                                            <p className='lokDatDokTekst'>Dr. {event.doctorFirstName} {event.doctorLastName}</p>
                                        </Flex>
                                    </Flex>
                                    <p className='opisEventa'>{event.description}</p>
                                </Flex>
                                {( userRole === "USER" ||  userRole === "DOCTOR") ?(
                                    <>
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
                                </>
                               ):null }
                                {userRole === 'ADMIN' && (
                            <Button
                            colorScheme="red"
                            size="sm"
                            position="absolute" 
                                    
                                    bottom="20px" 
                                    right="20px"
                          onClick={() => setDeleteModal({ isOpen: true, type: 'event', id: event.id })}
                        >
                          Delete Event
                        </Button>
                            )}
                            </Flex>
                        ))
                    ) : (
                        <p>No events available</p>
                    )}
                </Flex>
            </div>
              {/* Delete Confirmation Modal */}
      <Modal isOpen={deleteModal.isOpen} onClose={() => setDeleteModal({ isOpen: false, type: null, id: null })}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Confirm Deletion</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>Are you sure you want to delete this event?</Text>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" onClick={handleDelete}>
              Yes
            </Button>
            <Button ml={3} onClick={() => setDeleteModal({ isOpen: false, type: null, id: null })}>
              No
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
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