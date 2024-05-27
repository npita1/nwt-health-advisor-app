import React, { useState, useEffect } from 'react';
import '../styles/QuestionsAndAnswers.css';
import '../styles/Categories.css';
import Categories from '../components/CategoriesForum';
import {Flex, Button, Box} from '@chakra-ui/react';
import { getAllForumQuestions, getForumQuestionsByCategory} from '../services/forumService';

function QuestionsAndAnswers() {
    const [questions, setQuestions] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);

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


    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
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
    
    return(
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
                    <Button className='newQuestionDugme' colorScheme='#FF585F'>New Question</Button>
                </Flex>
            </div>

            <div className='pitanjaDiv'> 
                <Flex direction="column" className='pitanjaFlex'>
                    {
                        questions.map(question => (
                            <div className='pitanje'>
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

        </div>
    );
}

export default QuestionsAndAnswers;
