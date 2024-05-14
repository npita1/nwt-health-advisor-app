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

const  DoctorDrawer =() => {
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
                    src="images/doktorica.png"
                    alt='Doctor'
                />
                <Heading size='md' color={"#1F55B3"} py={2}>
                    Dr. Sarah Turner
                </Heading>
                <Text size='md' color={"#BCCCE8"} py={2}>
                    Chief Medical Officer
                </Text>

               
                <Heading size='sm' color={"#1F55B3"}  paddingTop={10} alignSelf={"start"}>
                    Specialization
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Chief Medical Officer
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Availability
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Chief Medical Officer
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Service hours
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Chief Medical Officer
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Email
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Chief Medical Officer
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Phone
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                    Chief Medical Officer
                </Text>
                <Heading size='sm' color={"#1F55B3"} alignSelf={"start"}>
                    Responsobilities
                </Heading>
                <Text size='lg' color={"#BCCCE8"} paddingBottom={8} alignSelf={"start"}>
                At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.
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

