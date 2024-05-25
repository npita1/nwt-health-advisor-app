import bcrypt from 'bcryptjs';
import axios from 'axios';

const API_URL = 'http://localhost:8086';

export async function logIn(email, inputedPassword) {
    try {
        const response = await fetch(`${API_URL}/user/users/email/${email}`, {
        method: 'GET',
        });
    
        if (response.ok) {
            const userData = await response.json();
            //console.log(userData);
            if (bcrypt.compareSync(inputedPassword, userData.passwordHash)) {
                console.log('Loginovan korisnik');
            } else {
                console.log("Netacan password.")
            }
        } else {
            console.error("Netačan email");
        }
    } catch (error) {
        console.error('Došlo je do greške prilikom prijave korisnika:', error);
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
    
      } catch (error) {
        if (error.response && error.response.status === 409) {
          alert('Korisnik već postoji');
        } else {
          console.log(`login error ${error}`);
          // throw new Error(`Došlo je do greške prilikom dodavanja korisnika: ${error.message}`);
        }
      }
}

export async function getAllDoctors() {
  try {
    const response = await fetch(`${API_URL}/user/allDoctors`, {
      method: 'GET',
    });

    if (response.ok) {
      const data = await response.json();
      console.log('Uspješno dohvaćeni podaci o doktorima:');
      return data; 
    } else {
      throw new Error('Došlo je do greške prilikom dohvaćanja podataka o doktorima.');
    }
  } catch (error) {
    console.error(`Došlo je do greške prilikom dohvaćanja podataka o doktorima: ${error.message}`);
    throw error;
  }
}

  