## Launch ubuntu instance(t2.medium)

## install jenkins
````
https://www.jenkins.io/doc/book/installing/linux/#debianubuntu
````
**note:** -> first install java then jenkins

## install below plugins
````
stage view
````
````
maven integration
````
````
docker
````

## configure tools in Manage Jenkins-> Tools
<img width="1920" height="836" alt="image" src="https://github.com/user-attachments/assets/15c89fa2-5e98-432e-9687-9b8ead0edafa" />
<img width="1917" height="826" alt="image" src="https://github.com/user-attachments/assets/21c55a7c-0fe7-49f6-bf26-e7c547692e1c" />

## Click on Dashboard and create new project and select project type as *pipeline* project



```pipeline
pipeline {
    agent any

    tools {
        maven 'maven'
        
    }
    
    stages{
        stage('code-pull'){
            steps{
                git branch: 'main', url: 'https://github.com/abhipraydhoble/Project-InsureMe.git'
            }
        }

        stage('code-build'){
            steps{
                sh 'mvn clean package'
            }
        }

        stage('code-deploy'){
            steps{
                sh 'docker build -t devops-b53 .'
                sh 'docker run -itd --name app-1 -p 8089:8081 devops-b53'
            }
        }
    }
}
```
