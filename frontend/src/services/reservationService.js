
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