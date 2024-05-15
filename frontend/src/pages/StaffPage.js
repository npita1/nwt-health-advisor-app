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

  useEffect(() => {
    const fetchDoctors = async () => {
      try {
        const data = await getAllDoctors();
        setDoctors(data);
        setFilteredDoctors(data);
        const specializations = [...new Set(doctors.map(doctor => doctor.specialization.toLowerCase()))];
    const sortedSpecializations = specializations.sort();
    setCategories(sortedSpecializations)
      } catch (error) {
        console.error('Greška prilikom dohvaćanja podataka o doktorima:', error.message);
      }
    };

    fetchDoctors();
    
  }, []);

  const handleSearch = (value) => {
    setSearchValue(value);

    // Pronalazi index prvog doktora koji odgovara pretrazi
    const index = doctors.findIndex((doctor) =>
      doctor.user.firstName.toLowerCase().includes(value.toLowerCase()) ||
      doctor.user.lastName.toLowerCase().includes(value.toLowerCase())
    );

    // Ako je pronađen, pomičemo se do odgovarajuće kartice
    if (index !== -1 && doctorCardRef.current) {
      doctorCardRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <div>
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
            <MenuItem>All</MenuItem>
            {
            categories.map((category, index) => (
              <div key={index} ref={index === 0 ? doctorCardRef : null}>
                <MenuItem>{category ? 
                          category.charAt(0).toUpperCase() + 
                          category.slice(1) : 'N/A'}</MenuItem>
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
    </div>
  );
};

export default StaffPage;
