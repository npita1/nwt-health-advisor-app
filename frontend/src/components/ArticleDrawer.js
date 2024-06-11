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
            <DrawerHeader color={"#FF585F"}>{articleInfo.title}</DrawerHeader>
  
            <DrawerBody display="flex" flexDirection="column" alignItems="center">
                <Text>{articleInfo.text}</Text>
                {/* <Text>
                  
The coronavirus vaccination is a topic that has been the cause of differing opinions for some months now. Dr. Tilman Königswieser, Medical Director of the Salzkammergut Clinic, answers the four most frequently asked questions.

Why does it make sense to get vaccinated?
Vaccinations are generally very effective preventive measures against infectious diseases and are among the most important medical interventions. Individually, they prevent particular infectious diseases. Many vaccinations also protect against the spread of those infectious diseases. A number of infectious diseases have almost completely disappeared thanks to the success of vaccinations.

How could the coronavirus vaccine be researched and manufactured so quickly compared to other vaccines?
Generally speaking, vaccines against coronaviruses have been in development for a long time. Because of the severe effects of the pandemic, almost all renowned medical and scientific research institutes around the world have made efforts to develop effective and safe vaccines against the COVID-19 disease. Vaccines are the safest, and currently the only, way out of this pandemic.

How safe can the vaccine be after such a short manufacturing time?
It is precisely through this worldwide cooperation and the experience that mankind already has on the subject of vaccinations. In addition, the studies and approval testing are extremely important. These have to be done in the same way as with all other vaccinations or drugs. When it comes to important vaccinations and medication, however, approvals are given in such a way that research institutes and licensing authorities cooperate closely right from the start. Vaccines are subject to particularly close scrutiny. Tens of thousands of people around the world have been voluntarily vaccinated in vaccination studies over the past year. One can therefore say that the vaccinations work well and are well tolerated.
                </Text> */}
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