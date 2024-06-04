import * as React from 'react';
import { useState } from 'react';
import {
  Button,
  ChakraProvider,
  AlertDialog,
  AlertDialogBody,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogContent,
  AlertDialogOverlay,
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
  Image
} from '@chakra-ui/react';
import '../styles/Header.css';
import HomePage from '../pages/HomePage';
import StaffPage from '../pages/StaffPage';
import QuestionsAndAnswers from '../pages/QuestionsAndAnswers';
import Articles from '../pages/Articles';
import { logout as logoutService } from '../services/userService';
import '../styles/HomePage.css';

function Header() {
  const [isOpen, setIsOpen] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const cancelRef = React.useRef();

  const handleLogout = async () => {
    try {
      await logoutService();
      setShowAlert(true);
      // navigate('/');  // Preusmeri korisnika na početnu stranicu
    } catch (error) {
      console.error('Greška prilikom logouta korisnika:', error);
    }
  };

  const onClose = () => setIsOpen(false);
  const onOpen = () => setIsOpen(true);

  const handleConfirmLogout = async () => {
    onClose();
    await handleLogout();
  };

  return (
    <ChakraProvider>
      <div className="header">
        <Tabs>
          <TabList className="tab" style={{ display: 'flex', alignItems: 'center' }}>
            <div className="logo-container">
              <Image src="images/logo.png" alt="Logo" />
            </div>
            <Tab>Home</Tab>
            <Tab>Questions and Answers</Tab>
            <Tab>Workshops and Events</Tab>
            <Tab>Articles</Tab>
            <Tab>Our Specialists</Tab>
            {localStorage.token ?
              <Button className='dugmeLogout' colorScheme="#FF585F" size='xs' onClick={onOpen} style={{ marginLeft: 'auto' }}>Logout</Button>
              : <></>}
          </TabList>
          <TabPanels>
            <TabPanel>
              <HomePage />
            </TabPanel>
            <TabPanel>
              <QuestionsAndAnswers />
            </TabPanel>
            <TabPanel>
              <p>Consultations content goes here!</p>
            </TabPanel>
            <TabPanel>
              <p>Workshops and Events content goes here!</p>
            </TabPanel>
            <TabPanel>
              <Articles />
            </TabPanel>
            <TabPanel>
              <StaffPage />
            </TabPanel>
          </TabPanels>
        </Tabs>
      </div>
      
      <AlertDialog
        isOpen={isOpen}
        leastDestructiveRef={cancelRef}
        onClose={onClose}
      >
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Potvrda logout-a
            </AlertDialogHeader>

            <AlertDialogBody>
              Da li ste sigurni da želite da se odjavite?
            </AlertDialogBody>

            <AlertDialogFooter>
              <Button ref={cancelRef} onClick={onClose}>
                Odustani
              </Button>
              <Button colorScheme="red" onClick={handleConfirmLogout} ml={3}>
                Nastavi
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
    </ChakraProvider>
  );
}

export default Header;
