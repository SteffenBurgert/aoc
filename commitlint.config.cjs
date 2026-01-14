const {execSync} = require('child_process');

module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'header-max-length': [2, 'always', 100],
    'frontend-backend-exclamation': [2, 'always'],
  },
  plugins: [
    {
      rules: {
        'frontend-backend-exclamation': ({header}) => {
          let changedFiles = '';

          try {
            if (process.env.CI) {
              changedFiles = execSync(
                  'git diff-tree --no-commit-id --name-only -r HEAD').toString();
            } else {
              changedFiles = execSync(
                  'git diff --cached --name-only').toString();
            }
          } catch (e) {
            return [true];
          }

          const hasFrontend = changedFiles.includes('frontend/');
          const hasBackend = changedFiles.includes('backend/');
          const hasExclamation = /^[a-z]+(\(.*\))?!:/.test(header);

          if (hasFrontend && hasBackend) {
            return [
              hasExclamation,
              '❌ BOTH /frontend and /backend modified: You MUST include "!" (e.g., feat!: description)',
            ];
          }

          return [
            !hasExclamation,
            '❌ The "!" is ONLY allowed when BOTH /frontend AND /backend are modified.',
          ];
        },
      },
    },
  ],
};