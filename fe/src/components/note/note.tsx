import Typography from "@mui/material/Typography/Typography";
import {Paper} from "@mui/material";
import {NoteDto} from "@/types/note/note-types";
import Box from "@mui/material/Box";
import IconButton from '@mui/material/IconButton';
import EditIcon from '@mui/icons-material/Edit';
import Tooltip from "@mui/material/Tooltip";
import DeleteIcon from '@mui/icons-material/Delete';
import Link from "next/link";
import {deleteNote} from "@/services/note-service";
import {useRouter} from "next/navigation";

export default function Note({note, removeNodeFromArray}: { note: NoteDto, removeNodeFromArray: (id: number) => void }) {
  const router = useRouter();

  function handleDelete() {
    if (!note.id) {
      return;
    }
    const id = note.id;

    deleteNote(id)
    .then(() => {
      removeNodeFromArray(id);
      router.refresh();
    });
  }

  return (
      <Paper elevation={3}
             sx={{
               padding: 2, height: '350px', transition: '0.2s', '&:hover': {cursor: 'pointer'}
             }}>
        <Box display={'flex'} height={'100%'} position={'relative'} flexDirection={'column'}>
          <Box component={'div'} display={'flex'}>
            <Typography width={'80%'} variant="h6" color="primary" gutterBottom
                        onClick={() => router.push('/notes/' + note.id)}
                        sx={{'&:hover': {textDecoration: 'underline'}}}>
              {note.title}
            </Typography>
            <Box component={'div'} width={'20%'}>
              <Link href={'/notes/edit/' + note.id}>
                <Tooltip title={'Edit'} placement={'top'}>
                  <IconButton>
                    <EditIcon/>
                  </IconButton>
                </Tooltip>
              </Link>
              <Tooltip title={'Delete'} placement={'top'}>
                <IconButton color={'error'} onClick={() => handleDelete()}>
                  <DeleteIcon/>
                </IconButton>
              </Tooltip>
            </Box>
          </Box>
          <Typography variant="body2" color="textSecondary" overflow={'hidden'} maxHeight={'80%'}>
            {note.content}
          </Typography>
          <Typography position={'absolute'} bottom={'0'} variant="caption" color="textSecondary">
            {note.modifiedAt ? `Updated: ${note.modifiedAt}` : `Created: ${note.createdAt}`}
          </Typography>
        </Box>
      </Paper>
  )
}