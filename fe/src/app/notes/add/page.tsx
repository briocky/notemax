"use client";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography/Typography";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import AddIcon from '@mui/icons-material/Add';
import Button from "@mui/material/Button";
import Link from "next/link";
import * as React from "react";
import {NoteDto} from "@/types/note/note-types";
import {addNote} from "@/services/note-service";
import {useRouter} from "next/navigation";
import Alert from "@mui/material/Alert/Alert";

export default function AddNote() {

  const [createdNote, setCreatedNote] = React.useState<NoteDto | null>(null);
  const router = useRouter();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const note: NoteDto = {
      title: formData.get('title')?.toString(),
      content: formData.get('content')?.toString()
    } as NoteDto;

    addNote(note)
    .then((note: NoteDto) => {
      setCreatedNote(note);
      setTimeout(() => {
        router.replace('/notes/my-notes');
      }, 3000)
    })
  }

  return (
      <Container sx={{mt: 5}} component="main" maxWidth="sm">
        {createdNote && (
            <Alert variant="outlined" severity="success">
              Note({createdNote.title}) created successfully — <strong><Link
                href={'/notes/my-notes'}>check it out!</Link></strong>
            </Alert>)}
        <Typography variant="h4" color="primary" gutterBottom>
          New Note
        </Typography>
        <Box component={'form'} onSubmit={handleSubmit} display={'flex'} flexDirection={'column'}
             gap={2}>
          <TextField id="title" name="title" label="Title" variant="outlined" required/>
          <TextField
              id="content"
              name="content"
              label="Content"
              multiline
              minRows={4}
              variant="outlined"
              required/>
          <Box>
            <Typography variant="h6" color="primary" gutterBottom>
              Attachments
            </Typography>
            <Button variant={'outlined'} startIcon={<AddIcon/>}>Add</Button>
          </Box>
          <Box display={'flex'} justifyContent={'end'}>
            <Link href={'/notes/my-notes'}>
              <Button sx={{mr: 2}}>Cancel</Button>
            </Link>
            <Button type={'submit'} variant={'contained'} color={'primary'}>Save</Button>
          </Box>
        </Box>
      </Container>
  )
}