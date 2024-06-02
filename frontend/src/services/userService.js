import bcrypt from 'bcryptjs';
import axios from 'axios';
import { useState } from 'react';

const API_URL = 'http://localhost:8086';

export async function logIn(email, inputedPassword) {
  try {
    const response = await axios.post(`${API_URL}/authentication/login`, { email: email, password: inputedPassword }, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': '*/*',
      },
    });

    const token = response.data.access_token;
    localStorage.setItem('token', token);
    getUserByToken()

    console.log("Uspjesan login")
    

  } catch (error) {
    console.error(error.message);
    alert("Netačan email ili adresa.")
  }
}

export async function addUser(formData) {
    try {
        const response = await axios.post(`${API_URL}/authentication/register`, formData, {

          headers: {
            'Content-Type': 'application/json',
            'Accept': '*/*',
          },
        });
         const token = await response.data.access_token
        localStorage.setItem('token', token);
        getUserByToken();

      } catch (error) {
        if (error.response && error.response.status === 409) {
          alert('Korisnik već postoji');
        } else {
          console.log(`login error ${error}`);
          // throw new Error(`Došlo je do greške prilikom dodavanja korisnika: ${error.message}`);
        }
      }
}

export async function saveUserIdInStorage(userId){
  try{
    const response = await axios.get(`${API_URL}/user/users/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    const userRole = await response.data.role
    localStorage.setItem('userRole', userRole)
    console.log("ROLA JEEE", localStorage.userRole)
  }catch (error) {
    console.error('Greška prilikom spasavanja role usera u localStorage:', error);
    throw error;
  }
}

// Vraca user id ciji je token
export async function getUserByToken() {
  const token = localStorage.getItem('token');

  if (!token) {
    throw new Error('Nije pronađen token u local storage-u.');
  }

  try {
    const response = await axios.get(`${API_URL}/authentication/user`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (response.status === 200) {
      console.log('Uspješno dohvaćeni podaci o korisniku:', response.data);
      localStorage.setItem('userId', response.data);
      saveUserIdInStorage(localStorage.userId)

     
      return response.data;
    } else {
      throw new Error('Neuspješno dobavljanje podataka o korisniku.');
    }
  } catch (error) {
    console.error('Greška prilikom dohvaćanja korisnika:', error);
    throw error;
  }
}

// export async function getAllDoctors() {
//   const token = localStorage.getItem('token');

//     // Provjeri da li je token dostupan
//     if (!token) {
//       throw new Error('Nije pronađen token u local storage-u.');
//     }
//   try {
//     const response = await axios.get(`${API_URL}/user/allDoctors`, {
//       headers: {
//         // 'Accept': 'application/json',
//         'Authorization': `Bearer ${token}`
//       }
//     });

//     if (response.status === 200) {
//       console.log('Uspješno dohvaćeni podaci o doktorima:', response.data);
//       return response.data;
//     } else {
//       throw new Error('Neuspješno dobavljanje podataka o doktorima.');
//     }
//   } catch (error) {
//     console.error('Greška prilikom dohvaćanja doktora:', error);
//     throw error;
//   }
// }

export async function getAllDoctors() {
  const token = localStorage.getItem('token');

    // Provjeri da li je token dostupan
    // if (!token) {
    //   throw new Error('Nije pronađen token u local storage-u.');
    // }
  try {
    const response = await axios.get(`${API_URL}/user/allDoctors`
    // , {
    //   headers: {
    //      'Accept': 'application/json',
    //     // 'Authorization': `Bearer ${token}`
    //   }
    // }
  );

    if (response.status === 200) {
      console.log('Uspješno dohvaćeni podaci o doktorima:', response.data);
      return response.data;
    } else {
      throw new Error('Neuspješno dobavljanje podataka o doktorima.');
    }
  } catch (error) {
    console.error('Greška prilikom dohvaćanja doktora:', error);
    throw error;
  }
}