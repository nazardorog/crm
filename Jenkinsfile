pipeline {
    agent any

    parameters {
      choice name: 'MODULE', choices: ['bigTruck','expedite'], description: 'Вибери модуль'
      choice name: 'PACKAGE', choices: [
        'bigTruck.smoke.broker',
        'bigTruck.loadBoard',
        'expedite.full',
        'expedite.smoke'
      ], description: 'Вибери пакет тестів'
      string name: 'CLASSES', defaultValue: '*', description: 'Використай * або конкретні класи'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stages {
            stage('Checkout') {
                steps { checkout scm }
        }
        stage('Run tests') {
          steps {
            sh """
              mvn clean test \
                -Dtest=${PACKAGE}.${CLASSES} \
                -Dsurefire.useFile=false
            """
          }
        }

        stage('Allure results merge') {
            steps {
                sh """
                  mkdir -p target/allure-results
                  find target/allure-results/* -type d -exec cp -r {}/* target/allure-results/ \\;
                """
            }
        }
    }

    post {
        always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
            echo "Тест завершено: ${params.TEST_CLASS}"
        }
    }
}