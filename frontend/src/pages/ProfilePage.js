import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  ChakraProvider,
  Text,
  List,
  ListItem,
  AlertDialog,
  AlertDialogBody,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogContent,
  AlertDialogOverlay,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  Input,
  SimpleGrid,
  useDisclosure,
} from '@chakra-ui/react';
import { getCurrentUser, changePassword } from '../services/userService';
import { getUserReservations, deleteReservation } from '../services/reservationService';

function ProfilePage() {
  const [user, setUser] = useState(null);
  const [reservations, setReservations] = useState([]);
  const [selectedReservation, setSelectedReservation] = useState(null);
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmationPassword, setConfirmationPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
  const {
    isOpen: isPasswordModalOpen,
    onOpen: onOpenPasswordModal,
    onClose: onClosePasswordModal,
  } = useDisclosure();

  useEffect(() => {
    async function fetchData() {
      try {
        const [userData, reservationData] = await Promise.all([
          getCurrentUser(),
          getUserReservations(),
        ]);
        setUser(userData);
        setReservations(reservationData);
      } catch (err) {
        setError("Greška pri dohvaćanju podataka.");
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, []);
  const handleDeleteReservation = async () => {
    if (selectedReservation) {
    try{
  
      
      await deleteReservation(selectedReservation);
      
      
    
  }catch (error) {
    setErrorMessage('Greška prilikom brisanje rezervacije: ' + error.message);
  }finally {
    setSelectedReservation(null);
    onClose(); // Sigurno zatvaranje
    setReservations((prev) =>
      prev.filter((reservation) => reservation.reservationId !== selectedReservation)
    );
  }
}
  };

  const handlePasswordChange = async () => {
    if (newPassword !== confirmationPassword) {
      setErrorMessage('Nova lozinka i potvrda lozinke se ne podudaraju.');
      return;
    }
    try {
      await changePassword({ currentPassword, newPassword, confirmationPassword });
      alert('Lozinka uspješno promijenjena.');
      onClosePasswordModal();
      window.location.reload();
    } catch (error) {
      setErrorMessage('Greška prilikom promjene lozinke: Netačna stara lozinka ili slaba nova lozinka(min 8 karaktera, moram imati veliko i malo slovo,broj i znak ) ');
    }
  };

  return (
    <ChakraProvider>
      <Box p={4} position="relative">
        <Button
          colorScheme="blue"
          position="absolute"
          top={4}
          right={4}
          onClick={onOpenPasswordModal}
        >
          Promjena lozinke
        </Button>
        <Text fontSize="2xl" mb={4}>
          Profil korisnika
        </Text>
        {user && (
          <>
            <Text><b>Ime:</b> {user.firstName}</Text>
            <Text><b>Prezime:</b> {user.lastName}</Text>
          </>
        )}
        <Text fontSize="xl" mt={6} mb={4}>
          Vaše rezervacije: 
        </Text>
        <SimpleGrid columns={{ base: 1, md: 2 }} spacing={4}>
          {reservations.map((reservation) => (
            <Box
              key={reservation.reservationId}
              borderWidth="1px"
              borderRadius="lg"
              p={4}
              boxShadow="sm"
            >
              <Text fontWeight="bold" mb={2}>{reservation.eventName}</Text>
              <Text><b>Datum:</b> {reservation.eventDate}</Text>
              <Text><b>Lokacija:</b> {reservation.location}</Text>
              <Text><b>Broj ulaznica:</b> {reservation.numberOfTickets}</Text>
              <Button
                colorScheme="red"
                size="sm"
                mt={4}
                onClick={() => {
                  setSelectedReservation(reservation.reservationId);
                  onOpen();
                }}
              >
                Obriši rezervaciju
              </Button>
            </Box>
          ))}
        </SimpleGrid>
      </Box>

      {/* Modal za potvrdu brisanja */}
      <AlertDialog isOpen={isOpen} onClose={onClose}>
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Potvrda brisanja
            </AlertDialogHeader>
            <AlertDialogBody>
              Da li ste sigurni da želite obrisati ovu rezervaciju?
            </AlertDialogBody>
            <AlertDialogFooter>
              <Button onClick={onClose}>Otkaži</Button>
              <Button colorScheme="red" onClick={handleDeleteReservation} ml={3}>
                Obriši
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>

      {/* Modal za promjenu lozinke */}
      <Modal isOpen={isPasswordModalOpen} onClose={onClosePasswordModal}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Promjena lozinke</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Input
              placeholder="Trenutna lozinka"
              mb={4}
              type="password"
              onChange={(e) => setCurrentPassword(e.target.value)}
            />
            <Input
              placeholder="Nova lozinka"
              mb={4}
              type="password"
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <Input
              placeholder="Potvrda nove lozinke"
              mb={4}
              type="password"
              onChange={(e) => setConfirmationPassword(e.target.value)}
            />
            {errorMessage && <Text color="red.500">{errorMessage}</Text>}
          </ModalBody>
          <ModalFooter>
            <Button onClick={onClosePasswordModal}>Otkaži</Button>
            <Button colorScheme="blue" ml={3} onClick={handlePasswordChange}>
              Promijeni
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </ChakraProvider>
  );
}

export default ProfilePage;
