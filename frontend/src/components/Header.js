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
  Text,
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
import AdminPanel from '../pages/AdminPanel';

function Header() {
  const [isOpen, setIsOpen] = useState(false);
  const cancelRef = React.useRef();
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));

  const handleLogout = async () => {
    try {
      await logoutService();
      // navigate('/');  // Preusmeri korisnika na početnu stranicu
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  const onClose = () => setIsOpen(false);
  const onOpen = () => setIsOpen(true);

  const handleConfirmLogout = async () => {
    onClose();
    await handleLogout();
  };
  const [selectedTabIndex, setSelectedTabIndex] = useState(() => {
    const savedIndex = localStorage.getItem('selectedTabIndex');
    return savedIndex ? parseInt(savedIndex, 10) : 0; // Podrazumevani indeks je 0 (Home)
  });
  const handleTabChange = (index) => {
    setSelectedTabIndex(index);
    localStorage.setItem('selectedTabIndex', index); // Sačuvaj indeks u localStorage
  };
    
  const tabs = [
    { label: "Home", component: <HomePage /> },
    { label: "Questions and Answers", component: <QuestionsAndAnswers /> },
    { label: "Workshops and Events", component: <Event /> },
    { label: "Articles", component: <Articles /> },
    { label: "Our Specialists", component: <StaffPage /> },
    { label: "My Profile", component: <ProfilePage />,  role: ["USER", "DOCTOR"]  },
    { label: "Add doctor", component: <AddDoctor />, role: "ADMIN" },
    { label: "Admin Panel", component: <AdminPanel />, role: "ADMIN" },
  ];

  return (
    <ChakraProvider>
      <div className="header">
        <Tabs index={selectedTabIndex} onChange={handleTabChange}>
          <TabList className="tab" style={{ display: 'flex', alignItems: 'center' }}>
            <div className="logo-container">
              <Image src="images/logo.png" alt="Logo" />
            </div>
            {tabs.map(
  (tab, index) =>
    (!tab.role || (Array.isArray(tab.role) ? tab.role.includes(userRole) : tab.role === userRole)) && (
      <Tab key={index}>{tab.label}</Tab>
    )
)}

            {localStorage.token && (
              <Button
                className="dugmeLogout"
                colorScheme="#FF585F"
                size="sm"
                onClick={onOpen}
                style={{ marginLeft: 'auto' }}
              >
                Logout
              </Button>
            )}
          </TabList>

          <TabPanels>
          {tabs.map(
  (tab, index) =>
    (!tab.role || (Array.isArray(tab.role) ? tab.role.includes(userRole) : tab.role === userRole)) && (
      <TabPanel key={index}>{tab.component}</TabPanel>
    )
)}
          </TabPanels>
        </Tabs>
      </div>

      <AlertDialog isOpen={isOpen} leastDestructiveRef={cancelRef} onClose={onClose}>
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Logout Confirmation
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
