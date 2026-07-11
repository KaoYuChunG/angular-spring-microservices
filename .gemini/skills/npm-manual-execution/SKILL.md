---
name: npm-manual-execution
description: Force manual execution of npm, yarn, or pnpm install commands. Use whenever an installation task is identified to prevent automatic, potentially hung, processes.
---

# npm Manual Execution Policy

## Core Instructions

- **Do NOT automatically execute** `npm install`, `yarn install`, or `pnpm install` commands.
- Whenever a task requires installing dependencies, output the exact command in a code block and explain to the user why it is needed.
- Explicitly ask the user to execute the command.
- Wait for the user to confirm that the installation is complete before proceeding to the next step of the task.

## Motivation
Automatic installation of heavy dependencies in a CLI wrapper often hides progress bars, leading to the appearance of a hung process. This skill ensures visibility and control over long-running I/O operations.
