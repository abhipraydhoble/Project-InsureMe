# Project-InsureMe


## Tech Stack
✓ AWS - For creating ec2 machines as servers and deploy the web application. </br>
✓ Git - For version control for tracking changes in the code files </br>
✓ Jenkins - For continuous integration and continuous deployment  </br>
✓ Docker - For deploying containerized applications </br>
✓ Ansible - Configuration management tools  </br>
✓ Selenium - For automating tests on the deployed web application </br>

## Step 1: Create Infrastructure
Create two ec2 instance 
1. Master
2. Node
### Master
1. ami = ubuntu
2. instance type = t2.medium
3. ports =  22, 8080
   
Take SSH and Connect to Instance
## CI/CD Setup

### Install Jenkins for Automation:
### Install Jenkins on the EC2 instance to automate deployment: Install Java
sudo apt update
sudo apt install  openjdk-11-jdk




#jenkins
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \   </br>
https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key  </br>
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
/etc/apt/sources.list.d/jenkins.list > /dev/null   </br>
sudo apt-get update    </br>
sudo apt-get install jenkins   </br>
sudo systemctl start jenkins    </br>
sudo systemctl enable jenkins   </br>


Access Jenkins in a web browser using the public IP of your EC2 instance.
publicIp:8080


### Install Necessary Plugins in Jenkins:

#### Goto Manage Jenkins →Plugins → Available Plugins →

##### Install below plugins:
1.Maven Integration plugin  </br>
2.Docker  </br>
3.Docker Commons  </br>
4.Docker Pipeline  </br>
5.Docker API  </br>
6.docker-build-step  </br>

##### Add tools in  Dashboard->Manage Jenkins-> Tools


#### Create Job in Jenkins : go to Dashboard->Item Name


As soon as the developer pushes the updated code on the GIT master branch, the Jenkins job should be triggered using a GitHub Webhook and Jenkins job should be triggered

To Create Webhook: go to settings of your github repository 
In Payload URL: Add yours jenkins url

In Jenkins Job ->Configuration->choose GitHub hook trigger for GITScm polling


## Install  Docker:

sudo apt install docker.io -y
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins

Add DockerHub Credentials:

To securely handle DockerHub credentials in your Jenkins pipeline, follow these steps: </br>
Go to "Dashboard" → "Manage Jenkins" → "Manage Credentials." </br>
Click on "System" and then "Global credentials (unrestricted)."  </br>
Click on "Add Credentials" on the left side.    </br>
Choose "Secret text" as the kind of credentials.  </br>
Enter your DockerHub credentials (Username and Password) and give the credentials an ID (e.g., "docker"). </br>
Click "OK" to save your DockerHub credentials. </br>

pipeline {
    
     agent any

    stages {
        stage('Code-Checkout') {
            steps {
              checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'github-token', url: 'https://github.com/kajol2699/Project-InsureMe.git']])
            }
        }
        
        stage('Code-Build') {
            steps {
               sh 'mvn clean package'
            }
        }
        
        stage('Containerize the application'){
            steps { 
               echo 'Creating Docker image'
               sh "docker build -t kaju912/insurance:latest ."
            }
        }
        
        stage('Docker Push') {
    	agent any
          steps {
       	withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push kaju912/insurance:latest'
        }
      }
    }
    
      
    stage('Code-Deploy') {
        steps {
           ansiblePlaybook credentialsId: 'ansible', installation: 'ansible', playbook: 'ansible-playbook.yml', vaultTmpPath: ''       
        }
      }
    
   }
}



Build Pipeline:




Final Output:
