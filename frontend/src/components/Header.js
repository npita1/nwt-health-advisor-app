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
import Event from '../pages/Event';
import Articles from '../pages/Articles';
import { logout as logoutService } from '../services/userService';
import '../styles/HomePage.css';
import AddDoctor from '../pages/addDoctor';
import ProfilePage from '../pages/ProfilePage';
function Header() {
  const [isOpen, setIsOpen] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const cancelRef = React.useRef();
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));

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
            {( userRole === "USER") ?
        <Tab>My Profile</Tab> 
         : <></>}
            {( userRole === "ADMIN") ?
        <Tab>Add doctor</Tab> 
         : <></>}
            
            {localStorage.token ?
              <Button className='dugmeLogout' colorScheme="#FF585F" size='sm' onClick={onOpen} style={{ marginLeft: 'auto' }}>Logout</Button>
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
              <Event />
            </TabPanel>
            <TabPanel>
              <Articles />
            </TabPanel>
            <TabPanel>
              <StaffPage />
            </TabPanel>
            <TabPanel>
             <ProfilePage/>
              </TabPanel>
            <TabPanel>
            <AddDoctor />
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
              Logout confimation
            </AlertDialogHeader>

            <AlertDialogBody>
              Are you sure you want to log out?
            </AlertDialogBody>

            <AlertDialogFooter>
              <Button ref={cancelRef} onClick={onClose}>
                Cancel
              </Button>
              <Button colorScheme="red" onClick={handleConfirmLogout} ml={3}>
                Log out
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
    </ChakraProvider>
  );
}

export default Header;
