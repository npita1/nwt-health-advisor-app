import axios from 'axios';
import { getUserByToken } from '../services/userService';

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

export async function getAllArticles() {
  try {
      const response = await fetch(`${API_URL}/forum/allArticles`, {
        method: 'GET',
      });
  
      if (response.ok) {
        const data = await response.json();
        console.log('Uspješno dohvaćeni svi članci:');
        return data; 
      } else {
        throw new Error('Došlo je do greške prilikom dohvaćanja podataka o člancima.');
      }
    } catch (error) {
      console.error(`Došlo je do greške prilikom dohvaćanja podataka o člancima: ${error.message}`);
      throw error;
    }
}

export function getCurrentDate() {
  const date = new Date();
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}.${month}.${year}`;
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
      console.error("Došlo je do greške prilikom dohvaćanja podataka o forum pitanjima:" `${error.message}`);
      throw error;
    }
}


export async function addForumQuestion(userId, questionData) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    console.log('Sending request to add question:', questionData);
    const response = await fetch(`${API_URL}/forum/addForumQuestion?userId=${userId}`, {
      method: 'POST',
      headers: {
        "Content-Type": 'application/json',
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(questionData)
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to add question:', response.status, errorText);
      throw new Error(`Failed to add question: ${response.status} - ${errorText}`);
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

export async function addArticle(articleData, userId, image) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
      throw new Error('Token nije pronađen');
  }

  const formData = new FormData();
  formData.append('doctorId', userId);
  formData.append('title', articleData.title);
  formData.append('text', articleData.text);
  formData.append('date', articleData.date);
  formData.append('image', image);
  formData.append('categoryId', articleData.categoryId);

  try {
      console.log('Šaljem zahtjev za dodavanje članka:', articleData);
      const response = await fetch(`${API_URL}/forum/addArticle`, {
          method: 'POST',
          headers: {
              'Accept': '*/*',
              'Authorization': `Bearer ${token}`
          },
          body: formData
      });

      if (!response.ok) {
          const errorText = await response.text();
          console.error('Dodavanje članka nije uspjelo:', response.status, errorText);
          throw new Error(`Dodavanje članka nije uspjelo: ${response.status} - ${errorText}`);
      }
      window.location.reload();
      return response.json();
  } catch (error) {
      console.error('Mrežna ili serverska greška:', error);
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




export async function getForumAnswersByQuestionId(id) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }
  try {
    const response = await fetch(`${API_URL}/forum/forumAnswers/question/${id}`, {
      method: 'GET',
      headers: {
        "Content-Type": 'application/json',
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      }
    });

    if (response.ok) {
      const data = await response.json();
      console.log('Uspješno dohvaćeni podaci o forum odgovorima na pitanje:');
      return data; 
    } else {
      throw new Error('Došlo je do greške prilikom dohvaćanja podataka o forum odgovorima na pitanje.');
    }
  } catch (error) {
    console.error("Došlo je do greške prilikom dohvaćanja podataka o forum odgovorima na pitanje:" `${error.message}`);
    throw error;
  }
}

export async function addForumAnswer(answerData) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    console.log('Sending request to add question:', answerData);
    const response = await fetch(`${API_URL}/forum/addForumAnswer`, {
      method: 'POST',
      headers: {
        "Content-Type": 'application/json',
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(answerData)
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to add question:', response.status, errorText);
      throw new Error(`Failed to add question: ${response.status} - ${errorText}`);
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

