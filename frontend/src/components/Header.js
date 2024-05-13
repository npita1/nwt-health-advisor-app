import * as React from 'react'
import { ChakraProvider } from '@chakra-ui/react'
import { Tabs, TabList, TabPanels, Tab, TabPanel, Image } from '@chakra-ui/react'
import '../styles/Header.css'
import HomePage from '../pages/HomePage'
import StaffPage from '../pages/StaffPage'

function Header() {
  return (
    <ChakraProvider>
      <div className="header">
        <Tabs>
          <TabList className="tab">
          <div className="logo-container">
            <Image src="images/logo.png" alt="Logo" />
          </div>
            <Tab>Home</Tab>
            <Tab>Questions and Answers</Tab>
            <Tab>Consultations</Tab>
            <Tab>Workshops and Events</Tab>
            <Tab>Articles</Tab>
            <Tab>Our Specialists</Tab>
          </TabList>
          <TabPanels>
            <TabPanel>
              <HomePage />
            </TabPanel>
            <TabPanel>
              <p>Questions and Answers content goes here!</p>
            </TabPanel>
            <TabPanel>
              <p>Consultations content goes here!</p>
            </TabPanel>
            <TabPanel>
              <p>Workshops and Events content goes here!</p>
            </TabPanel>
            <TabPanel>
              <p>Articles content goes here!</p>
            </TabPanel>
            <TabPanel>
              <StaffPage />
            </TabPanel>
           
          </TabPanels>
        </Tabs>
      </div>
    </ChakraProvider>
  )
}

export default Header
