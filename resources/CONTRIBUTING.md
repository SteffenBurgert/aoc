# 🚀 Commit Guidelines

### 📝 1. Commit Message Format

Every commit message must follow this structure:

```text
<type>(optional scope): <description>
```

> Example: feat(auth): add login functionality

#### Allowed Types:

We use the standard types from @commitlint/config-conventional:

* feat: A new feature.
* fix: A bug fix.
* chore: Changes to the build process or auxiliary tools (like Husky/Commitlint).
* docs: Documentation changes.
* style: Formatting, missing semicolons, etc. (no code change).
* refactor: Refactoring a specific feature.
* test: Adding or updating tests.

### ⚡ 3. The Fullstack Rule (!)

This repo uses an exclamation mark (`!`) to flag commits that involve architectural changes across
both layers.

| Scenarios             | Header Format     | Example                        |
|-----------------------|-------------------|--------------------------------|
| Frontend changes only | `<type>: <desc>`  | feat: update dashboard ui      |
| Backend changes only  | `<type>: <desc>`  | fix: resolve api timeout       |
| Both layers modified  | `<type>!: <desc>` | feat!: implement user profiles |

> [IMPORTANT]
> This rule is strictly enforced by our CI/CD pipeline and local hooks.

#### Scenarios:

* **Frontend only:** `feat: update user profile page` ✅
* **Backend only:** `fix: add validation to user-endpoint` ✅
* **Both Layers:** **`feat!: implement full-stack authentication`** 🚀

#### Rules Summary:

* **REQUIRED:** If files in **BOTH** `/frontend` and `/backend` are modified.
* **FORBIDDEN:** If changes are restricted to **ONLY ONE** of these folders.

### 🤖 4. Automatic Validation

Your commits are checked in two stages to ensure consistency:

1. Locally (Pre-commit): A Husky hook runs commitlint before the commit is created.
   It checks your staged files against your message.
2. Pipeline (CI): A GitHub Action verifies every push and pull request.
   This cannot be bypassed and ensures the repository history remains clean.

### 🛠 5. Troubleshooting

If your commit is rejected with the following error:

```text
❌ BOTH /frontend and /backend modified: You MUST include "!"
```

### How to fix:

You don't need to create a new commit. Just amend your last message:

1. Add the `!` after the type (e.g., `feat!: your description`).
2. Save and close the editor.
3. The hook will re-validate automatically.