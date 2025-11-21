#  $$\color{red}  \textbf{Project} \ \  \textbf{InsureMe}$$
 

InsureMe was having trouble managing their software because it was all one big piece. </br>
As they grew bigger, it became even harder to manage. <br>

### $\color{orange} \textbf{Requirements}$

#### 1. Automated Deployment:</br>
Whenever a developer makes changes to the code and pushes them to the main branch of the Git repository, </br>
Jenkins should automatically start a deployment process.
</br>
#### 2. CI/CD Pipeline: </br>
Jenkins should: </br>

* Check out the latest code from the main branch.
* Compile and test the code to ensure it works correctly
* Package the application into a container using Docker.
* Deploy the containerized application

With DevOps Approch I used several devops tools such as  <br>

- Git: Managed code changes with version control. </br>
- Jenkins: Automated integration, testing, and deployment processes. </br>
- Docker: Containerized applications for consistency and scalability. </br>
- AWS: Provided infrastructure for hosting and deploying the application. </br>
 Together, these tools streamlined development, testing, and deployment, ensuring efficient management of the InsureMe project. </br>

### $\color{orange} \textbf{Project} \\ \textbf{Summary}$

- Create  EC2 instance on Amazon Web Services (AWS)
- These servers will host application and manage its deployment.
- Install Jenkins  server to automate the process of building, testing, and deploying application.
- Set up Jenkins to watch your code repository on GitHub.
- Whenever someone makes changes to the code and pushes them to GitHub, Jenkins automatically kicks off a process to update and deploy application.
- Used Docker to package application and its dependencies into a container, making it easy to deploy and run anywhere.
- After that write a pipeline in Jenkins to build, test, and deploy your application automatically.
- This pipeline runs every time someone makes changes to the code, ensuring that the latest version of your application is always available.
- Whenever someone pushes changes to the code, Jenkins pulls the latest code, builds the application, creates a Docker image, and pushes it to DockerHub (a service for storing Docker images).
- Then, it deploys the updated application 
- With this setup, you  can fully automated process for building, testing, and deploying your application.
- Whenever someone make changes to the code, Jenkins takes care of the rest, ensuring that your application is always up-to-date and running smoothly on your servers.</p>

## Project Steps

### Launch ubuntu instance(t2.medium)

### install jenkins
````
sudo apt update
sudo apt install fontconfig openjdk-21-jre  -y
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins -y
````
### install docker
````
sudo apt install docker.io -y
sudo systemctl start docker
sudo usermod -aG docker jenkins
sudo usermod -aG docker ubuntu
newgrp docker
sudo chmod 777 /var/run/docker.sock
````
### install maven
````
sudo apt install maven -y
````
### install below plugins
````
stage view
````
````
maven integration
````
````
aws credentials
````
````
s3 publisher
````
````
docker
````

### configure tools in Manage Jenkins-> Tools
<img width="1920" height="836" alt="image" src="https://github.com/user-attachments/assets/15c89fa2-5e98-432e-9687-9b8ead0edafa" />
<img width="1917" height="826" alt="image" src="https://github.com/user-attachments/assets/21c55a7c-0fe7-49f6-bf26-e7c547692e1c" />


### add credentials 
<img width="1892" height="498" alt="image" src="https://github.com/user-attachments/assets/9d473b5b-1945-4b53-a649-54aab597e9ea" />


### Click on Dashboard and create new project and select project type as *pipeline* project



### setup webhook
<img width="1176" height="871" alt="image" src="https://github.com/user-attachments/assets/0efbdf6d-0971-4543-96aa-d055153c3185" />
<img width="1895" height="802" alt="image" src="https://github.com/user-attachments/assets/db4c0ab3-f9ed-49dd-8d5a-f606dd6526ea" />
<img width="1918" height="669" alt="image" src="https://github.com/user-attachments/assets/fc755a17-9b42-4d0a-b5fa-9a870bf6436b" />
<img width="1345" height="652" alt="image" src="https://github.com/user-attachments/assets/a276934b-65cf-4ec8-b9fb-4c9fe3a60be0" />



```groovy
pipeline {
    agent any 
    tools{
        maven 'maven'
    }
    environment {

     S3_BUCKET = "cdec-b57-jenkins-s3-int"
     REGION = "ap-southeast-1"
     warFile = "target/Insurance-0.0.1-SNAPSHOT.jar"
     }
    stages {
        stage('code-pull'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/abhipraydhoble/Project-InsureMe.git']])
            }
        }
        stage('code-build'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage('code-push'){
            steps{
                withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws_cred', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                   sh 'aws s3 cp ${warFile} s3://${S3_BUCKET}/Artifacts/ --region ${REGION}'
                 }
            }
        }
       stage('docker-image'){
            steps{
                sh 'docker build -t abhipraydh96/insure .'
                
            }
        }
        
        stage('image-push'){
            steps {
       	       withCredentials([usernamePassword(credentialsId: 'docker_cred', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push abhipraydh96/insure'
               }
            }
        } 
        
        stage('code-deploy'){
            steps{
                sh 'docker run -itd --name insure-me -p 8089:8081 abhipraydh96/insure'
            }
        }
    }
}
```
