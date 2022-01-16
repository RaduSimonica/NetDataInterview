package jenkins

pipeline {
    agent any
    stages {
        stage("Cleanup") {
            steps {
                cleanWs()
            }
        }
        stage("Git Checkout") {
            steps {
                git branch: 'master', url: 'https://github.com/RaduSimonica/NetDataInterview.git'
            }
        }
        stage("Deploy netdata") {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    build job: 'Deploy Netdata', wait=true
                }
            }
        }
        stage("Run tests!") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    sh "mvn clean test"
                }
            }
        }
    }
}