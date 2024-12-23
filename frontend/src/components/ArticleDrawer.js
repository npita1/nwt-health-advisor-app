import React from 'react';
import {
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    CardMedia
  } from '@chakra-ui/react'
  import { useDisclosure } from '@chakra-ui/react';

  import { Button, Input, Heading, Image, Text } from '@chakra-ui/react'
import { SettingsPowerRounded } from '@mui/icons-material';

const  ArticleDrawer =({articleInfo, isOpen, setIsOpen}) => {
    const { onClose, onOpen } = useDisclosure()
    const btnRef = React.useRef()
  
    return (
      <>
      
             {/* <Button ref={btnRef} size= "sm" variant='link' textColor={"#FF585F"} onClick={onOpen}>
          Show more 
        </Button> */}
        <Drawer
          isOpen={isOpen}
          placement='right'
          onClose={()=>{setIsOpen(false)}}
          finalFocusRef={btnRef}
          size="xl"
          
        >
          <DrawerOverlay  />
          <DrawerContent overflowY="auto">
            <DrawerCloseButton color={"#FF585F"}></DrawerCloseButton>
            <DrawerHeader color={"#FF585F"} style={{ fontSize: '40px' }}>{articleInfo.title}</DrawerHeader>
  
            <DrawerBody display="flex" flexDirection="column" alignItems="center">
                <img
                  src={`http://localhost:8086/forum${articleInfo.imagePath}`}
                  alt="green iguana"
                  style={{ width: '100%', height: 'auto' }}
                />
                <Text>{articleInfo.text}</Text>
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
  
  export default ArticleDrawer;