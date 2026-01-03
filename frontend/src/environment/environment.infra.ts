import {
  aocUrl,
  Environment,
  githubPathToYear,
  githubUrl,
  rawGithubPathToYear,
  rawGithubUrl
} from '../environment.interface';

export const environment: Environment = {
  production: false,
  baseUrl: 'http://localhost:8080/',
  aocUrl: aocUrl,
  githubUrl: githubUrl,
  rawGithubUrl: rawGithubUrl,
  githubPathToYear: githubPathToYear,
  rawGithubPathToYear: rawGithubPathToYear,
};
