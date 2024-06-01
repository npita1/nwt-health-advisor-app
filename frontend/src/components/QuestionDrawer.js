// QuestionDrawer.js
import React from 'react';
import {
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  Button,
  useDisclosure,
  Box
} from '@chakra-ui/react';

const QuestionDrawer = ({ question, isOpen, onClose }) => {
  return (
    <Drawer isOpen={isOpen} placement="right" onClose={onClose} size="xl">
      <DrawerOverlay />
      <DrawerContent>
        <DrawerCloseButton />
        <DrawerHeader>{question.title}</DrawerHeader>

        <DrawerBody>
          <Box>
            <p>{question.text}</p>
            <p><strong>By:</strong> {question.anonymity ? 'Anonymous' : `${question.user.firstName} ${question.user.lastName}`}</p>
            <p><strong>Date:</strong> {question.date}</p>
            <p><strong>Category:</strong> {question.category.name}</p>
          </Box>
          {/* Add the logic to display answers if needed */}
        </DrawerBody>

        <DrawerFooter>
          <Button variant="outline" mr={3} onClick={onClose}>
            Close
          </Button>
        </DrawerFooter>
      </DrawerContent>
    </Drawer>
  );
};

export default QuestionDrawer;
