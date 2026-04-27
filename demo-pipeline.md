
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



