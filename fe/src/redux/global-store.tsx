import {configureStore} from "@reduxjs/toolkit";
import userReducer from './features/user/user-slice';

export const globalStore = configureStore({
  reducer: {
    user: userReducer,
  },
})

export type RootState = ReturnType<typeof globalStore.getState>
export type AppDispatch = typeof globalStore.dispatch