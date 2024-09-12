pipeline {
    agent any
    tools{
        maven 'maven'
    }
    
    stages{
        stage('git'){
            steps{
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/abhipraydhoble/Project-InsureMe.git'
            }
        }
        stage('build'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage ('image'){
            steps{
                sh 'docker build -t testapp .'
            }
        }
        stage('deploy'){
            steps{
                sh ' docker run -itd -p 8089:8081 testapp'
            }




            
        }
    }
}
