import Typography from "@mui/material/Typography/Typography";
import {Paper} from "@mui/material";
import {Note} from "@/types/note/note-types";


export default function Note({note}: { note: Note }) {
  return (
      <Paper elevation={3}
             sx={{
               padding: 2, height: '350px', overflow: 'hidden'
             }}>
        <Typography variant="h6" color="primary" gutterBottom>
          {note.title}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          {note.content}
        </Typography>
      </Paper>
  )
}