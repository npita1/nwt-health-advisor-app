import axios from 'axios';
const API_URL = 'https://ec2-54-161-190-131.compute-1.amazonaws.com/api';

export async function getAllEvents() {
    try {
        const response = await fetch(`${API_URL}/reservation/allEvents`, {
          method: 'GET',
        });
    
        if (response.ok) {
          const data = await response.json();
          console.log('Uspješno dohvaćeni podaci o eventima:');
          return data; 
        } else {
          throw new Error('Došlo je do greške prilikom dohvaćanja podataka o eventima.');
        }
      } catch (error) {
        console.error(`Došlo je do greške prilikom dohvaćanja podataka o eventima: ${error.message}`);
        throw error;
      }
}


export async function addReservation(eventReservationData) {
    const token = localStorage.getItem('token');
    if (!token || token === "") {
      throw new Error('No token found');
    }
  
    try {
      const response = await fetch(`${API_URL}/reservation/addReservation`, {
        method: 'POST',
        headers: {
          "Content-Type": 'application/json',
          Accept: 'application/json, text/plain, */*',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(eventReservationData)
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Failed to add reservation:', response.status, errorText);
        throw new Error(`Failed to add reservation: ${response.status} - ${errorText}`);
      }
  
      return response.json();
    } catch (error) {
      console.error('Network or server error:', error);
      // Parsiraj grešku da dobiješ samo poruku iza "message"
    try {
      const errorData = JSON.parse(error.message.split(' - ')[1]);
      const validationMessage = errorData.message;
      alert(validationMessage);  // Prikazujemo samo poruku validacije
    } catch (parseError) {
      alert('Nepoznata greška: ' + error.message);  // Ako parsing ne uspije
    }
      throw error;
    }
}

export async function addEvent(eventData) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    const response = await fetch(`${API_URL}/reservation/addEvent`, {
      method: 'POST',
      headers: {
        "Content-Type": 'application/json',
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(eventData)
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to add event:', response.status, errorText);
      throw new Error(`Failed to add event: ${response.status} - ${errorText}`);
    }
    return response.json();
  } catch (error) {
    console.error('Network or server error:', error);
    
    try {
      // Parsiraj odgovor za prikaz validacijske greške
      const errorData = JSON.parse(error.message.split(' - ')[1]);
      const validationMessage = errorData.message;
      alert(validationMessage);  // Prikaz validacijske greške
    } catch (parseError) {
      alert('Unknown error: ' + error.message);  // Ako parsing ne uspije
    }
    
    throw error;
  }
}
export async function getUserReservations() {
  const token = localStorage.getItem('token');
  const response = await axios.get(`${API_URL}/reservation/myReservations`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data;
}

export async function deleteReservation(reservationId) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    const response = await fetch(`${API_URL}/reservation/deleteReservation/${reservationId}`, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      }
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to delete reservation:', response.status, errorText);
      throw new Error(`Failed to delete reservation: ${response.status} - ${errorText}`);
    }

    return response.json();
  } catch (error) {
    console.error('Network or server error:', error);

    throw error;
  }
}

export async function deleteEvent(eventId) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    const response = await fetch(`${API_URL}/reservation/deleteEvent/${eventId}`, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      }
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to delete event:', response.status, errorText);
      throw new Error(`Failed to delete event: ${response.status} - ${errorText}`);
    }

    return response.json();
  } catch (error) {
    console.error('Network or server error:', error);

    throw error;
  }
}