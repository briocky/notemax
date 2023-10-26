"use client";
import { createTheme } from '@mui/material/styles';
import { purple } from '@mui/material/colors';

const theme = createTheme({
  palette: {
    primary: {
      main: purple[600],
    },
    secondary: {
      main: "#008F7A",
    },
  },
});

export default theme;