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
                    sh 'docker-compose down || true' // на випадок залишків
                    sh 'docker-compose up --build --abort-on-container-exit'
                    sh 'docker-compose down'
                }
            }
        }
    }
}