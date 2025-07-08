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

        stage('Run Test in Docker') {
            steps {
                echo "Запускаємо тест: ${params.TEST_CLASS}"
                script {
                    def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')

                    // Очищаємо старі результати Allure
                    sh 'rm -rf target/allure-results || true'

                    def tests = params.TEST_CLASS.split(',')

                    def parallelStages = tests.collectEntries { testClass ->
                        ["${testClass}": {
                            sh """
                                echo "==> Запускаємо тест: ${testClass}"
                                docker run --rm \
                                    --network shared_network \
                                    -v "${hostWorkspace}":/app \
                                    -w /app \
                                    -e RUN_ENV=jenkins \
                                    maven:3.8-openjdk-17 \
                                    mvn test \
                                        -Dtest=${params.TEST_CLASS} \
                                        -DfailIfNoTests=false
                            """
                        }]
                    }

                    parallel parallelStages
                }
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