"use client";
import Container from "@mui/material/Container";
import * as React from "react";
import {useEffect, useState} from "react";
import {LinkDto, NoteDto} from "@/types/note/note-types";
import {useRouter} from "next/navigation";
import {getNoteById, updateNote} from "@/services/note-service";
import Alert from "@mui/material/Alert/Alert";
import Link from "next/link";
import Typography from "@mui/material/Typography/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Loading from "@/components/loading/loading";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";

export default function EditPage({params}: { params: { id: number } }) {
  const [links, setLinks] = useState<LinkDto[]>([] as LinkDto[]);
  const [newLink, setNewLink] = React.useState<string>('');
  const [note, setNote] = useState<NoteDto | null>(null);
  const [updatedNote, setUpdatedNote] = useState<NoteDto | null>(null);
  const router = useRouter();

  function handleAddLink() {
    if (newLink) {
      setLinks([...links, {url: newLink} as LinkDto]);
      setNewLink('');
    }
  }

  function handleDeleteLink(linkId: number) {
    const updatedLinks = links.filter((link, idx) => idx !== linkId);
    setLinks(updatedLinks);
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const note: NoteDto = {
      title: formData.get('title')?.toString(),
      content: formData.get('content')?.toString(),
      links: links
    } as NoteDto;
  console.log(links)
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
      setLinks(note.links || []);
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
              Links
            </Typography>
            {links.map((link, idx) => (
                <Box key={idx} component={'div'} display={'flex'} justifyContent={'space-between'} alignItems={'center'} mb={2}>
                  <Typography variant={'body1'}>{(idx+1) + ". " + link.url}</Typography>
                  <Button onClick={() => handleDeleteLink(idx)} variant={'outlined'} startIcon={<DeleteIcon/>}>Delete</Button>
                </Box>
            ))}
            <Box component={'div'} display={'flex'}>
              <TextField placeholder={'Type your link'} size={'small'} variant={'outlined'} fullWidth value={newLink} onChange={(e) => setNewLink(e.target.value)}/>
              <Button sx={{ml: 1}} onClick={() => handleAddLink()} variant={'outlined'} startIcon={<AddIcon/>}>Add</Button>
            </Box>
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