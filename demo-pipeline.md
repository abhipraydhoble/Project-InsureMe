
```groovy
pipeline {
    agent any
    tools {
       maven 'maven-3'
     }

    
    stages {
        stage('pull') {
            steps {
                 git branch: 'main', url: 'https://github.com/abhipraydhoble/Project-InsureMe.git'
            }
          
        }
        stage('build') {
            steps{
                sh 'mvn clean package'
            }
        }
        
        stage('deploy'){
            steps {
                sh 'docker build -t demo .'
                sh 'docker run -itd --name c1 -p 8089:8081 demo'
            }
        }
    }
}
    

```
### new pipeline
````
pipeline {
    agent any 
    tools{
        maven 'maven'
    }
    environment {
     S3_BUCKET = "project-insure-me-build-artifacts-store-oncdecb36"
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
                withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws-cred', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                   sh 'aws s3 cp ${warFile} s3://${S3_BUCKET}/Artifacts/ --region ${REGION}'
                 }
            }
        }
       stage('docker-image'){
            steps{
                sh 'docker build -t abhipraydh96/insureb67 .'
                
            }
        }
        
        stage('image-push'){
            steps {
       	       withCredentials([usernamePassword(credentialsId: 'docker-cred', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push abhipraydh96/insureb67'
               }
            }
        } 
        
        stage('code-deploy'){
            steps{
                sh 'docker run -itd --name insure-me -p 8089:8081 abhipraydh96/insureb67'
            }
        }
    }
}
````



