"use client";
import {createTheme} from '@mui/material/styles';
import {green, grey, teal} from '@mui/material/colors';

const theme = createTheme({
  palette: {
    primary: {
      main: green[900],
    },
    secondary: {
      main: teal[100],
    },
  },
});

export default theme;