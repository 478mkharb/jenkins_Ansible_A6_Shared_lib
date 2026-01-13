# Jenkins Ansible Shared Library

This repository contains a **Jenkins Shared Library** for deploying Ansible playbooks with support for dynamic inventory, user approval, and notifications. The library is designed to simplify CI/CD pipelines, centralize logic, and make deployments reusable and environment-independent.

---

## Directory Structure

```bash
.
├── README.md
├── resources
│   └── ansible
│       └── config.yml           # Configuration file for environments, Git, Ansible, Slack
├── src                           # Reserved for Groovy classes (if needed)
└── vars
    ├── ansibleDeploy.groovy     # Main orchestrator pipeline function
    ├── approvalGate.groovy       # Handles user approval stage
    ├── checkoutCode.groovy       # Git checkout logic
    ├── loadConfig.groovy         # Loads configuration from config.yml
    ├── notifySlack.groovy        # Sends Slack notifications
    └── runPlaybook.groovy        # Executes the Ansible playbook
```

---

## Features

* **Git Checkout:** Pulls code from a specified branch and repository.
* **Dynamic Inventory Support:** Works with AWS EC2 dynamic inventory or any inventory file.
* **User Approval:** Optional manual approval stage before deployment.
* **Playbook Execution:** Runs Ansible playbooks with configurable forks.
* **Notifications:** Slack notifications for success and failure.
* **Config-driven:** All inputs (environment, inventory, playbook, Slack channel, etc.) are stored in `resources/ansible/config.yml`.
* **Reusable Functions:** Each major step is a global function in `vars/` for clean and maintainable pipelines.

---

## Getting Started

### 1. Add Library to Jenkins

1. Go to **Manage Jenkins → Configure System → Global Pipeline Libraries**.
2. Add a new library:

   * Name: `jenkins-ansible-shared-lib`
   * Default version: main/master branch
   * Retrieval method: Modern SCM (Git)

### 2. Use in Jenkinsfile

```groovy
@Library('jenkins-ansible-shared-lib') _
ansibleDeploy()
```

Place this Jenkinsfile at the **root of your project repository**.

### 3. Configure `config.yml`

Example snippet:

```yaml
ENVIRONMENT: prod
CODE_BASE_PATH: Assignment5
KEEP_APPROVAL_STAGE: true
GIT:
  URL: "https://github.com/your-org/your-repo.git"
  BRANCH: "main"
  CREDENTIALS_ID: "github-pat"
ANSIBLE:
  PLAYBOOK: site.yml
  INVENTORY: inventory/aws_ec2.aws_ec2.yml
  FORKS: 5
SLACK_CHANNEL_NAME: build-status
ACTION_MESSAGE: "Deployment triggered"
```

### 4. Add Ansible Playbooks & Roles

Your repository should contain:

* `site.yml` with roles
* `inventory/` folder (can be dynamic or static)
* `roles/` folder with all Ansible roles

### 5. Pipeline Execution

* Clone the repository
* Jenkins executes the shared library using `ansibleDeploy()`
* The library handles checkout, approval, playbook execution, and notifications

---

## Recommended Usage

* Keep your `Jenkinsfile` minimal and delegate logic to the shared library.
* Use separate `config.yml` for different environments (`prod.yml`, `qa.yml`, etc.)
* Use dynamic inventory for cloud deployments (AWS, Azure, GCP).
* Enable `KEEP_APPROVAL_STAGE` for production deployments.

---

## Contact

For questions or contributions, contact **Mukesh Kharb**.

---

*This library follows best practices for Jenkins Shared Libraries and Ansible CI/CD pipelines.*

