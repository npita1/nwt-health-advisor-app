import React, { useEffect, useState } from 'react';
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

const DoctorCard = ({doctorInfo}) => {
    return(
    <Card
  direction={{ base: 'column', sm: 'row' }}
  overflow='hidden'
  variant='outline'
  borderRadius='25px'
  borderColor={"#FF585F"}
  marginBottom={5}
>
  <Image
    objectFit='cover'
    maxW={{ base: '100%', sm: '200px' }}
    src="images/StaffPage/doktorica.png"
    alt='Doctor'
    style={{ width: '100%' }} 
  />

  <Stack flexGrow={1}>
    <CardBody>
      <div className='TextDiv'>
      <Text size='md' color={"#1F55B3"}>
          {
            doctorInfo.specialization ? 
            doctorInfo.specialization.charAt(0).toUpperCase() + 
            doctorInfo.specialization.slice(1) : 'N/A'
            }
      </Text>
      <Text size='md' color={"#FF585F"} >Doctor</Text>

      </div>
      <Heading py='2' size='md' color={"#1F55B3"}>
        Dr. {doctorInfo.user.firstName + " " + doctorInfo.user.lastName}
      </Heading>

      <Text  color={"#BCCCE8"}>
        {doctorInfo.about}
      </Text>
    </CardBody>

    <CardFooter className='CardFooter'>
      <EmailIcon color={"#BCCCE8"} ></EmailIcon>
      <Text color={"#BCCCE8"}>{doctorInfo.user.email}</Text>
      <PhoneIcon color={"#BCCCE8"}></PhoneIcon>
      <Text color={"#BCCCE8"}>{doctorInfo.phoneNumber}</Text>
      <Spacer />
      <DoctorDrawer doctorInfo={doctorInfo}></DoctorDrawer>

    </CardFooter>

  </Stack>
</Card>
    )
}

export default DoctorCard;
