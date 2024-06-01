import React, { useState, useEffect } from 'react';
import '../styles/QuestionsAndAnswers.css';
import '../styles/Categories.css';
import Categories from '../components/CategoriesForum';
import {
  Flex,
  Button,
  Box,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  FormControl,
  FormLabel,
  Input,
  Textarea,
  Radio,
  RadioGroup,
  Stack,
  Select,
  Accordion,
  AccordionItem,
  AccordionButton,
  AccordionPanel,
  AccordionIcon
} from '@chakra-ui/react';
import {
  getAllForumQuestions,
  getForumQuestionsByCategory,
  getAllCategories,
  addForumQuestion,
  getForumAnswersByQuestionId
} from '../services/forumService';
import { getUserByToken } from '../services/userService';

function QuestionsAndAnswers() {
  const [questions, setQuestions] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [questionTitle, setQuestionTitle] = useState('');
  const [questionText, setQuestionText] = useState('');
  const [questionCategory, setQuestionCategory] = useState('');
  const [anonymity, setAnonymity] = useState(true);
  const [categories, setCategories] = useState([]);
  const [expandedQuestionId, setExpandedQuestionId] = useState(null);

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        let fetchedQuestions;
        if (selectedCategory) {
          fetchedQuestions = await getForumQuestionsByCategory(selectedCategory.name);
        } else {
          fetchedQuestions = await getAllForumQuestions();
        }
        setQuestions(fetchedQuestions);
      } catch (error) {
        console.error('Error fetching questions:', error);
      }
    };
    fetchQuestions();
  }, [selectedCategory]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const fetchedCategories = await getAllCategories();
        setCategories(fetchedCategories);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    fetchCategories();
  }, []);

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
  };

  const handleModalClose = () => {
    setShowModal(false);
    setQuestionTitle('');
    setQuestionText('');
    setQuestionCategory('');
    setAnonymity(true);
  };

  const handleQuestionSubmit = async () => {
    console.log('Question title:', questionTitle);
    console.log('Question text:', questionText);
    console.log('Question category:', questionCategory);
    console.log('Anonymity:', anonymity);
    try {
      const userId = await getUserByToken();
      console.log('User ID:', userId);
    } catch (error) {
      console.error('Greška prilikom dohvatanja korisnika:', error);
    }
    
    // Add your question submission logic here

    setQuestionTitle('');
    setQuestionText('');
    setQuestionCategory('');
    setAnonymity(true);

    handleModalClose();
  };

  const categoryIcons = {
    Immunology: 'images/CategoryIcons/AllergyIcon.png',
    Cardiology: 'images/CategoryIcons/CardiologyIcon.png',
    Dermatology: 'images/CategoryIcons/DermatologyIcon.png',
    Dentistry: 'images/CategoryIcons/DentistryIcon.png',
    FamilyMedicine: 'images/CategoryIcons/FamilyMedicine.png',
    Gastroenterology: '/images/CategoryIcons/GastroenterologyIcon.png',
    Neurology: 'images/CategoryIcons/NeurologyIcon.png',
    Ophthalmology: 'images/CategoryIcons/OphthalmologyIcon.png',
    Orthopedics: 'images/CategoryIcons/OrthopedicsIcon.png',
    Pediatrics: 'images/CategoryIcons/PediatricsIcon.png'
  };

  const getCategoryIcon = (categoryId) => {
    const category = categories.find(cat => cat.id === categoryId);
    if (category) {
      return categoryIcons[category.name.replace(/\s/g, '')] || 'images/CategoryIcons/DefaultIcon.png';
    }
    return 'images/CategoryIcons/DefaultIcon.png';
  };

  const handleQuestionClick = async (question) => {
    if (expandedQuestionId === question.id) {
      setExpandedQuestionId(null);
      return;
    }

    try {
      const answers = await getForumAnswersByQuestionId(question.id);
      question.answers = answers;
      setExpandedQuestionId(question.id);
      //console.log(answers)
    } catch (error) {
      console.error('Error fetching answers:', error);
    }
  };

  return (
    <div>
      <div className='categoryDiv'>
        <Flex direction="column" className='categoryFlex'>
          <Flex className='categoryAndButton' justifyContent="space-between" alignItems="center">
            <Box>
              <h1>Question categories</h1>
            </Box>
            <Box>
              <Button className={`allQuestionsButton ${!selectedCategory ? 'selected' : ''}`} onClick={() => setSelectedCategory(null)}>All Questions</Button>
            </Box>
          </Flex>
          <Categories onSelectCategory={handleCategorySelect} selectedCategory={selectedCategory} />
        </Flex>
      </div>

      <div className='zaglavljePitanja'>
        <Flex justifyContent="space-between" alignItems="center" className='zaglavljeFlex'>
          <Button className='newQuestionDugme' colorScheme='#FF585F' onClick={() => setShowModal(true)}>New Question</Button>
        </Flex>
      </div>

      <div className='pitanjaDiv'>
        <Flex direction="column" className='pitanjaFlex'>
        <Accordion allowToggle> 
            {questions.map(question => (
              <AccordionItem key={question.id} className='pitanje'>
                  <AccordionButton onClick={() => handleQuestionClick(question)}>
                    <Box  as='span' flex='1' textAlign='left'>
                      <Flex direction="column">
                        <Flex justifyContent="space-between" alignItems="center" className='naslovIIkona'>
                          <Flex direction="column">
                            <p className='naslovPitanja'>{question.title}</p>
                            <p className='korisnikPitanja'>
                              By: {question.anonymity ? 'Anonymous' : (question.user ? `${question.user.firstName} ${question.user.lastName}` : 'Unknown User')}
                            </p>
                            <p className='datumPitanja'>{question.date}</p>
                          </Flex>
                          <div className='kategorijaPitanjaUnutarPitanjaDiv'>
                            <img
                              src={getCategoryIcon(question.category.id)}
                              alt="Category Icon"
                              style={{ width: '50px', height: 'auto' }}
                            />
                          </div>
                        </Flex>
                        <p className='tekstPitanja'>{question.text}</p>
                      </Flex>
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                <AccordionPanel pb={4} className='pitanje'>
                  <div className='odgovori'>
                    {question.answers && question.answers.map((answer, index) => (
                      <p key={index}>{answer.text}</p>
                    ))}
                  </div>
                </AccordionPanel>
              </AccordionItem>
            ))}
          </Accordion>
        </Flex>
      </div>

      {/* Modal */}
      <Modal isOpen={showModal} onClose={handleModalClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>New Question</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <FormControl id='questionTitle' isRequired>
              <FormLabel>Question Title</FormLabel>
              <Input placeholder='Enter question title' value={questionTitle} onChange={(e) => setQuestionTitle(e.target.value)} />
            </FormControl>
            <FormControl id='questionText' mt={4} isRequired>
              <FormLabel>Question Text</FormLabel>
              <Textarea placeholder='Enter question text' value={questionText} onChange={(e) => setQuestionText(e.target.value)} />
            </FormControl>
            <FormControl id='questionCategory' mt={4} isRequired>
              <FormLabel>Category</FormLabel>
              <Select placeholder='Select category' value={questionCategory} onChange={(e) => setQuestionCategory(e.target.value)}>
                {categories.map(category => (
                  <option key={category.id} value={category.id}>{category.name}</option>
                ))}
              </Select>
            </FormControl>
            <FormControl id='anonymity' mt={4}>
              <FormLabel>Anonymity</FormLabel>
              <RadioGroup value={anonymity.toString()} onChange={(value) => setAnonymity(value === 'true')}>
                <Stack spacing={5} direction='row'>
                  <Radio value='true'>Yes</Radio>
                  <Radio value='false'>No</Radio>
                </Stack>
              </RadioGroup>
            </FormControl>
          </ModalBody>
          <ModalFooter>
            <Button className='submitButton' colorScheme='#FF585F' mr={3} onClick={handleQuestionSubmit}>
              Submit
            </Button>
            <Button onClick={handleModalClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </div>
  );
}

export default QuestionsAndAnswers;
