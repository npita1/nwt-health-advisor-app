import React, { useEffect, useState, useRef } from 'react';
import Article from '../components/Article';
import { getAllArticles } from '../services/forumService';
import { getUserByToken, saveUserIdInStorage } from '../services/userService';
import '../styles/Articles.css';
import { Button } from '@chakra-ui/react';
import { SmallAddIcon, AddIcon} from '@chakra-ui/icons';
// const articlesData = [
//   {
//     title: "Lizard",
//     description: "Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging across all continents except Antarctica.",
//     image: "images/StaffPage/article.png"
//   },
//   {
//     title: "Chameleon",
//     description: "Chameleons are known for their distinct abilities to change color and their unique eyes which can move independently of each other.",
//     image: "images/StaffPage/chameleon.png"
//   },
//   {
//     title: "Gecko",
//     description: "Geckos are small to medium-sized lizards belonging to the infraorder Gekkota, found in warm climates throughout the world.",
//     image: "images/StaffPage/gecko.png"
//   },
//   {
//     title: "Gecko",
//     description: "Geckos are small to medium-sized lizards belonging to the infraorder Gekkota, found in warm climates throughout the world.",
//     image: "images/StaffPage/gecko.png"
//   }
// ];

const Articles = () => {
  const [articlesData, setArticlesData] = useState([]);
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));

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

  // useEffect(() => {
  //   // setUserRole(localStorage.userRole)
  //   // console.log("ROULAAA", userRole)
   
    
  // }, []);


  return (
    <>
      <div className='naslovDiv'>
        <h1 className='naslov'>Recent Articles </h1>
        
         

      </div>
      {(userRole === "DOCTOR" || userRole === "ADMIN") ?
         <Button  className='addButton' colorScheme='#1F55B3' leftIcon={<SmallAddIcon />}>Dodaj clanak</Button> 
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
    </>
  );
};

export default Articles;
