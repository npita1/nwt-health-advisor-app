import React, { useState } from 'react';
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
  } from '@chakra-ui/react';
import { logIn } from '../services/userService';

function LogIn() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
  
    const handlePasswordVisibility = () => setShowPassword(!showPassword);
  
    const handleSubmit = async (e) => {
      e.preventDefault();
        
        try {
          await logIn(email, password);
        } catch(error) {
          console.log("greska se desila: ", error)
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
