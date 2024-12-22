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
  getForumAnswersByQuestionId,
  addForumAnswer // Import the function to add an answer
} from '../services/forumService';
import { getDoctorIdByUserId } from '../services/userService';

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
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
  const [answerText, setAnswerText] = useState('');

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        let fetchedQuestions;
        if (selectedCategory) {
          fetchedQuestions = await getForumQuestionsByCategory(selectedCategory.name);
        } else {
          fetchedQuestions = await getAllForumQuestions();
        }
        setQuestions(fetchedQuestions.map(question => ({ ...question, answers: question.answers || [] })));
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

  function getCurrentDate() {
    const date = new Date();
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
  }

  const handleQuestionSubmit = async () => {
    console.log('Question title:', questionTitle);
    console.log('Question text:', questionText);
    console.log('Question category:', questionCategory);
    console.log('Anonymity:', anonymity);

    try {
      const userId = localStorage.getItem('userId');
      console.log('User ID:', userId);

      if (userId == null || userId === "") {
        alert("You need to be logged in to post a question. Please log in to continue.");
        return;
      }

      var isAnonymous;
      if (anonymity === 'false') {
        isAnonymous = false;
      } else {
        isAnonymous = true;
      }

      const questionData = {
        id: 0,
        user: {
          id: 0,
          email: "string",
          firstName: "string",
          lastName: "string",
          type: 0,
          password: "string",
          userServiceId: 0
        },
        category: {
          id: parseInt(questionCategory),
          name: "string",
          description: "string"
        },
        title: questionTitle,
        text: questionText,
        date: getCurrentDate(),
        anonymity: isAnonymous
      };

      console.log('Sending request to add question:', JSON.stringify(questionData, null, 2));

      const addedQuestion = await addForumQuestion(userId, questionData);
      console.log('Added question:', addedQuestion);

      setQuestions(prevQuestions => [addedQuestion, ...prevQuestions]);

      setQuestionTitle('');
      setQuestionText('');
      setQuestionCategory('');
      setAnonymity(true);

      handleModalClose();
    } catch (error) {
      console.error('Greška prilikom dodavanja pitanja:', error);
      
    }
  };

  const handleAnswerSubmit = async (questionId) => {
    try {
      const userId = localStorage.getItem('userId');
      const doctorID = await getDoctorIdByUserId(userId);

      const answerData = {
        id: 0,
        question: {
          id: parseInt(questionId),
          user: {
            id: 0,
            email: "string",
            firstName: "string",
            lastName: "string",
            type: 0,
            password: "string",
            userServiceId: 0
          },
          category: {
            id: 0,
            name: "string",
            description: "string"
          },
          title: "string",
          text: "string",
          date: "06|37]9260",
          anonymity: true
        },
        doctor: {
          id: doctorID,
          about: "string",
          specialization: "string",
          user: {
            id: 0,
            email: "string",
            firstName: "string",
            lastName: "string",
            type: 0,
            password: "string",
            userServiceId: 0
          },
          availability: "- -L  pp -p p}pL}L {-LL-}-p L}p-{-LL{  pLpLL{p} -   -pL L -}-}L{ {-{p{}{ L }{ p{ -L LL{- -LL} { L L p-}}-L}{L}L L}{}{ L}}  p{} -}{L-L {} ",
          phoneNumber: "(556)              42265979"
        },
        text: answerText,
        date: getCurrentDate()
      };

      console.log('Sending request to add answer:', JSON.stringify(answerData, null, 2));

      const addedAnswer = await addForumAnswer(answerData);
      console.log('Added answer:', addedAnswer);

      // Update the questions state to include the new answer
      setQuestions(prevQuestions => 
        prevQuestions.map(question =>
          question.id === questionId ? { ...question, answers: [...question.answers, addedAnswer] } : question
        )
      );

      setAnswerText(''); // Clear the answer text field
    } catch (error) {
      console.error('Error submitting answer:', error);
      
    }
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
     { ( userRole === "USER" || userRole==="DOCTOR"  ) ? 
     (
      <>
      <div className='zaglavljePitanja'>
        <Flex justifyContent="space-between" alignItems="center" className='zaglavljeFlex'>
          <Button className='newQuestionDugme' colorScheme='#FF585F' onClick={() => setShowModal(true)}>New Question</Button>
        </Flex>
      </div>
      </>
     ):null
}

      <div className='pitanjaDiv'>
        <Flex direction="column" className='pitanjaFlex'>
          <Accordion allowToggle > 
            {questions.map(question => (
              <AccordionItem key={question.id} className='akordionItem'>
                <AccordionButton onClick={() => (userRole === "DOCTOR" || userRole === "USER" || userRole === "ADMIN") && handleQuestionClick(question)} className='akordionItem'>
                  <Box as='span' flex='1' textAlign='left'>
                    <div className='pitanje' key={question.id}>
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
                    </div>
                  </Box>
                  <AccordionIcon />
                </AccordionButton>
                <AccordionPanel pb={4} className='odgovorPanel'>
                  <div className='odgovori'>
                    {question.answers && question.answers.map((answer, index) => (
                      <div className='divJednogOdgovora' key={index}>
                        <Flex>
                          <img
                            src={`http://localhost:8084${answer.doctor.imagePath}`}
                            alt="Doctor Icon"
                            style={{ width: '50px', height: '50px', border: '50%' }}
                          />
                          <Flex direction="column">
                            <p className='odgovorImeDoktora'>Dr. {answer.doctor.user.firstName} {answer.doctor.user.lastName}</p>
                            <p className='datumPitanja'>{answer.date}</p>
                            <p className='tekstOdgovora'>{answer.text}</p>
                          </Flex>
                        </Flex>
                      </div>
                    ))}
                  </div>
                  {userRole === "DOCTOR" ? (
                    <>
                      <Textarea
                        placeholder='Type your answer here...'
                        className='textFieldOdgovor'
                        value={answerText}
                        onChange={(e) => setAnswerText(e.target.value)}
                      />
                      <Button
                        size='sm'
                        className='dugmeOdgovor'
                        colorScheme='#FF585F'
                        onClick={() => handleAnswerSubmit(question.id)}
                      >
                        Post Answer
                      </Button>
                    </>
                  ) : null}
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
              <RadioGroup value={anonymity.toString()} onChange={(value) => setAnonymity(value)}>
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