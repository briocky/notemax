import {axiosInstanceWithAuth as axios} from "@/services/axios/axios";
import {NoteDto} from "@/types/note/note-types";

const notesEndpoint = 'api/notes';

async function addNote(note: NoteDto) {
  const addEndpoint = notesEndpoint + '/new';
  return await axios.post<NoteDto>(addEndpoint, note)
    .then((response) => {
      return response.data;
    });
}

async function getNotes() {
  const getAllEndpoint = notesEndpoint + '/all';
  return await axios.get<NoteDto[]>(getAllEndpoint)
    .then((response) => {
      return response.data;
    });
}

export {addNote, getNotes};