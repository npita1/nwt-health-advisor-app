
const API_URL = 'http://localhost:8083';

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

export async function getAllForumQuestions() {
  try {
      const response = await fetch(`${API_URL}/forum/allForumQuestions`, {
        method: 'GET',
      });
  
      if (response.ok) {
        const data = await response.json();
        console.log('Uspješno dohvaćeni podaci o forum pitanjima:');
        return data; 
      } else {
        throw new Error('Došlo je do greške prilikom dohvaćanja podataka o forum pitanjima.');
      }
    } catch (error) {
      console.error(`Došlo je do greške prilikom dohvaćanja podataka o forum pitanjima: ${error.message}`);
      throw error;
    }
}


export async function getForumQuestionsByCategory(category) {
  try {
      const response = await fetch(`${API_URL}/forum/questions/category/${category}`, {
        method: 'GET',
      });
  
      if (response.ok) {
        const data = await response.json();
        console.log('Uspješno dohvaćeni podaci o forum pitanjima po kategoriji:');
        return data; 
      } else {
        throw new Error('Došlo je do greške prilikom dohvaćanja podataka o forum pitanjima po kategoriji.');
      }
    } catch (error) {
      console.error(`Došlo je do greške prilikom dohvaćanja podataka o forum pitanjima po kategoriji: ${error.message}`);
      throw error;
    }
}