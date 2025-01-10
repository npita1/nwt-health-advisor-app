import bcrypt from 'bcryptjs';
import axios from 'axios';
import { useState } from 'react';

const API_URL = 'https://ec2-54-161-190-131.compute-1.amazonaws.com/api';

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
    alert("Netačan email ili lozinka.")
  }
}

export async function logout() {
  try{
    const response = await axios.get(`${API_URL}/authentication/logout`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    localStorage.setItem('token', "")
    localStorage.setItem('userId', "")
    localStorage.setItem('userRole', "")
    localStorage.setItem('selectedTabIndex', "0");

    window.location.reload();
  }catch(error){
    console.error('Greška prilikom logouta korisnika:', error);
    
    throw error;
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

      const token = await response.data.access_token;
      localStorage.setItem('token', token);
      getUserByToken();

  } catch (error) {
      if (error.response) {
          // Provjerite status greške
          if (error.response.status === 409) {
              alert('Korisnik već postoji');
          } else if (error.response.status === 400) {
              // Ispisivanje cijelog odgovora u konzolu da bismo vidjeli strukturu greške
              console.log('Greška u odgovoru:', error.response.data);

              // Ako postoji niz grešaka, prikažemo sve poruke
              if (Array.isArray(error.response.data)) {
                  const errorMessages = error.response.data.map(err => err.message).join('\n ');
                  alert(`Greške u validaciji: ${errorMessages}`);
              } else {
                  alert('Došlo je do greške prilikom registracije.');
              }
          }
      } else {
          console.log('Greška u mreži:', error);
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
    window.location.reload();

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

export async function addDoctor(doctorData, image) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('Token not found');
  }

  const formData = new FormData();
  formData.append('email', doctorData.email);
  formData.append('firstName', doctorData.firstName);
  formData.append('lastName', doctorData.lastName);
  formData.append('password', doctorData.password);
  formData.append('about', doctorData.about);
  formData.append('specialization', doctorData.specialization);
  formData.append('availability', doctorData.availability);
  formData.append('phoneNumber', doctorData.phoneNumber);
  formData.append('image', image);

  try {
    const response = await fetch(`${API_URL}/user/addNewDoctor`, {
      method: 'POST',
      headers: {
        'Accept': '*/*',
        'Authorization': `Bearer ${token}`
      },
      body: formData
    });
  
    if (!response.ok) {

      const errorText = await response.text();
      console.error('Adding doctor failed:', response.status, errorText);

      // Ako server vraća grešku u tekstualnom formatu, prikaži tu grešku u alertu
      if (response.status === 400) {
        alert(`Validacijska greška: ${errorText}`);
      } else {
        alert('Adding doctor failed: Doctor with this email or phone number already exist');
      }

      throw new Error(`Adding doctor failed: ${response.status} - ${errorText}`);
    }

    const responseData = await response.json();
    alert('Doctor added successfully');
    localStorage.setItem('selectedTabIndex', "4");
    window.location.reload();
    return responseData;
  } catch (error) {
    console.error('Network or server error:', error);
    throw error;
  }
}



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
      
      return response.data;
    } else {
      throw new Error('Neuspješno dobavljanje podataka o doktorima.');
    }
  } catch (error) {
    console.error('Greška prilikom dohvaćanja doktora:', error);
    throw error;
  }
}

export async function getDoctorIdByUserId(id) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('Token nije pronađen');
  }
  try {
    const response = await fetch(`${API_URL}/user/doctor/getbyuserid?doctorId=${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      }
    });

    if (response.ok) {
      const data = await response.json();
      
      return data.id; 
    } else {
      throw new Error('Došlo je do greške prilikom dohvaćanja podataka o doktoru.');
    }
  } catch (error) {
    console.error("Došlo je do greške prilikom dohvaćanja podataka o doktoru:" `${error.message}`);
    throw error;
  }
}
export async function changePassword(passwordData) {
  const token = localStorage.getItem('token');
  const response = await axios.patch(
    `${API_URL}/user/changePassword`,
    passwordData,
    {
      headers: { Authorization: `Bearer ${token}` },
    }
  );
  localStorage.setItem('token', "")
  localStorage.setItem('userId', "")
  localStorage.setItem('userRole', "")
  localStorage.setItem('selectedTabIndex', "0");
  return response.data;
}
// Funkcija za dohvaćanje trenutnog korisnika
export const getCurrentUser = async () => {
  const token = localStorage.getItem("token");
  try {
    const response = await axios.get(`${API_URL}/user/currentUser`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data; // Ovo vraća mapu sa detaljima korisnika
  } catch (error) {
    console.error("Greška pri dohvaćanju trenutnog korisnika:", error);
    throw error;
  }
}
export async function getAllUsers() {
  const token = localStorage.getItem('token');

      try {
    const response = await axios.get(`${API_URL}/user/allUsers`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
      );

    if (response.status === 200) {
      return response.data;
    } else {
      throw new Error('Neuspješno dobavljanje podataka o korisnicima.');
    }
  } catch (error) {
    console.error('Greška prilikom dohvaćanja korisnika:', error);
    throw error;
  }
}

export async function deleteUser(userId) {
  const token = localStorage.getItem('token');
  if (!token || token === "") {
    throw new Error('No token found');
  }

  try {
    const response = await fetch(`${API_URL}/user/deleteUser/${userId}`, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json, text/plain, */*',
        Authorization: `Bearer ${token}`
      }
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Failed to delete user:', response.status, errorText);
      throw new Error(`Failed to delete user: ${response.status} - ${errorText}`);
    }

    return response.json();
  } catch (error) {
    console.error('Network or server error:', error);

    throw error;
  }
}