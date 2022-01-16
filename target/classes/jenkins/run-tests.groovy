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
                git branch: 'Grid', url: 'https://github.com/RaduSimonica/something-something.git'
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