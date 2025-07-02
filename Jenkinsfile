pipeline {
    agent any

    parameters {
        string(name: 'TEST_CLASS', defaultValue: 'web.expedite.ui.WEU003_Truck', description: 'Повне ім’я класу тесту')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stage('Run Test in Docker') {
            steps {
                echo "Запускаємо тест: ${params.TEST_CLASS}"

                sh '''
                docker run --rm \\
                  -v ${WORKSPACE}:/app \\
                  -w /app \\
                  maven:3.8.6-openjdk-17-slim \\
                  mvn test -Dtest=${TEST_CLASS} -DfailIfNoTests=false
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline завершено.'
        }
    }
}