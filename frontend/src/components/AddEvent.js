import React, { useState } from "react";
import { Flex, Input, Button, Textarea, Heading } from "@chakra-ui/react";
import { addEvent } from "../services/reservationService";
import { getDoctorIdByUserId } from '../services/userService';

function AddEvent({ onEventAdded }) {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [date, setDate] = useState("");
  const [loading, setLoading] = useState(false);

  const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-'); // Razdvajamo datum na godine, mjesec i dan
    return `${day}.${month}.${year}`; // Kombiniramo ih u format DD.MM.YYYY
  };
  const handleAddEvent = async () => {
    try {
      setLoading(true);
      const userId = localStorage.getItem('userId');
      const doctorID = await getDoctorIdByUserId(userId);

      // Provjera i formatiranje datuma
    if (!date) {
      alert("Please select a date!");
      return;
    }

    const formattedDate = formatDate(date); // Formatiramo datum
      const eventData = {
        id: 0,
        location: location,
        name: name,
        description: description,
        date: formattedDate, // Koristimo formatirani datum
        doctorInfo: {
          id: doctorID,
          about: "string",
          specialization: "string",
          user: {
            id: 0,
            email: "string",
            firstName: "string",
            lastName: "string",
            type: 0,
            password: "string",
            userServiceId: 0,
          },
          phoneNumber: "(035)                                                                                 86352897"
        },
      };

      await addEvent(eventData);
      alert("Event successfully added!");
      onEventAdded();
      setName("");
      setDescription("");
      setLocation("");
      setDate("");
      window.location.reload();
    } catch (error) {
      console.error("Error adding event:", error);
    } finally {
      setLoading(false);
    }
  };
  return (
    <Flex
      direction="column"
      p={4}
      gap={3}
      border="1px solid #ddd"
      borderRadius="8px"
      backgroundColor="#f9f9f9"
      margin="20px"
    >
      <Heading as="h2" size="md" mb={2} color="#333">
        Add New Event
      </Heading>
      <Input
        placeholder="Event Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <Textarea
        placeholder="Event Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <Input
        placeholder="Location"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
      />
      <Input
        type="date"
        placeholder="Date"
        value={date}
        onChange={(e) => setDate(e.target.value)}
      />
      
      <Button
        colorScheme="blue"
        onClick={handleAddEvent}
        isLoading={loading}
        disabled={!name || !description || !location || !date }
      >
        Add Event
      </Button>
    </Flex>
  );
}

export default AddEvent;