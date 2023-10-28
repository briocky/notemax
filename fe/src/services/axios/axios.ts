import axios from "axios";
import {getToken} from "@/services/token-service";

const host = 'http://localhost:8080/';

export const axiosInstance = axios.create({
  baseURL: host
});

export const axiosInstanceWithAuth = axios.create({
  baseURL: host,
  headers: {
    Authorization: `Bearer ${getToken()}`
  }
});
