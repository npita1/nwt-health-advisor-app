import React from 'react';
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
import { Card, CardHeader, CardBody, CardFooter, Image, Stack, Heading, Text, Button } from '@chakra-ui/react'

const { Search } = Input;
const suffix = (
  <AudioOutlined
    style={{
      fontSize: 16,
      color: '#FF585F',
    }}
  />
);
const onSearch = (value, _e, info) => console.log(info?.source, value);
const StaffPage = () => (
  <div>
  <div className='staff-container'>
        <Search
            placeholder="Search"
            onSearch={onSearch}
            colorScheme = "#FF585F"
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
    style={{height: 35}}
    borderColor={"#FF585F"}
    textColor={"#FF585F"}
    
  >
    Sort <ChevronDownIcon color={"#FF585F"}/>
  </MenuButton>
  <MenuList>
    <MenuItem>All</MenuItem>
    <MenuItem>Kategorija 1</MenuItem>
    {/* <MenuDivider /> */}
    <MenuItem>Kategorija 2</MenuItem>
    <MenuItem>Kategorija 3</MenuItem>
  </MenuList>
</Menu>
</div>
<Card
  direction={{ base: 'column', sm: 'row' }}
  overflow='hidden'
  variant='outline'
  borderRadius='25px'
  borderColor={"#FF585F"}
>
  <Image
    objectFit='cover'
    maxW={{ base: '100%', sm: '200px' }}
    src="images/doktorica.png"
    alt='Doctor'
    style={{ width: '100%' }} 
  />

  <Stack>
    <CardBody>
      <div className='TextDiv'>
      <Text size='md' color={"#1F55B3"}>Chief Medical Officer</Text>
      <Text size='md' color={"#FF585F"}>Doctor</Text>
      </div>
      <Heading py='2' size='md' color={"#1F55B3"}>
        Dr. Sarah Turner
      </Heading>

      <Text  color={"#BCCCE8"}>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.</Text>
    </CardBody>

    <CardFooter className='CardFooter'>
      <EmailIcon color={"#BCCCE8"} ></EmailIcon>
      <Text color={"#BCCCE8"}>mail@gmail.com</Text>
      <PhoneIcon color={"#BCCCE8"}></PhoneIcon>
      <Text color={"#BCCCE8"}>(387) 62 111 000</Text>

    </CardFooter>
  </Stack>
</Card>
</div>

);
export default StaffPage;