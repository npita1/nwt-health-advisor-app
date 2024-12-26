import React, { useEffect, useState, useRef } from 'react';
import Article from '../components/Article';
import { getAllArticles,deleteArticle } from '../services/forumService';
import { getUserByToken, saveUserIdInStorage } from '../services/userService';
import '../styles/Articles.css';
import { Button, Flex, Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton, Box,Text} from '@chakra-ui/react';
import { SmallAddIcon, AddIcon} from '@chakra-ui/icons';
import AddArticle from '../components/AddArticle';

const Articles = () => {
  const [articlesData, setArticlesData] = useState([]);
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
  const [deleteModal, setDeleteModal] = useState({ isOpen: false, type: null, id: null });
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
  const handleDelete = async () => {
    const { type, id } = deleteModal;
    try {
      if (type === 'article') {
       
        const response = await deleteArticle(id);
        console.log(response.message);
        setArticlesData(prev => prev.filter(article => article.id !== id));
      } 
      // Zatvaranje modala nakon uspješnog brisanja
      setDeleteModal({ isOpen: false, type: null, id: null });
    } catch (error) {
      // Obrada greške
      console.error('Error deleting item:', error.message || error);
    }
  };
  return (
    <Box width="100%" maxWidth="1000px" mx="auto" p={4}>
      <div className='naslovDiv'>
        <h1 className='naslov'>Recent Articles </h1>
        
         

      </div>
      {(userRole === "DOCTOR" ) ?
         <Button  onClick={handleAddArticle} className='addButton' colorScheme='#1F55B3' leftIcon={<SmallAddIcon />}>Add Article</Button> 
         : <></>}
      <div className="articlesContainer">
        {articlesData.map((article) => (
          <Box key={article.id} className="article-box" p={4} mb={4} boxShadow="md" borderRadius="md" borderWidth="1px">
          <Flex justifyContent="space-between" alignItems="center" >
            {/* Informacije o članku */}
            <Article
              title={article.title}
              subtitle={{
                doctor: `Dr. ${article.doctorFirstName} ${article.doctorLastName}`,
                category: article.categoryName,
              }}
              image={article.image}
              articleInfo={article}
            />
      
            {/* Dugme za brisanje (vidljivo samo za ADMIN role) */}
            {userRole === 'ADMIN' && (
              <Button
                colorScheme="red"
                size="sm"
                ml={4}
                onClick={() => setDeleteModal({ isOpen: true, type: 'article', id: article.id })}
              >
                Delete
              </Button>
            )}
          </Flex>
        </Box>
        ))}
      </div>
        {/* Delete Confirmation Modal */}
      <Modal isOpen={deleteModal.isOpen} onClose={() => setDeleteModal({ isOpen: false, type: null, id: null })}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Confirm Deletion</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>Are you sure you want to delete this article?</Text>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="red" onClick={handleDelete}>
              Yes
            </Button>
            <Button ml={3} onClick={() => setDeleteModal({ isOpen: false, type: null, id: null })}>
              No
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
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