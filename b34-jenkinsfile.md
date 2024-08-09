
```groovy
pipeline {
    agent any

    stages {
        stage('code-checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/abhipraydhoble/Project-InsureMe.git'
            }
        }
        stage('code-build') {
            steps {
            sh 'mvn clean package'
            }
        }
        stage('docker') {
            steps {
            sh 'docker build -t abhipraydh96/project:p1 .'
            
            }
        }
        stage('Docker-Push') {
            steps {
       	       withCredentials([usernamePassword(credentialsId: 'docker-cred', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push abhipraydh96/project:p1'
           }
        }
      }
      stage('Code-Deploy') {
        steps {
           sh 'docker run -d -p 8089:8081 abhipraydh96/project:p1'
        }
      }
    }
}
```
