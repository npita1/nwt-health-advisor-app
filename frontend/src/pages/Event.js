import React, { useRef, useState, useEffect } from 'react';
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
                {events.length > 0 ? (
                    events.map((event, index) => (
                        <div key={index} className='eventItem'>
                            <h3>{event.name}</h3>
                            <p>{event.location}</p>
                            <p>{event.date}</p>
                        </div>
                    ))
                ) : (
                    <p>No events available</p>
                )}
            </div>
        </div>
    );
}

export default Event;
