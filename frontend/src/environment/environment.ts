import {Environment} from '../environment.interface';

const backendUrl = 'http://localhost:8080/';

export const environment: Environment = {
  production: false,
  baseUrl: backendUrl,
};
