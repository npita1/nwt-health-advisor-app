// import React from 'react'
// import Header from './components/Header.js'

// function App() {
//   return (
//     <div>
//       <Header />
//     </div>
//   )
// }

// export default App
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import StaffPage from './pages/StaffPage';
import Articles from './pages/Articles';
import QuestionsAndAnswers from './pages/QuestionsAndAnswers.js';
import Header from './components/Header.js'

const App = () => {
  return (
  
    <div>
    {/* Prikazivanje Header komponente */}
    <Header />
    
    {/* Postavljanje Switch komponente za definiranje ruta */}
    {/* <Routes>
  <Route path="/" element={<HomePage />} />
  <Route path="/questions-and-answers" element={<QuestionsAndAnswers />} />
  <Route path="/consultations" element={<div>consultacije</div>} />
  <Route path="/workshops-and-events" element={<div>cao</div>} />
  <Route path="/articles" element={<Articles />} />
  <Route path="/our-specialists" element={<StaffPage />} />
</Routes> */}
  </div>
 
  );
}

export default App;