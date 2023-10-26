import type {Metadata} from 'next'
import {Inter} from 'next/font/google'
import './globals.css'
import theme from './theme';
import {ThemeProvider} from "@mui/material";
import Navbar from "@/components/navbar/navbar";
import {ReduxProvider} from "@/redux/provider";

const inter = Inter({subsets: ['latin']})

export const metadata: Metadata = {
  title: 'Notemax',
  description: 'Notes management app',
}

export default function RootLayout({children,}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <ReduxProvider>
          <ThemeProvider theme={theme}>
            <Navbar/>
            {children}
          </ThemeProvider>
        </ReduxProvider>
      </body>
    </html>
  )
}
