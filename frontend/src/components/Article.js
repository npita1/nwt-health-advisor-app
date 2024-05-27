import * as React from 'react';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';
import { Image } from '@chakra-ui/react';
import '../styles/Articles.css';


const theme = createTheme();

export default function Article({ title, description, image }) {
  return (
    <ThemeProvider theme={theme}>
      <Card sx={{ maxWidth: 345, margin: '10px' }}>
        <CardActionArea style={{padding: '0'}}>
          <div style={{ position: 'relative', paddingBottom: '56.25%', height: 0 }}>
            <CardMedia
              component="img"
              image="images/StaffPage/article.png"
              alt="green iguana"
              style={{ position: 'absolute', top: 0, left: -30, width: '100%', height: '100%' }}
            />
          </div>
          <CardContent>
            <Typography gutterBottom variant="h5" component="div" className='naslov'>
              {title}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {description}
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    </ThemeProvider>
  );
}
