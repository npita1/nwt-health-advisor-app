import React, { useState, useEffect } from 'react';
import { getAllCategories } from '../services/forumService';
import { Flex, useDisclosure } from '@chakra-ui/react';
import '../styles/Categories.css'


function Categories() {
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const { isOpen, onOpen, onClose } = useDisclosure();

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

  const handleCategoryClick = (category) => {
    setSelectedCategory(category);
    onOpen();
  };

  const firstRow = categories.slice(0,5);
  const secondRow = categories.slice(5, 10);

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
  
  

  return (
      <div className='tabelaKategorija'>
          <Flex direction="column" className='divv'>

            <Flex className='redovi'>
            {
              firstRow.map(category => (
                <Flex direction="column" className='kategorija' onClick={() => handleCategoryClick(category)}>
              <img
              src={categoryIcons[category.name.replace(/\s/g, '')] || 'images/CategoryIcons/DefaultIcon.png'}
              alt={category.name}
              style={{ width: '70px', height: 'auto' }}
            />
                  <p className='nazivKategorije'>{category.name}</p>
                </Flex>
              ))
            }
            </Flex>

            <Flex>
            {
              secondRow.map(category => (
                <Flex direction="column" className='kategorija' onClick={() => handleCategoryClick(category)}>
              <img
              src={categoryIcons[category.name.replace(/\s/g, '')] || 'images/CategoryIcons/DefaultIcon.png'}
              alt={category.name}
              style={{ width: '70px', height: 'auto' }}
            />
                  <p className='nazivKategorije'>{category.name}</p>
                </Flex>
              ))
            }
            </Flex>

          </Flex>
      
      </div>
    
  )
}

export default Categories;
