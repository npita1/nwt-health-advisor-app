import React, { useState } from 'react';
import {
  FormControl,
  FormLabel,
  Input,
  InputGroup,
  InputRightElement,
  Button,
} from '@chakra-ui/react';
import bcrypt from 'bcryptjs';


function SignUp() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const handlePasswordVisibility = () => setShowPassword(!showPassword);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const hashedPassword = await bcrypt.hash(password, 10);

    const formData = {
        email: email,
        firstName: firstName,
        lastName: lastName,
        type: 3,
        passwordHash: hashedPassword,
    };
      
      console.log('Podaci koji se šalju na server:', formData);

      try {
        // Slanje POST zahtjeva na backend
        const response = await fetch('http://localhost:8084/user/addUser', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(formData),
        });


        if (response.ok) {
          console.log('Podaci uspješno poslani na server.');
        } else {
          console.error('Došlo je do greške prilikom slanja podataka na server.');
        }
      } catch (error) {
        console.error('Došlo je do greške prilikom slanja podataka:', error);
      }
    
  };

  return (
    <form onSubmit={handleSubmit}>
      <FormControl isRequired>
        <FormLabel>First name</FormLabel>
        <Input
          type='text'
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
      </FormControl>
      <FormControl isRequired mt={4}>
        <FormLabel>Last name</FormLabel>
        <Input
          type='text'
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </FormControl>
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
      <FormControl isRequired mt={4}>
        <FormLabel>Confirm Password</FormLabel>
        <Input
          type='password'
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />
      </FormControl>
      <Button type='submit' colorScheme='teal' mt={4}>
        Sign Up
      </Button>
    </form>
  );
}

export default SignUp;
