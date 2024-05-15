import bcrypt from 'bcryptjs';

const API_URL = 'http://localhost:8084';

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
        const response = await fetch(`${API_URL}/user/addUser`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData),
        });
    
        if (response.ok) {
          console.log('Korisnik uspješno dodan.');
        } else {
          throw new Error('Došlo je do greške prilikom dodavanja korisnika.');
        }
      } catch (error) {
        throw new Error(`Došlo je do greške prilikom dodavanja korisnika: ${error.message}`);
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

  