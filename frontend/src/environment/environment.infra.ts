import {
  aocUrl,
  Environment,
  githubPathToYear,
  githubUrl,
  rawGithubPathToYear,
  rawGithubUrl
} from '../environment.interface';

const backendUrl = 'http://localhost:8080/';

export const environment: Environment = {
  production: false,
  baseUrl: backendUrl,
  aocUrl: aocUrl,
  githubUrl: githubUrl,
  rawGithubUrl: rawGithubUrl,
  githubPathToYear: githubPathToYear,
  rawGithubPathToYear: rawGithubPathToYear,
};
