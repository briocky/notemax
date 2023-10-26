import {LoginDto, AuthResponse, SignUpDto} from "@/types/auth/auth-types";
import axios from "axios";

const host = 'http://localhost:8080/';

async function signIn(data: LoginDto) {
  const loginEndpoint = host + 'api/auth/login';

  return await axios.post<AuthResponse>(loginEndpoint, data)
    .then((response) => {
      return response.data;
    });
}

async function signUp(data: SignUpDto) {
  const registerEndpoint = host + 'api/auth/register';
  return await axios.post<AuthResponse>(registerEndpoint, data)
  .then((response) => {
    return response.data;
  });
}

export {signIn, signUp};