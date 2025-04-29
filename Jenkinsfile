pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test in Docker') {
            steps {
                script {
                    docker.build('my-autotest-image').inside {
                        sh 'mvn clean test'
                    }
                }
            }
        }
    }
}