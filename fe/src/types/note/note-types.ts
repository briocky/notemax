
type LinkDto = {
  id: number | undefined;
  url: string | undefined;
}

type NoteDto = {
  id: number | undefined;
  title: string | undefined;
  content: string | undefined;
  createdAt: string | undefined;
  modifiedAt: string | undefined;
  links: LinkDto[] | undefined;
}

export type {NoteDto, LinkDto};