

type LoginDto = {
  email: string | undefined;
  password: string | undefined;
}

type SignUpDto = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

type LoginResponse = {
  token: string;
}

export type {LoginDto, SignUpDto, LoginResponse};