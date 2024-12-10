import React, { useEffect, useState, useRef } from 'react';
import Article from '../components/Article';
import { getAllArticles } from '../services/forumService';
import { getUserByToken, saveUserIdInStorage } from '../services/userService';
import '../styles/Articles.css';
import { Button, Flex, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton, Box} from '@chakra-ui/react';
import { SmallAddIcon, AddIcon} from '@chakra-ui/icons';
import AddArticle from '../components/AddArticle';

const Articles = () => {
  const [articlesData, setArticlesData] = useState([]);
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));

  const [showAddArticle, setShowAddArticle] = useState(false);

  const handleAddArticle = () => {
    setShowAddArticle(true);
  };

  const handleCloseAddArticle = () => {
    setShowAddArticle(false);
  };

  async function fetchArticles() {
    try {
      const articles = await getAllArticles();
      setArticlesData(articles)
      console.log(articles);
    } catch (error) {
      console.error('Error fetching articles:', error);
    }
  };
  
  useEffect(() => {
    fetchArticles();
    
  }, []);

  useEffect(() => {
    setUserRole(localStorage.getItem('userRole'))
  }, [localStorage.getItem('userRole')])

  return (
    <Box width="100%" maxWidth="1000px" mx="auto" p={4}>
      <div className='naslovDiv'>
        <h1 className='naslov'>Recent Articles </h1>
        
         

      </div>
      {(userRole === "DOCTOR" ) ?
         <Button  onClick={handleAddArticle} className='addButton' colorScheme='#1F55B3' leftIcon={<SmallAddIcon />}>Add Article</Button> 
         : <></>}
      <div className='articlesContainer'>
      
        {articlesData.map((article, index) => (
          <Article
            key={index}
            title={article.title}
            subtitle={{
              doctor: "Dr. " + article.doctor.user.firstName + " " + article.doctor.user.lastName,
              category: article.category.name
            }}
            image={article.image}
            articleInfo={article}
            
          />
        ))}
      </div>

      <Modal isOpen={showAddArticle} onClose={handleCloseAddArticle}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Add Article</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <AddArticle onClose={handleCloseAddArticle} />
          </ModalBody>
          <ModalFooter>
            {/* Dodajte opcionalne kontrole u footeru ako je potrebno */}
          </ModalFooter>
        </ModalContent>
    </Modal>
    </Box>
  );
};

export default Articles;
