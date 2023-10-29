"use client";
import {useSelector} from "react-redux";
import {RootState} from "@/redux/global-store";
import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography/Typography";
import {Fab, Grid, Pagination} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import Container from "@mui/material/Container";
import {useRouter} from "next/navigation";
import Note from "@/components/note/note";
import Link from "next/link";
import {getNotes} from "@/services/note-service";
import {NoteDto} from "@/types/note/note-types";
import Loading from "@/components/loading/loading";
import Box from "@mui/material/Box";

export default function MyNotes() {
  const [notes, setNotes] = useState<NoteDto[] | null>(null);
  const [page, setPage] = useState<number>(0);
  const [size] = useState<number>(10);

  const router = useRouter();
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  function deleteNote(id: number) {
    if (notes) {
      setNotes(notes.filter((note) => note.id !== id));
    }
  }

  useEffect(() => {
    getNotes(page, size)
    .then((notes) => {
      setNotes(notes);
    })
  }, [page, size]);

  if (!isAuthenticated) {
    router.push('/auth/login');
    return (<Typography variant={'h5'}>Redirecting...</Typography>);
  }

  if (!notes) {
    return (<Loading title={'Loading notes...'}/>)
  }

  return (
      <Container sx={{mt: 5, display: 'flex', flexDirection: 'column', minHeight: '70%'}}
                 component="main" maxWidth="xl">
        <Box component={'div'}>
          <Typography variant="h4" color="primary" gutterBottom>
            My Notes
          </Typography>
          <Grid container spacing={2}>
            {notes.map((note) => (
                <Grid key={note.id} item xs={12} sm={6} md={4}>
                  <Note removeNodeFromArray={deleteNote} note={note}/>
                </Grid>
            ))}
          </Grid>
          <Link href={'/notes/add'}>
            <Fab sx={{position: 'fixed', right: '20%', bottom: '50px'}}
                 color={'secondary'} variant="extended"
            >
              <EditIcon sx={{mr: 1}}/>
              Add Note
            </Fab>
          </Link>
        </Box>
        <Box flex={'1'} component={'div'} display={'flex'}
             justifyContent={'center'} alignItems={'end'} mt={15}>
          <Pagination count={10} onChange={(e, value) => setPage(value - 1)} color="primary"/>
        </Box>
      </Container>
  );
}
