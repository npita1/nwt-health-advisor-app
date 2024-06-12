
const API_URL = 'http://localhost:8086';

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
      console.log('Sending request to add reservation:', eventReservationData);
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
      throw error;
    }
}