import React, { useRef, useState, useEffect } from 'react';
import { Flex } from '@chakra-ui/react';
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import '../styles/Event.css';
import osm from '../resources/osm-providers';
import { getAllEvents } from '../services/reservationService';

function Event() {
    const [center, setCenter] = useState({ lat: 43.8663, lng: 18.4031 });
    const [events, setEvents] = useState([]);
    const ZOOM_LEVEL = 13; 
    const mapRef = useRef();

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

    return (
        <div>
            <div className="kartaDiv">
                <MapContainer center={center} zoom={ZOOM_LEVEL} ref={mapRef} className="leaflet-container" scrollWheelZoom={false}>
                    <TileLayer url={osm.maptiler.url}></TileLayer>
                </MapContainer>
            </div>
            <div className='eventiDiv'>
                <Flex direction="column" className='sviEventiFlex'>
                    {events.length > 0 ? (
                        events.map((event, index) => (
                            <Flex className='eventItem'>
                                <img src="images/EventPage/physical.jpg" className='slikaEvent' alt="Slika" style={{ width: '280px', height: 'auto' }} />
                                <Flex direction="column" key={index}>
                                    <h3 className='naslovEventa'>{event.name}</h3>
                                    <Flex direction="column" className='informacije'>
                                    <Flex className='infoFlex'>
                                        <img src="images/EventPage/location.png"  alt="Slika" style={{ width: '20px', height: 'auto' }} />
                                        <p className='lokDatDokTekst'>{event.location}</p>
                                    </Flex>
                                    <Flex className='infoFlex'>
                                        <img src="images/EventPage/calendar.png"  alt="Slika" style={{ width: '20px', height: 'auto' }} />
                                        <p className='lokDatDokTekst'>{event.date}</p>
                                    </Flex>
                                    <Flex className='infoFlex'>
                                        <img src="images/EventPage/lecturer.png"  alt="Slika" style={{ width: '20px', height: 'auto' }} />
                                        <p className='lokDatDokTekst'>Dr. {event.doctorInfo.user.firstName} {event.doctorInfo.user.lastName}</p>
                                    </Flex>
                                    </Flex>
                                    <p className='opisEventa'>{event.description}</p>
                                </Flex>
                            </Flex>
                        ))
                    ) : (
                        <p>No events available</p>
                    )}
                </Flex>
            </div>
        </div>
    );
}

export default Event;
