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
    const [slika, postaviSliku] = useState([]);
    const [validFileTypeTxt, setValidFileTypeTxt] = useState(true);
    const [validFileTypeImg, setValidFileTypeImg] = useState(true);


    async function dohvatiKategorije() {
        try {
            const kategorije = await getAllCategories();
            postaviKategorije(kategorije);
            
        } catch (error) {
            console.error('Greška pri dohvatanju kategorija:', error);
        }
    }

    useEffect(() => {
        dohvatiKategorije();
    }, []);

    async function dodajClanakUBazu(articleData) {
        try {
            const doctorID = await getDoctorIdByUserId(localStorage.getItem('userId'));
            await addArticle(articleData, doctorID, slika[0].file);
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

    const handleImageChange = (fileItems) => {
        postaviSliku(fileItems);
    };

    const handleSubmit = () => {
      if (slika.length === 0) {
        alert('Morate dodati sliku prije nego što pošaljete članak!');
        return;
    }
    if(kategorija===""){
      alert('Morate odabrati kategoriju!');
      return;
    }
    if(!(validFileTypeImg && validFileTypeTxt)){
      alert('Invalid file type');
      return;
    }
        const datum = getCurrentDate();
        const podaciClanka = {
            title: naslov,
            text: sadrzajFajla,
            date: datum,
            categoryId: kategorija        
    };
        
        dodajClanakUBazu(podaciClanka);
    };

    return (
        <div>
        <FormControl isRequired>
          <FormLabel>Title</FormLabel>
          <Input 
            placeholder='Title'
            value={naslov}
            onChange={(e) => postaviNaslov(e.target.value)}
          />
        </FormControl>
  
        <FormControl>
          <FormLabel>Category</FormLabel>
          <Select
            placeholder='Choose a category'
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
          <FormLabel>Add text file</FormLabel>
          <FilePond
            files={fajlovi}
            allowMultiple={false}
            onupdatefiles={handleFileChange}
            acceptedFileTypes={['text/plain']}
            labelIdle='Drag & drop your file or <span class="filepond--label-action">Browse</span>'
            maxFileSize='10MB'
            allowFileTypeValidation={true}
                onaddfile={(error, file) => {
                    if (error) {
                        setValidFileTypeTxt(false);
                    }else{
                        setValidFileTypeTxt(true);
                    }
                }}
          />
        </FormControl>
        
        <FormControl>
          <FormLabel>Upload image</FormLabel>
          <FilePond
            files={slika}
            allowMultiple={false}
            onupdatefiles={handleImageChange}
            acceptedFileTypes={['image/jpeg', 'image/png']}
            labelIdle='Drag & drop your image or <span class="filepond--label-action">Browse</span> <br/> (JPEG and PNG only)'
            maxFileSize='10MB'
            allowFileTypeValidation={true}
                onaddfile={(error, file) => {
                    if (error) {
                        setValidFileTypeImg(false);
                    }else{
                        setValidFileTypeImg(true);
                    }
                }}
          />
        </FormControl>
  
        <Button colorScheme="blue" mt={4} onClick={handleSubmit}>
          Submit
        </Button>
      </div>
    );
}
