import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  ChakraProvider,
  Text,
  AlertDialog,
  AlertDialogBody,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogContent,
  AlertDialogOverlay,
  useDisclosure,
} from '@chakra-ui/react';
import { getAllUsers, deleteUser } from '../services/userService';

function AdminPanel() {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchUsers() {
      try {
        const userData = await getAllUsers();
        setUsers(userData);
      } catch (err) {
        setError("Greška pri dohvaćanju korisnika.");
      } finally {
        setLoading(false);
      }
    }

    fetchUsers();
  }, []);

  const handleDeleteUser = async () => {
    if (selectedUser) {
      try {
        await deleteUser(selectedUser);
        
      } catch (error) {
        setErrorMessage('Greška prilikom brisanja korisnika: ' + error.message);
      } finally {
        onClose();
        setSelectedUser(null);
        setUsers((prev) => prev.filter((user) => user.id !== selectedUser));
        window.location.reload();
      }
    }
  };

  return (
    <ChakraProvider>
      <Box p={4}>
        <Text fontSize="2xl" mb={4}>
          Admin Panel
        </Text>

        {loading ? (
          <Text>Učitavanje...</Text>
        ) : error ? (
          <Text color="red.500">{error}</Text>
        ) : (
          <Box>
            {users.map((user) => (
              <Box
                key={user.id}
                borderWidth="1px"
                borderRadius="lg"
                p={4}
                boxShadow="sm"
                mb={4}
              >
                <Text fontWeight="bold" mb={2}>{user.firstName} {user.lastName}</Text>
                <Text><b>Email:</b> {user.email}</Text>
                <Text><b>Role:</b> {user.role}</Text>
                <Button
                  colorScheme="red"
                  size="sm"
                  mt={4}
                  onClick={() => {
                    setSelectedUser(user.id);
                    onOpen();
                  }}
                >
                  Obriši korisnika
                </Button>
              </Box>
            ))}
          </Box>
        )}

        {/* Modal za potvrdu brisanja */}
        <AlertDialog isOpen={isOpen} onClose={onClose}>
          <AlertDialogOverlay>
            <AlertDialogContent>
              <AlertDialogHeader fontSize="lg" fontWeight="bold">
                Potvrda brisanja
              </AlertDialogHeader>
              <AlertDialogBody>
                Da li ste sigurni da želite obrisati ovog korisnika?
              </AlertDialogBody>
              <AlertDialogFooter>
                <Button onClick={onClose}>Otkaži</Button>
                <Button colorScheme="red" onClick={handleDeleteUser} ml={3}>
                  Obriši
                </Button>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialogOverlay>
        </AlertDialog>

      </Box>
    </ChakraProvider>
  );
}

export default AdminPanel;
