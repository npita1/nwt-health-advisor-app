import React, { useEffect, useState, useRef } from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';
import { Image } from '@chakra-ui/react';
import '../styles/Articles.css';
import ArticleDrawer from './ArticleDrawer';
import { useDisclosure } from '@chakra-ui/react';

const theme = createTheme();

export default function Article({ title, subtitle, image, articleInfo, onClick}) {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <div>
    <ThemeProvider theme={theme}>
      <Card sx={{ width: 300, maxWidth:345, margin: '10px' }}>
        <CardActionArea style={{padding: '0'}} onClick={() => {setIsOpen(true)}}>
       
          <div style={{ position: 'relative', paddingBottom: '56.25%', height: 0 }}>
          
            <CardMedia
              component="img"
              image={`http://localhost:8083${articleInfo.imagePath}`}
              alt="green iguana"
              style={{ position: 'absolute', top: 0, left: -30, width: '100%', height: '100%' }}
            />
          </div>
          <CardContent>
            <Typography gutterBottom variant="h5" component="div" className='naslov'>
              {title} 
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {subtitle.doctor + ", " + subtitle.category}
            </Typography>
            <Typography variant="body2" color="text.secondary">
            
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    </ThemeProvider>
    {isOpen ? <ArticleDrawer articleInfo={articleInfo} isOpen={isOpen} setIsOpen={setIsOpen}/> : <></>}
    
    </div>
  );
}
