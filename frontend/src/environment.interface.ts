const projectPath = 'SteffenBurgert/aoc';
const yearsPath = 'main/backend/src/main/kotlin/aoc/backend/service/year';

export const aocUrl = 'https://adventofcode.com'
export const githubUrl = `https://github.com/${projectPath}`;
export const rawGithubUrl = `https://raw.githubusercontent.com/${projectPath}`;
export const githubPathToYear = `tree/${yearsPath}`;
export const rawGithubPathToYear = `refs/heads/${yearsPath}`;

export interface Environment {
  production: boolean;
  baseUrl: string;
  aocUrl: string;
  githubUrl: string;
  rawGithubUrl: string;
  githubPathToYear: string;
  rawGithubPathToYear: string;
}
