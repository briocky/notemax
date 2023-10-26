
const TOKEN_KEY_NAME = 'access_token';

function setToken(token: string) {
  if (token === null) {
    throw new Error('Token is null!');
  }

  return localStorage.setItem(TOKEN_KEY_NAME, token);
}

function deleteToken(token: string) {
  if (token === null) {
    throw new Error('Token is null!');
  }

  return localStorage.removeItem(TOKEN_KEY_NAME);
}

export { setToken, deleteToken };