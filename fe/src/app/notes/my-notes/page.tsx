"use client";
import {useSelector} from "react-redux";
import {RootState} from "@/redux/global-store";
import {useEffect} from "react";
import Typography from "@mui/material/Typography/Typography";
import {Fab, Grid} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import Container from "@mui/material/Container";
import {useRouter} from "next/navigation";
import Note from "@/components/note/note";

const notes = [
  { id: 1, title: 'Note 1', content: 'This is the content of note 1.' },
  { id: 2, title: 'Note 2', content: 'Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. Content for note 2 goes here. Realy Long msg. ' },
  { id: 3, title: 'Note 2', content: 'Content for note 2 goes here.' },
  { id: 4, title: 'Note 2', content: 'Content for note 2 goes here.' },
  { id: 5, title: 'Note 2', content: 'Content for note 2 goes here.' },
  { id: 6, title: 'Note 2', content: 'Content for note 2 goes here.' },
];

export default function MyNotes() {

  const router = useRouter();
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  useEffect(() => {
    //TODO make request to get notes
  }, []);

  if (!isAuthenticated) {
    router.push('/auth/login');
    return (<Typography variant={'h5'}>Redirecting...</Typography>);
  }

  return (
      <Container sx={{ mt: 5 }} component="main" maxWidth="xl">
        <Typography variant="h4" color="primary" gutterBottom>
          My Notes
        </Typography>
        <Grid container spacing={2}>
          {notes.map((note) => (
              <Grid key={note.id} item xs={12} sm={6} md={4}>
                <Note note={note} />
              </Grid>
          ))}
        </Grid>
        <Fab sx={{position: 'fixed', right: '20%', bottom: '50px'}}
             color={'secondary'} variant="extended"
             href={'/notes/add'}
        >
          <EditIcon sx={{mr: 1}}/>
          Add Note
        </Fab>
      </Container>
  );
}
