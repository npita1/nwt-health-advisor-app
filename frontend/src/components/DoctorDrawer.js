import React from 'react';
import {
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
  } from '@chakra-ui/react'
  import { useDisclosure } from '@chakra-ui/react';
  import { Button, Input, Heading, Image, Text } from '@chakra-ui/react'

const  DoctorDrawer =({doctorInfo}) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const btnRef = React.useRef()
  
    return (
      <>
        <Button ref={btnRef} size= "sm" variant='link' textColor={"#FF585F"} onClick={onOpen}>
          Show more 
        </Button>
        <Drawer
          isOpen={isOpen}
          placement='right'
          onClose={onClose}
          finalFocusRef={btnRef}
          size="md"
          
        >
          <DrawerOverlay  />
          <DrawerContent overflowY="auto">
            <DrawerCloseButton color={"#FF585F"}></DrawerCloseButton>
            <DrawerHeader color={"#FF585F"}>Profile Staff</DrawerHeader>
  
            <DrawerBody display="flex" flexDirection="column" alignItems="center">
                <Image
                    objectFit='cover'
                    maxW={{ base: '100%', sm: '200px' }}
                    src={`http://localhost:8086/user${doctorInfo.imagePath}`}
                    alt='Doctor'
                />
                <Heading size='md' color={"#1F55B3"} py={2}>
                    Dr. {doctorInfo.firstName + " " + doctorInfo.lastName}
                </Heading>
                <Text size='lg' color={"#FF585F"} py={2}>
                {
                    doctorInfo.specialization ? 
                    doctorInfo.specialization.charAt(0).toUpperCase() + 
                    doctorInfo.specialization.slice(1) : 'N/A'
                }
                </Text>

               
               
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Availability
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    {doctorInfo.availability}
                </Text>
                {/* <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Service hours
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Nemamo jos
                </Text> */}
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Email
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    {doctorInfo.email}
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Phone
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    {doctorInfo.phoneNumber}
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    About
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                {doctorInfo.about}
                </Text>
            </DrawerBody>

  
            {/* <DrawerFooter>
              <Button variant='outline' mr={3} onClick={onClose}>
                Cancel
              </Button>
              <Button colorScheme='blue'>Save</Button>
            </DrawerFooter> */}
          </DrawerContent>
        </Drawer>
      </>
    )
  }
  
  export default DoctorDrawer;

