import {LoginDto, AuthResponse, SignUpDto} from "@/types/auth/auth-types";
import {axiosInstance as axios} from "@/services/axios/axios";

async function signIn(data: LoginDto) {
  const loginEndpoint = 'api/auth/login';

  return await axios.post<AuthResponse>(loginEndpoint, data)
    .then((response) => {
      return response.data;
    });
}

async function signUp(data: SignUpDto) {
  const registerEndpoint = 'api/auth/register';
  return await axios.post<AuthResponse>(registerEndpoint, data)
  .then((response) => {
    return response.data;
  });
}

export {signIn, signUp};