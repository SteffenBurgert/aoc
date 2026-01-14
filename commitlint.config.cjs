const { execSync } = require('child_process');

module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'header-max-length': [2, 'always', 100],
    'frontend-backend-exclamation': [2, 'always'],
  },
  plugins: [
    {
      rules: {
        'frontend-backend-exclamation': ({ header }) => {
          const changedFiles = execSync('git diff --cached --name-only').toString();
          const hasFrontend = changedFiles.includes('frontend/');
          const hasBackend = changedFiles.includes('backend/');
          const hasExclamation = /^[a-z]+(\(.*\))?!:/.test(header);

          if (hasFrontend && hasBackend) {
            return [
              hasExclamation,
              '❌ BOTH folders (frontend & backend) modified: You MUST include an "!" after the type (e.g., feat!: ...)',
            ];
          }

          return [
            !hasExclamation,
            '❌ The "!" is ONLY allowed when /frontend AND /backend are modified simultaneously.',
          ];
        },
      },
    },
  ],
};