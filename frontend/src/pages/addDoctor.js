import React, { useEffect, useState, useRef } from 'react';
import { Button, FormControl, FormLabel, Input, Textarea, InputGroup, InputRightElement, Flex, Box } from '@chakra-ui/react';
import '../styles/Articles.css';
import { FilePond, registerPlugin } from 'react-filepond';
import 'filepond/dist/filepond.min.css';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import FilePondPluginFileValidateSize from 'filepond-plugin-file-validate-size';
import '../styles/Event.css';
import { addDoctor } from '../services/userService';

// Registracija pluginova
registerPlugin(FilePondPluginFileValidateType, FilePondPluginFileValidateSize);

export default function AddDoctor() {
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [password, setPassword] = useState("");
    const [about, setAbout] = useState("");
    const [specialization, setSpecialization] = useState("");
    const [availability, setAvailability] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [slika, postaviSliku] = useState([]);
    const [showPassword, setShowPassword] = useState(false);
    const [validFileType, setValidFileType] = useState(true);
    const handlePasswordVisibility = () => setShowPassword(!showPassword);

    const textareaRef = useRef(null);

    const handleTextareaChange = (e) => {
        setAbout(e.target.value);
        autoResizeTextarea();
    };

    const autoResizeTextarea = () => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };

    // Ensure the textarea resizes properly when the component mounts
    useEffect(() => {
        autoResizeTextarea();
    }, []);

    async function dodajClanakUBazu(doctorData) {
        try {
            if(validFileType){
            await addDoctor(doctorData, slika[0].file);
        }else {
            alert('Invalid file type');
        }
        } catch (error) {
            console.error('Greška pri dodavanju doktora:', error);
        }
    }


    const handleImageChange = (fileItems) => {
        postaviSliku(fileItems);
    };

    const handleSubmit = () => {
        if (slika.length === 0) {
            alert('Morate dodati sliku doktora');
            return;
        }
        const doctorData = {
             email: email,
             firstName: firstName,
             lastName: lastName,
             password: password,
             about: about,
             specialization: specialization,
             availability: availability,
             phoneNumber: phoneNumber       
    };
        
        dodajClanakUBazu(doctorData);
    };

    return (
        <Box width="100%" maxWidth="800px" mx="auto" p={4}>
    <Flex direction="column" className='sviEventiFlex'>
        <FormControl isRequired>
            <FormLabel>Email</FormLabel>
            <Input 
                type='email'
                placeholder='example@mail.com'
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>First Name</FormLabel>
            <Input 
                type='text'
                placeholder='John'
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>Last Name</FormLabel>
            <Input 
                type='text'
                placeholder='Doe'
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
            />
        </FormControl>

        <FormControl isRequired mt={4}>
            <FormLabel>Password</FormLabel>
            <InputGroup size='md'>
                <Input
                    pr='4.5rem'
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <InputRightElement width='4.5rem'>
                    <Button h='1.75rem' size='sm' onClick={handlePasswordVisibility}>
                        {showPassword ? 'Hide' : 'Show'}
                    </Button>
                </InputRightElement>
            </InputGroup>
        </FormControl>            
        
        <FormControl>
            <FormLabel>Profile picture</FormLabel>
            <FilePond
                files={slika}
                allowMultiple={false}
                onupdatefiles={handleImageChange}
                acceptedFileTypes={['image/jpeg', 'image/png']}
                labelIdle='Drag and drop your image or <span class="filepond--label-action">Browse</span> <br/> (JPEG and PNG formats only)'
                maxFileSize='10MB'
                allowFileTypeValidation={true}
                onaddfile={(error, file) => {
                    if (error) {
                        setValidFileType(false);
                    }else{
                        setValidFileType(true);
                    }
                }}
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>About</FormLabel>
            <Textarea
                ref={textareaRef}
                placeholder='About'
                value={about}
                onChange={handleTextareaChange}
                rows={1} // Minimum number of rows to show
                style={{ resize: 'none', overflow: 'hidden' }} // Optional: Disable manual resizing
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>Specialization</FormLabel>
            <Input 
                type='text'
                placeholder='Specialization'
                value={specialization}
                onChange={(e) => setSpecialization(e.target.value)}
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>Availability</FormLabel>
            <Input 
                type='text'
                placeholder='Monday - Friday'
                value={availability}
                onChange={(e) => setAvailability(e.target.value)}
            />
        </FormControl>

        <FormControl isRequired>
            <FormLabel>Phone Number</FormLabel>
            <Input 
                type='text'
                placeholder='(387) 6XXXXXXX'
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
            />
        </FormControl>

        <Button colorScheme="blue" mt={4} onClick={handleSubmit}>
            Add Doctor
        </Button>
    </Flex>
</Box>

    );
}
