import Typography from "@mui/material/Typography/Typography";
import {Paper} from "@mui/material";
import {NoteDto} from "@/types/note/note-types";
import Box from "@mui/material/Box";


export default function Note({note}: { note: NoteDto }) {
  return (
      <Paper elevation={3}
             sx={{
               padding: 2, height: '350px'
             }}>
        <Box display={'flex'} height={'100%'} position={'relative'} flexDirection={'column'}>
          <Typography variant="h6" color="primary" gutterBottom>
            {note.title}
          </Typography>
          <Typography variant="body2" color="textSecondary" overflow={'hidden'} maxHeight={'80%'}>
            {note.content}
          </Typography>
          <Typography position={'absolute'} bottom={'0'} variant="caption" color="textSecondary">
            {note.updatedAt ? `Updated: ${note.updatedAt}`: `Created: ${note.createdAt}`}
          </Typography>
        </Box>
      </Paper>
  )
}