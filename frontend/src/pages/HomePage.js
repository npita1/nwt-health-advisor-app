import React, { useState } from 'react';
import { Button, Flex, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from '@chakra-ui/react';
import '../styles/HomePage.css';
import SignUp from '../pages/SignUp.js';


function HomePage() {
  const [showSignUp, setShowSignUp] = useState(false);

  const handleSignUpClick = () => {
    setShowSignUp(true);
  };

  const handleCloseSignUp = () => {
    setShowSignUp(false);
  };

  return (
    <div>
    <div>
      <Flex className='gornjiSaDoktoromDiv'>
        <Flex direction="column" justifyContent="flex-start">
          <div className='naslovDiv'>
            <h1 className='naslov'>Welcome to Health Advisor!</h1>
          </div>
          <div className='nabrajanjeDiv'>
          <p className='naslovNabrajanje'>Our platform offers:</p>
            <ul className='nabrajanje'>
              <li>Q&A forum for health-related questions</li>
              <li>Consultation booking with doctors</li>
              <li>Overview of health workshops and events with reservation options</li>
              <li>Access to health articles</li>
            </ul>
          </div>
          <div className='tekstIznadDugmadiDiv'>
            <p className='tekstIznadDugmadi'>Join us and empower your journey towards better health!</p>
          </div>
          <div className='dugmadDiv'>
            <Button className='dugmeLogIn' colorScheme='#1F55B3' size="md" mr={2}>
             Log In
            </Button>
            <Button className='dugmeSignUp'colorScheme="#FF585F" size="md" onClick={handleSignUpClick}>
             Sign Up
            </Button>
          </div>
        </Flex>
        {/* Slika na desnoj strani */}
        <div className='doktorSlikaDiv' >
          <img src="images/doktor.png" alt="Slika" className='slikaDoktor' style={{ width: '400px', height: 'auto' }} />
        </div>
      </Flex>
    </div>


    <div className='whyChooseUsDiv'>

      <p>dalje div</p>
    </div>

    <Modal isOpen={showSignUp} onClose={handleCloseSignUp}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Sign Up</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <SignUp onClose={handleCloseSignUp} />
          </ModalBody>
          <ModalFooter>
            {/* Dodajte opcionalne kontrole u footeru ako je potrebno */}
          </ModalFooter>
        </ModalContent>
    </Modal>

    </div>
  );
}

export default HomePage;
