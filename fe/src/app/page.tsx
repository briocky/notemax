import Container from '@mui/material/Container'
import Typography from "@mui/material/Typography/Typography";
import Button from "@mui/material/Button";
import Link from 'next/link';

export default function Home() {
  return (
      <Container sx={{textAlign: 'center', mt: 10}}>
        <Typography variant="h2" color="primary" sx={{mb: 4}}>
          Notes App
        </Typography>
        <Typography variant="h5" color="textSecondary" sx={{mb: 6}}>
          Effortlessly organize your thoughts and ideas.
        </Typography>
        <Link href={'/notes/my-notes'}>
          <Button variant="contained" color="primary" size="large">
            Get Started
          </Button>
        </Link>
      </Container>
  )
}
