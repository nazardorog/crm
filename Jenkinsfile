pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run docker-compose tests') {
            steps {
                script {
                    sh 'docker-compose down || true'
                    sh 'docker-compose up --build --abort-on-container-exit'
                    sh 'docker-compose down'
                }
            }
        }
    }
}