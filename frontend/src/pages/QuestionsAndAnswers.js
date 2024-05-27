import React, { useState, useEffect } from 'react';
import '../styles/QuestionsAndAnswers.css';
import '../styles/Categories.css';
import Categories from '../components/CategoriesForum';
import {Flex, Button, Box} from '@chakra-ui/react';
import { getAllForumQuestions, getForumQuestionsByCategory} from '../services/forumService';

function QuestionsAndAnswers() {
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        const fetchQuestions = async () => {
          try {
            const fetchedQuestions = await getForumQuestionsByCategory("Cardiology");
            setQuestions(fetchedQuestions);
          } catch (error) {
            console.error('Error fetching questions:', error);
          }
    };
    fetchQuestions();
    } , []);
    
    return(
        <div>
            <div className='categoryDiv'>
                <Flex direction="column" className='categoryFlex'>
                    <Flex className='categoryAndButton' justifyContent="space-between" alignItems="center">
                        <Box>
                            <h1>Question categories</h1>
                        </Box>
                        <Box>
                            <Button>All Questions</Button>
                        </Box>
                    </Flex>
                    <Categories/>
                </Flex>
            </div>

            <div className='zaglavljePitanja'> 

            </div>

            <div className='pitanjaDiv'> 
                {
                    questions.map(question => (
                        <div>{question.title}</div>
                    ))
                }
            </div>

        </div>
    );
}

export default QuestionsAndAnswers;
