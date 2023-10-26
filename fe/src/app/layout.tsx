import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import theme from './theme';
import {ThemeProvider} from "@mui/material";
import Navbar from "@/components/navbar/navbar";

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Notemax',
  description: 'Notes management app',
}

export default function RootLayout({children,}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <ThemeProvider theme={theme}>
        <body className={inter.className}>
          <Navbar />
          {children}
        </body>
      </ThemeProvider>
    </html>
  )
}
