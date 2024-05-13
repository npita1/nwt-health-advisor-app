import React, { useState } from 'react';
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
  } from '@chakra-ui/react';
import bcrypt from 'bcryptjs';

function LogIn() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
  
    const handlePasswordVisibility = () => setShowPassword(!showPassword);
  
    const handleSubmit = async (e) => {
      e.preventDefault();
  
      const hashedPassword = await bcrypt.hash(password, 10);
  
      const formData = {
          email: email,
          passwordHash: hashedPassword,
      };
        
        console.log('Podaci koji se šalju na server:', formData);
  
        try {
            // Slanje GET zahtjeva na backend
            const response = await fetch(`http://localhost:8084/user/users/email/${email}`, {
              method: 'GET',
            });
            
            if (response.ok) {
              const userData = await response.json();
              console.log(userData);
              if(bcrypt.compare(hashedPassword,userData.passwordHash))
                console.log('Loginovan korisnik');
            } else {
              console.error('Došlo je do greške prilikom slanja podataka na server.');
            }
          } catch (error) {
            console.error('Došlo je do greške prilikom slanja podataka:', error);
          }
          
      
    };

    return(    
    <form onSubmit={handleSubmit}>
        <FormControl isRequired mt={4}>
          <FormLabel>Email</FormLabel>
          <Input
            type='email'
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </FormControl>
        <FormControl isRequired mt={4}>
          <FormLabel>Password</FormLabel>
          <InputGroup size='md'>
            <Input
              pr='4.5rem'
              type={showPassword ? 'text' : 'password'}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <InputRightElement width='4.5rem'>
              <Button h='1.75rem' size='sm' onClick={handlePasswordVisibility}>
                {showPassword ? 'Hide' : 'Show'}
              </Button>
            </InputRightElement>
          </InputGroup>
        </FormControl>

        <Button type='submit' colorScheme='teal' mt={4}>
          Log In
        </Button>
      </form>
    );
}

export default LogIn;
