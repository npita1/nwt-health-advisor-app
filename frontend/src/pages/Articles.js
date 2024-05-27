import React from 'react';
import Article from '../components/Article';
import '../styles/Articles.css';
const articlesData = [
  {
    title: "Lizard",
    description: "Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging across all continents except Antarctica.",
    image: "images/StaffPage/article.png"
  },
  {
    title: "Chameleon",
    description: "Chameleons are known for their distinct abilities to change color and their unique eyes which can move independently of each other.",
    image: "images/StaffPage/chameleon.png"
  },
  {
    title: "Gecko",
    description: "Geckos are small to medium-sized lizards belonging to the infraorder Gekkota, found in warm climates throughout the world.",
    image: "images/StaffPage/gecko.png"
  },
  {
    title: "Gecko",
    description: "Geckos are small to medium-sized lizards belonging to the infraorder Gekkota, found in warm climates throughout the world.",
    image: "images/StaffPage/gecko.png"
  }
];

const Articles = () => {
  return (
    <>
      <div className='naslovDiv'>
        <h1 className='naslov'>Recent Articles</h1>
      </div>
      <div className='articlesContainer'>
        {articlesData.map((article, index) => (
          <Article
            key={index}
            title={article.title}
            description={article.description}
            image={article.image}
          />
        ))}
      </div>
    </>
  );
};

export default Articles;
