import * as React from 'react'
import { ChakraProvider } from '@chakra-ui/react'
import { Tabs, TabList, TabPanels, Tab, TabPanel, Image } from '@chakra-ui/react'
import '../styles/Header.css'
import HomePage from '../pages/HomePage'
import StaffPage from '../pages/StaffPage'
import QuestionsAndAnswers from '../pages/QuestionsAndAnswers'
import Articles from '../pages/Articles'
import { useNavigate } from 'react-router-dom';

// function Header() {
//   return (
//     <ChakraProvider>
//       <div className="header">
//         <Tabs>
//           <TabList className="tab">
//           <div className="logo-container">
//             <Image src="images/logo.png" alt="Logo" />
//           </div>
//             <Tab>Home</Tab>
//             <Tab>Questions and Answers</Tab>
//             <Tab>Consultations</Tab>
//             <Tab>Workshops and Events</Tab>
//             <Tab>Articles</Tab>
//             <Tab>Our Specialists</Tab>
//           </TabList>
//           <TabPanels>
//             <TabPanel>
//               <HomePage />
//             </TabPanel>
//             <TabPanel>
//               <QuestionsAndAnswers/>
//             </TabPanel>
//             <TabPanel>
//               <p>Consultations content goes here!</p>
//             </TabPanel>
//             <TabPanel>
//               <p>Workshops and Events content goes here!</p>
//             </TabPanel>
//             <TabPanel>
//               <Articles />
//             </TabPanel>
//             <TabPanel>
//               <StaffPage />
//             </TabPanel>
           
//           </TabPanels>
//         </Tabs>
//       </div>
//     </ChakraProvider>
//   )
// }
function Header() {
  const navigate = useNavigate();

  return (
    <ChakraProvider>
      <div className="header">
        <Tabs>
          <TabList className="tab">
            <div className="logo-container">
              <Image src="images/logo.png" alt="Logo" />
            </div>
            <Tab onClick={() => navigate('/')}>Home</Tab>
            <Tab onClick={() => navigate('/questions-and-answers')}>Questions and Answers</Tab>
            <Tab onClick={() => navigate('/consultations')}>Consultations</Tab>
            <Tab onClick={() => navigate('/workshops-and-events')}>Workshops and Events</Tab>
            <Tab onClick={() => navigate('/articles')}>Articles</Tab>
            <Tab onClick={() => navigate('/our-specialists')}>Our Specialists</Tab>
          </TabList>
          <TabPanels>
            {/* Content for each tab */}
          </TabPanels>
        </Tabs>
      </div>
    </ChakraProvider>
  );
}

export default Header
