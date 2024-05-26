
const API_URL = 'http://localhost:8086';

export async function getAllCategories() {
    try {
        const response = await fetch(`${API_URL}/forum/allCategories`, {
          method: 'GET',
        });
    
        if (response.ok) {
          const data = await response.json();
          console.log('Uspješno dohvaćeni podaci o kategorijama:');
          return data; 
        } else {
          throw new Error('Došlo je do greške prilikom dohvaćanja podataka o kategorijama.');
        }
      } catch (error) {
        console.error(`Došlo je do greške prilikom dohvaćanja podataka o kategorijama: ${error.message}`);
        throw error;
      }
}