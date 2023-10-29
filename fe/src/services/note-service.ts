import {authAxios} from "@/services/axios/axios";
import {NoteDto} from "@/types/note/note-types";

const notesEndpoint = 'api/notes';

async function addNote(note: NoteDto) {
  const addEndpoint = notesEndpoint + '/new';
  return await authAxios.post<NoteDto>(addEndpoint, note)
    .then((response) => {
      return response.data;
    });
}

async function getNotes(page: number = 0, size: number = 10) {
  const getAllEndpoint = notesEndpoint + '/all';
  return await authAxios.get<NoteDto[]>(getAllEndpoint, {params: {page: page, size: size}})
    .then((response) => {
      return response.data;
    });
}

export {addNote, getNotes};