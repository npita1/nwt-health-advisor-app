import React, { useEffect, useState } from 'react';
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    Select
} from '@chakra-ui/react';
import { getAllCategories, getCurrentDate } from '../services/forumService';
import '../styles/Articles.css';
import { FilePond, registerPlugin } from 'react-filepond';
import 'filepond/dist/filepond.min.css';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import FilePondPluginFileValidateSize from 'filepond-plugin-file-validate-size';


// Register the plugins
registerPlugin(FilePondPluginFileValidateType, FilePondPluginFileValidateSize);

export default function AddArticle() {
    const [categories, setCategories] = useState([]);
    const [fileContent, setFileContent] = useState('');
    const [files, setFiles] = useState([]);
    const [title, setTitle] = useState('');
    const [category, setCategory] = useState('');

    async function fetchCategories() {
        try {
            const categories = await getAllCategories();
            setCategories(categories);
            console.log(categories);
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    }

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleFileChange = (fileItems) => {
        if (fileItems.length > 0) {
            const file = fileItems[0].file;
            const reader = new FileReader();
            reader.onload = (e) => {
                setFileContent(e.target.result);
            };
            reader.readAsText(file);
        } else {
            setFileContent('');
        }
        setFiles(fileItems);
    };

    const handleSubmit = () => {
        const date = getCurrentDate()
        const articleData = {
            title,
            category_id: category,
            text: fileContent,
            doctor_id: localStorage.getItem('userId'),
            date: date
        };
        console.log('Article Data:', articleData);
        // Ovdje možete dodati logiku za slanje podataka na server
    };

    return (
        <div>
            <FormControl isRequired>
                <FormLabel>Article title</FormLabel>
                <Input 
                    placeholder='Title'
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />
            </FormControl>

            <FormControl>
                <FormLabel>Category</FormLabel>
                <Select
                    placeholder='Select category'
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                >
                    {categories.map((category) => (
                        <option key={category.id} value={category.id}>
                            {category.name}
                        </option>
                    ))}
                </Select>
            </FormControl>

            <FormControl>
                <FormLabel>Upload Text File</FormLabel>
                <FilePond
                    files={files}
                    allowMultiple={false}
                    onupdatefiles={handleFileChange}
                    acceptedFileTypes={['text/plain']}
                    labelIdle='Drag & Drop your file or <span class="filepond--label-action">Browse</span>'
                    maxFileSize='10MB'
                />
            </FormControl>

            <Button colorScheme="blue" mt={4} onClick={handleSubmit}>
                Submit
            </Button>
        </div>
    );
}
