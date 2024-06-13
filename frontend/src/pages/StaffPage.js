import React, { useEffect, useState, useRef } from 'react';
import { AudioOutlined } from '@ant-design/icons';
import { Input, Space } from 'antd';
import '../styles/StaffPage.css'

import {
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  MenuItemOption,
  MenuGroup,
  MenuOptionGroup,
  MenuDivider,
  Box
} from '@chakra-ui/react'
import { ChevronDownIcon, EmailIcon, PhoneIcon} from '@chakra-ui/icons';
import { Card, Spacer, CardHeader, CardBody, CardFooter, Image, Stack, Heading, Text, Button } from '@chakra-ui/react'
import DoctorDrawer from '../components/DoctorDrawer';

import { getAllDoctors } from '../services/userService';
import DoctorCard from '../components/DoctorCard';


const { Search } = Input;

const StaffPage = () => {
  const [doctors, setDoctors] = useState([]);
  const [filteredDoctors, setFilteredDoctors] = useState([]);
  const [searchValue, setSearchValue] = useState('');
  const [categories, setCategories] = useState([]);
  const doctorCardRef = useRef(null);

  async function fetchDoctors() {
    try {
      const doctors = await getAllDoctors();
      setDoctors(doctors)
      setFilteredDoctors(doctors)
      console.log(doctors);
    } catch (error) {
      console.error('Error fetching doctors:', error);
    }
  };
  
  useEffect(() => {
    fetchDoctors();
    
    
  }, []);



  const handleSearch = (value) => {
    setSearchValue(value);
    const [firstName, lastName] = value.split(' ');

    const filtered = doctors.filter((doctor) => {
    const fullName = `${doctor.user.firstName} ${doctor.user.lastName}`;
    const lowerCasedFullName = fullName.toLowerCase();
    return lowerCasedFullName.includes(value.toLowerCase()) ||
           (firstName && lowerCasedFullName.includes(firstName.toLowerCase())) ||
           (lastName && lowerCasedFullName.includes(lastName.toLowerCase()));
  });
    setFilteredDoctors(filtered);
  
    if (filtered.length > 0 && doctorCardRef.current) {
      doctorCardRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  };

  const handleSort = (doctors, category) => {
    const filtered = doctors.filter((doctor) =>
      doctor.specialization.toLowerCase() === category.toLowerCase()
    );
  
    setFilteredDoctors(filtered);
  };

  return (
    <Box width="100%" maxWidth="1000px" mx="auto" p={4}>
      <div className='staff-container'>
        <Search
          placeholder="Search"
          onSearch={handleSearch}
          colorScheme="#FF585F"
          style={{ width: 200, borderColor: "#FF585F" }}
          className="custom-search-container"
          borderColor="#FF585F"
        />

        <Menu>
          <MenuButton
            px={4}
            py={2}
            transition='all 0.2s'
            borderRadius='md'
            borderWidth='1px'
            marginLeft={'20px'}
            _hover={{ bg: '#ffa5a8' }}
            _expanded={{ bg: '#ffa5a8' }}
            style={{ height: 35 }}
            borderColor={"#FF585F"}
            textColor={"#FF585F"}
            
          >
            Sort <ChevronDownIcon color={"#FF585F"} />
          </MenuButton>
          <MenuList>
            <MenuItem
            style={{ cursor: 'pointer' }} 
            role="button"
            onClick={() => {setFilteredDoctors(doctors)}}>All</MenuItem>
            {categories.map((category, index) => (
              <div key={index} ref={index === 0 ? doctorCardRef : null}>
                <MenuItem>
                  {category ? category.charAt(0).toUpperCase() + category.slice(1) : 'N/A'}
                </MenuItem>
              </div>
            ))}
          </MenuList>
        </Menu>
      </div>
      
      {filteredDoctors.map((doctor, index) => (
        <div key={index} ref={index === 0 ? doctorCardRef : null}>
          <DoctorCard doctorInfo={doctor} />
        </div>
      ))}
    </Box>
  );
};

export default StaffPage;
