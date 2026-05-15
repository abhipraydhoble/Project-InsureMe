

```md
# Project InsureMe - CI/CD Pipeline (GitHub Actions + AWS EC2 Self-Hosted Runner)

This project demonstrates a complete **CI/CD pipeline using GitHub Actions, Docker, and AWS EC2 self-hosted runner**.

---

# 🚀 Architecture Flow

```

GitHub Repository
↓
GitHub Actions Pipeline
↓
AWS EC2 Self-Hosted Runner
↓
Maven Build + Docker Build
↓
Docker Container Deployment on EC2
↓
Application Access via Public IP

````

---

# ⚙️ Tech Stack

- Java
- Maven
- Docker
- GitHub Actions
- AWS EC2 (Ubuntu)
- Self-Hosted GitHub Runner

---

# 🖥️ EC2 Setup

## 1. Update system
```bash
sudo apt update -y
sudo apt upgrade -y
````

---

## 2. Install Java

```bash
sudo apt install openjdk-17-jdk -y
```

---

## 3. Install Maven

```bash
sudo apt install maven -y
```

---

## 4. Install Docker

```bash
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker

# Fix permission (quick fix)
sudo chmod 777 /var/run/docker.sock
```

---

# 🤖 Setup GitHub Self-Hosted Runner on EC2

## Step 1: Open GitHub Runner Settings

Go to:

[https://github.com/abhipraydhoble/Project-InsureMe/settings/actions/runners](https://github.com/abhipraydhoble/Project-InsureMe/settings/actions/runners)

Click:
👉 New self-hosted runner

Select:

* OS: Linux
* Architecture: x64

---

## Step 2: Download Runner on EC2

```bash
mkdir actions-runner && cd actions-runner

curl -o actions-runner.tar.gz -L https://github.com/actions/runner/releases/download/v2.325.0/actions-runner-linux-x64-2.325.0.tar.gz

tar xzf actions-runner.tar.gz
```

---

## Step 3: Configure Runner

Run the command provided by GitHub:

```bash
./config.sh --url https://github.com/abhipraydhoble/Project-InsureMe --token YOUR_TOKEN
```

---

## Step 4: Start Runner

```bash
./run.sh
```

You should see:

```
Listening for Jobs
```

---

## (Optional) Run Runner as Service

```bash
sudo ./svc.sh install
sudo ./svc.sh start
```

---

# 🧪 GitHub Actions Workflow

Create file:

```
.github/workflows/main.yml
```

---

## Workflow Code

```yaml
name: CI Pipeline

on:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: self-hosted

    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Build Maven Project
        run: mvn clean package

      - name: Build Docker Image
        run: docker build -t demo .

      - name: Run Docker Container
        run: |
          docker rm -f c1 || true
          docker run -d --name c1 -p 8089:8081 demo
```

---

# 🌐 Access Application

After successful pipeline execution:

```
http://<EC2-PUBLIC-IP>:8089
```

Example:

```
http://13.233.xx.xx:8089
```

---

# 📊 CI/CD Flow

```
Code Push to GitHub
        ↓
GitHub Actions Triggered
        ↓
Self-Hosted Runner (EC2)
        ↓
Maven Build
        ↓
Docker Build & Run
        ↓
Application Live on EC2
```

---

# ❗ Common Issues

## 1. Job stuck in QUEUED

Runner is not running.

Fix:

```bash
cd actions-runner
./run.sh
```

---

## 2. App not accessible

Check EC2 Security Group:

Open port:

```
8089
```

---

## 3. Docker permission error

Fix:

```bash
sudo chmod 777 /var/run/docker.sock
```

---

# 🎯 Summary

* GitHub Actions triggers CI pipeline
* EC2 acts as self-hosted runner
* Docker builds and runs app
* App is accessible via EC2 public IP

---



```

---

