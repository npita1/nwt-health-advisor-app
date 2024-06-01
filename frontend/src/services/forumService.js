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

function getCurrentDate() {
  const date = new Date();
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}.${month}.${year}`;
}

export async function addForumQuestion(questionData) {

}

export async function getForumAnswersByQuestionId(id) {
  try {
    const response = await fetch(`${API_URL}/forum/forumAnswers/question/${id}`, {
      method: 'GET',
    });

    if (response.ok) {
      const data = await response.json();
      console.log('Uspješno dohvaćeni podaci o forum odgovorima na pitanje:');
      return data; 
    } else {
      throw new Error('Došlo je do greške prilikom dohvaćanja podataka o forum odgovorima na pitanje.');
    }
  } catch (error) {
    console.error(`Došlo je do greške prilikom dohvaćanja podataka o forum odgovorima na pitanje: ${error.message}`);
    throw error;
  }
}

