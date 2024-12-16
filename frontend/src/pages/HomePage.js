import React, { useState } from 'react';
import { Button, Flex, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton } from '@chakra-ui/react';
import '../styles/HomePage.css';
import '../styles/Categories.css';
import SignUp from '../pages/SignUp.js';
import LogIn from '../pages/LogIn.js';
import Categories from '../components/Categories.js';


function HomePage() {
  const [showSignUp, setShowSignUp] = useState(false);
  const [showLogIn, setShowLogIn] = useState(false);
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
  const handleSignUpClick = () => {
    setShowSignUp(true);
  };

  const handleCloseSignUp = () => {
    setShowSignUp(false);
  };

  const handleLogInClick = () => {
    setShowLogIn(true);
  };

  const handleCloseLogIn = () => {
    setShowLogIn(false);
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
              <li>Overview of health workshops and events with reservation options</li>
              <li>Access to health articles</li>
            </ul>
          </div>
          <div className='tekstIznadDugmadiDiv'>
            <p className='tekstIznadDugmadi'>Join us and empower your journey towards better health!</p>
          </div>
          { (userRole==="") ? (
            <>
          <div className='dugmadDiv'>
            <Button className='dugmeLogIn' colorScheme='#1F55B3' size="md" mr={2} onClick={handleLogInClick}>
             Log In
            </Button>
            <Button className='dugmeSignUp'colorScheme="#FF585F" size="md" onClick={handleSignUpClick}>
             Sign Up
            </Button>
          </div>
          </>
          ):null }
        </Flex>
         
        {/* Slika na desnoj strani */}
        <div className='doktorSlikaDiv' >
          <img src="images/HomePage/doktor.png" alt="Slika" className='slikaDoktor' style={{ width: '400px', height: 'auto' }} />
        </div>
      </Flex>
    </div>


    <div className='whyChooseUsDiv'>
      <Flex direction="column" className='whyChooseUsFlex'>
        <h1 className='naslov'>Why Choose Us?</h1>
        <Flex className='boxesDiv'>
          <Flex direction="column" className='box'>
          <img src="images/HomePage/medical-team.png" alt="Slika" className='whyChooseUsIkone' style={{ width: '150px', height: 'auto' }} />
            <p className='boxText'>All Specialist</p>
          </Flex>
          <Flex direction="column" className='box'>
            <img src="images/HomePage/lock.png" alt="Slika" className='whyChooseUsIkone' style={{ width: '150px', height: 'auto' }} />
            <p className='boxText'>Private & Secure</p>
          </Flex>
          <Flex direction="column" className='box'>
            <img src="images/HomePage/educational.png" alt="Slika" className='whyChooseUsIkone' style={{ width: '150px', height: 'auto' }} />
            <p className='boxText'>Educational</p>
          </Flex>
        </Flex>
      </Flex>
    </div>


    <div className='specialitiesDiv'>
          <Flex direction="column" className='divv'>
            <h1 className='naslov'>Our consulting specialities</h1>
            <Categories/>
          </Flex>
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

    <Modal isOpen={showLogIn} onClose={handleCloseLogIn}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Log In</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <LogIn onClose={handleCloseLogIn} />
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
