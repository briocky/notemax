"use client";
import Container from "@mui/material/Container";
import * as React from "react";
import {useEffect, useState} from "react";
import {NoteDto} from "@/types/note/note-types";
import {useRouter} from "next/navigation";
import {addNote, getNoteById, updateNote} from "@/services/note-service";
import Alert from "@mui/material/Alert/Alert";
import Link from "next/link";
import Typography from "@mui/material/Typography/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import Loading from "@/components/loading/loading";

export default function EditPage({params}: { params: { id: number } }) {
  const [note, setNote] = useState<NoteDto | null>(null);
  const [updatedNote, setUpdatedNote] = useState<NoteDto | null>(null);
  const router = useRouter();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const note: NoteDto = {
      title: formData.get('title')?.toString(),
      content: formData.get('content')?.toString()
    } as NoteDto;

    updateNote(params.id, note)
    .then((note: NoteDto) => {
      setUpdatedNote(note);
      setTimeout(() => {
        router.replace('/notes/my-notes');
      }, 3000)
    })
  }

  useEffect(() => {
    getNoteById(params.id)
    .then((note) => {
      setNote(note);
    });
  }, [params.id]);

  if (!note) {
    return (<Loading title={'Loading note...'}/>);
  }

  return (
      <Container sx={{mt: 5}} component="main" maxWidth="sm">
        {updatedNote && (
            <Alert variant="outlined" severity="success">
              Note({updatedNote.title}) updated successfully — <strong><Link
                href={'/notes/my-notes'}>check it out!</Link></strong>
            </Alert>)}
        <Typography variant="h4" color="primary" gutterBottom>
          Edit Note
        </Typography>
        <Box component={'form'} onSubmit={handleSubmit} display={'flex'} flexDirection={'column'}
             gap={2}>
          <TextField id="title" name="title" label="Title" value={note.title}
                     onChange={(e) => setNote({...note, title: e.target.value})}
                     variant="outlined" required/>
          <TextField
              id="content"
              name="content"
              label="Content"
              multiline
              minRows={4}
              value={note.content}
              onChange={(e) => setNote({...note, content: e.target.value})}
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
  );
}