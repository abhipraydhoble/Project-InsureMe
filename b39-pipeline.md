
```groovy
pipeline {
    agent any
    
    tools {
        maven 'maven'
    }
    
    
    stages{
        stage('Checkout') {
            steps {
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'git-cred', url: 'https://github.com/abhipraydhoble/Project-InsureMe.git']])
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Docker-Image'){
            steps{
                sh 'docker build -t abhipraydh96/devopsb39:v1 .'
                
            }
        }
        
        stage('Docker-Push'){
            steps {
       	       withCredentials([usernamePassword(credentialsId: 'docker-cred', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push abhipraydh96/devopsb39:v1'
               }
            }
        } 
        
        stage('Deploy'){
            steps{
                sh 'docker run -itd --name cont1 -p 8089:8081 abhipraydh96/devopsb39:v1'
            }
        }
    }
}
    

```



