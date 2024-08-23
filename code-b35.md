```groovy
pipeline {
    agent any
    tools {
        maven 'maven'
    
    }
    stages {
        stage('code-checkout'){
            steps{
               git branch: 'main', changelog: false, credentialsId: 'github-cred', poll: false, url: 'https://github.com/abhipraydhoble/Project-InsureMe.git' 
            }
        }
        
        stage('code-build'){
            steps{
                sh 'mvn clean package'
            }
        }
        
        stage('doc-image'){
            steps{
                 sh 'docker build -t abhipraydh96/b35 .'
            }
        }
        stage('code-deploy'){
            steps{
                sh 'docker run -itd --name p35 -p 8089:8081 abhipraydh96/b35'
            }
        }
    }
}
```
