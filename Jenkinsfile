pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Привіт! Це тестовий пайплайн.'
            }
        }
        stage('Run a shell command') {
            steps {
                sh 'echo "Цей простий shell командний крок виконується"'
            }
        }
    }

    post {
        always {
            echo 'Пайплайн завершився.'
        }
    }
}