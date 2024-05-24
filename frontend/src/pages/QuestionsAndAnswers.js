import '../styles/QuestionsAndAnswers.css';
import Categories from '../components/CategoriesForum';
import {Flex, Button, Box} from '@chakra-ui/react';

function QuestionsAndAnswers() {
    
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

            </div>

        </div>
    );
}

export default QuestionsAndAnswers;
