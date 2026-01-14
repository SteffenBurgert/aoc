# 🏷️ Versioning Strategy

This project uses automated versioning powered by GitHub Actions. Versions are calculated
dynamically based on your commit history and the specific service being deployed.

---

## 1. How Versions are Calculated

The versioning process follows these steps:

1. **Tag Detection**: The system looks for the latest Git tag starting with `${service-name}-v`.
2. **Semantic Increment**: Based on the [Conventional Commits](https://www.conventionalcommits.org/)
   in your Pull Request, the tool decides whether to increment the **Major**, **Minor**, or **Patch
   ** version.
3. **Branch-Specific Formatting**: The final version string is formatted differently depending on
   the target branch to distinguish between stable releases and development snapshots.

---

## 2. Release vs. Snapshot

The version format depends on the branch:

### 🚀 Stable Releases (Main Branch)

When code is merged into `main`, the full Semantic Version is used for the formal release.

* **Format**: `<version>`
* **Example**: `1.2.0`

### 🏗️ Snapshots (Development Branches)

For any branch other than `main`, the system creates a **SNAPSHOT** version. To keep the identifier
clean, the trailing zero is removed from the version number before appending the branch information.

* **Format**: `<trimmed-version>-SNAPSHOT-<branch-name>`
* **Example**: `1.2.0-SNAPSHOT-feature-login` (derived from version `1.2.0`)

---

## 3. How to Influence the Version

Since the version is derived from your commit messages, you can control the next version number by
using the correct prefix:

| Commit Prefix       | Version Impact             | Example                  |
|:--------------------|:---------------------------|:-------------------------|
| `feat!:` or `fix!:` | **Major** (1.0.0 -> 2.0.0) | `feat!: breaking change` |
| `feat:`             | **Minor** (1.0.0 -> 1.1.0) | `feat: add new api`      |
| `fix:`              | **Patch** (1.0.0 -> 1.0.1) | `fix: resolve bug`       |

> [TIP]
> Changes affecting both `/frontend` and `/backend` **must** use the `!` prefix (e.g., `feat!:`),
> which will automatically trigger a **Major** version increment.

---

## 4. Automation Flow

1. **Developer** pushes a commit following the [Commit Guidelines](./CONTRIBUTING.md).
2. **GitHub Action** triggers the `version` workflow.
3. **mathieudutour/github-tag-action** calculates the base semantic version.
4. **Formatting Script**:
    * If on `main`: The full version is passed through.
    * If on other branches: The trailing zero is removed via `sed` (e.g., `1.2.0-SNAPSHOT-test.0` ->
      `1.2.0-SNAPSHOT-test`) and the branch suffix is added.
5. **Output**: The final version string is used for tagging Docker images or build artifacts.

---

> [TIP]
> Always use descriptive [Conventional Commits](./CONTRIBUTING.md) to ensure the version increments
> correctly and reflects the actual impact of your changes.