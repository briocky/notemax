"use client";
import Container from "@mui/material/Container/Container";
import Loading from "@/components/loading/loading";
import {useEffect, useState} from "react";
import {NoteDto} from "@/types/note/note-types";
import {getNoteById} from "@/services/note-service";
import Typography from "@mui/material/Typography/Typography";
import ListItem from "@mui/material/ListItem/ListItem";
import ListItemText from "@mui/material/ListItemText/ListItemText";
import {List, Paper} from "@mui/material";


export default function NoteDetails({params}: { params: { id: number } }) {
  const [note, setNote] = useState<NoteDto | null>(null);

  useEffect(() => {
    getNoteById(params.id)
    .then((note) => {
      setNote(note);
    });
  }, [params.id]);

  if (!note) {
    return (<Loading title={'Loading note...'}/>)
  }

  return (
      <Container component="main" maxWidth="sm">
        <Typography variant="h4" color="primary" gutterBottom mt={3}>
          Note Details
        </Typography>
        <Paper sx={{p: 4, mt: 4}}>
          <Typography variant="h5" gutterBottom>
            Title
          </Typography>
          <Typography variant="body1" gutterBottom>
            {note.title}
          </Typography>
          <Typography variant="h5" paragraph>
            Content
          </Typography>
          <Typography variant="body1" gutterBottom>
            {note.content}
          </Typography>
          <Typography variant="h6">
            Links
          </Typography>
          <List>
            {note.links?.map((link, index) => (
                <ListItem key={index}>
                  <ListItemText primary={(index + 1) + ". " + link.url}/>
                </ListItem>
            ))}
          </List>
        </Paper>
      </Container>
  )
}