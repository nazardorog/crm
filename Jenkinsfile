pipeline {
    agent any

    parameters {
        string(
            name: 'TEST_CLASS',
            defaultValue: 'web.expedite.ui.WEU003_Truck',
            description: 'Введи повне ім’я тестового класу, який потрібно запустити'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stage('Run Test') {
            steps {
                echo "Запускаємо тест: ${params.TEST_CLASS}"

                sh """
                    docker run --rm \\
                      -v \$(pwd):/app \\
                      -w /app \\
                      your-docker-image \\
                      mvn test -Dtest=${params.TEST_CLASS} -DfailIfNoTests=false
                """
            }
        }

        stage('Show Allure Results') {
            steps {
                sh '''
                    echo "Вміст папки з Allure результатами:"
                    ls -la target/allure-results || echo "Папка target/allure-results відсутня"
                '''
            }
        }
    }

    post {
        always {
            echo "Pipeline завершено"
        }
    }
}