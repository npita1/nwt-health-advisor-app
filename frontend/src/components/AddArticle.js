import React, { useEffect, useState } from 'react';
import { Button, FormControl, FormLabel, Input, Select } from '@chakra-ui/react';
import { getAllCategories, getCurrentDate, addArticle } from '../services/forumService';
import '../styles/Articles.css';
import { FilePond, registerPlugin } from 'react-filepond';
import 'filepond/dist/filepond.min.css';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import FilePondPluginFileValidateSize from 'filepond-plugin-file-validate-size';
import { getDoctorIdByUserId } from '../services/userService';

// Registracija pluginova
registerPlugin(FilePondPluginFileValidateType, FilePondPluginFileValidateSize);

export default function AddArticle() {
    const [kategorije, postaviKategorije] = useState([]);
    const [sadrzajFajla, postaviSadrzajFajla] = useState('');
    const [fajlovi, postaviFajlove] = useState([]);
    const [naslov, postaviNaslov] = useState('');
    const [kategorija, postaviKategoriju] = useState('');

    async function dohvatiKategorije() {
        try {
            const kategorije = await getAllCategories();
            postaviKategorije(kategorije);
            console.log(kategorije);
        } catch (error) {
            console.error('Greška pri dohvatanju kategorija:', error);
        }
    }

    useEffect(() => {
        dohvatiKategorije();
    }, []);

    async function dodajClanakUBazu(articleData) {
        try {
           const doctorID = await getDoctorIdByUserId(localStorage.getItem('userId'))
            await addArticle(articleData, doctorID);
            // await addArticle(articleData, 2);
        } catch (error) {
            console.error('Greška pri dodavanju članka:', error);
        }
    }

    const handleFileChange = (fileItems) => {
        if (fileItems.length > 0) {
            const file = fileItems[0].file;
            const reader = new FileReader();
            reader.onload = (e) => {
                postaviSadrzajFajla(e.target.result);
            };
            reader.readAsText(file);
        } else {
            postaviSadrzajFajla('');
        }
        postaviFajlove(fileItems);
    };

    const handleSubmit = () => {
        const datum = getCurrentDate();
        const podaciClanka = {
            doctor: {
                about: "string",
                specialization: "string",
                user: {
                  id: localStorage.getItem('userId'),
                  email: "string",
                  firstName: "string",
                  lastName: "string",
                  type: 0,
                  password: "string",
                  userServiceId: 0
                },
                availability: "Dostupnost",
                phoneNumber: "(123) 456-7890"
              },
              category: {
                id: kategorija,
                name: "string",
                description: "string"
              },
              title: naslov,
              text: sadrzajFajla,
              date: datum
        };
        console.log('Podaci članka:', podaciClanka);
        dodajClanakUBazu(podaciClanka);
    };

    return (
        <div>
            <FormControl isRequired>
                <FormLabel>Naslov članka</FormLabel>
                <Input 
                    placeholder='Naslov'
                    value={naslov}
                    onChange={(e) => postaviNaslov(e.target.value)}
                />
            </FormControl>

            <FormControl>
                <FormLabel>Kategorija</FormLabel>
                <Select
                    placeholder='Izaberite kategoriju'
                    value={kategorija}
                    onChange={(e) => postaviKategoriju(e.target.value)}
                >
                    {kategorije.map((kategorija) => (
                        <option key={kategorija.id} value={kategorija.id}>
                            {kategorija.name}
                        </option>
                    ))}
                </Select>
            </FormControl>

            <FormControl>
                <FormLabel>Dodajte tekst fajl</FormLabel>
                <FilePond
                    files={fajlovi}
                    allowMultiple={false}
                    onupdatefiles={handleFileChange}
                    acceptedFileTypes={['text/plain']}
                    labelIdle='Povucite i otpustite vaš fajl ili <span class="filepond--label-action">Pretražite</span>'
                    maxFileSize='10MB'
                />
            </FormControl>

            <Button colorScheme="blue" mt={4} onClick={handleSubmit}>
                Pošaljite
            </Button>
        </div>
    );
}
