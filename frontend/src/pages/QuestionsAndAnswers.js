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
    Select
} from '@chakra-ui/react';
import {
    getAllForumQuestions,
    getForumQuestionsByCategory,
    getAllCategories
} from '../services/forumService';

function QuestionsAndAnswers() {
    const [questions, setQuestions] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [questionTitle, setQuestionTitle] = useState('');
    const [questionText, setQuestionText] = useState('');
    const [questionCategory, setQuestionCategory] = useState('');
    const [anonymity, setAnonymity] = useState(true); 
    const [categories, setCategories] = useState([]);

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
        // Reset form fields
        setQuestionTitle('');
        setQuestionText('');
        setQuestionCategory('');
        setAnonymity(true); // Reset to true when modal is closed
    };

    const handleQuestionSubmit = () => {
        // Here you can handle form submission, e.g., submit data to backend
        console.log('Question title:', questionTitle);
        console.log('Question text:', questionText);
        console.log('Question category:', questionCategory);
        console.log('Anonymity:', anonymity);

        setQuestionTitle('');
        setQuestionText('');
        setQuestionCategory('');
        setAnonymity(true); // Reset to true after submission

        // For demonstration, just close the modal
        handleModalClose();
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
                            <Button className={`allQuestionsButton ${!selectedCategory ? 'selected' : ''}`} onClick={() => setSelectedCategory(null)} >All Questions</Button>
                        </Box>
                    </Flex>
                    <Categories onSelectCategory={handleCategorySelect} selectedCategory={selectedCategory}/>
                </Flex>
            </div>

            <div className='zaglavljePitanja'> 
                <Flex justifyContent="space-between" alignItems="center" className='zaglavljeFlex'>
                    <Button className='newQuestionDugme' colorScheme='#FF585F' onClick={() => setShowModal(true)}>New Question</Button>
                </Flex>
            </div>

            <div className='pitanjaDiv'> 
                <Flex direction="column" className='pitanjaFlex'>
                    {
                        questions.map(question => (
                            <div className='pitanje' key={question.id}>
                                <Flex direction="column">
                                    <Flex justifyContent="space-between" alignItems="center" className='naslovIIkona'>
                                        <Flex direction="column">
                                            <p className='naslovPitanja'>{question.title}</p>
                                            <p className='korisnikPitanja'>
                                                By: {question.anonymity ? 'Anonymous' : `${question.user.firstName} ${question.user.lastName}`}
                                            </p>
                                            <p className='datumPitanja'>{question.date}</p>
                                        </Flex>
                                        <img src="images/HomePage/doktor.png" alt="Slika" className='slikaDoktor' style={{ width: '80px', height: 'auto' }} />
                                    </Flex>
                                    <p className='tekstPitanja'> {question.text}</p>
                                </Flex>
                            </div>
                        ))
                    }
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
