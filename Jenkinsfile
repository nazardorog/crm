pipeline {
    agent any

    parameters {
        string(name: 'TEST_CLASSES', defaultValue: 'web.expedite.ui.WEU003_Truck,web.expedite.ui.WEU004_ExpediteFleet', description: 'Класи тестів через кому')
    }

    environment {
        ALLURE_RESULTS = "${env.WORKSPACE}/target/allure-results"
        ALLURE_REPORT = "${env.WORKSPACE}/target/allure-report"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stage('Run Tests in Parallel') {
            steps {
                script {
                    def tests = params.TEST_CLASSES.split(',')
                    def branches = tests.collectEntries {
                        ["Test-${it.trim()}" : {
                            sh """
                              mkdir -p target/allure-results/${it.trim()}
                              docker run --rm \\
                                -v ${env.WORKSPACE}:/app \\
                                -w /app \\
                                maven:3.8.6-openjdk-17 \\
                                mvn test -Dtest=${it.trim()} -Dallure.results.directory=target/allure-results/${it.trim()} -DfailIfNoTests=false
                            """
                        }]
                    }
                    parallel branches
                }
            }
        }

        stage('Aggregate Allure Results') {
            steps {
                sh """
                  rm -rf ${ALLURE_RESULTS}/*
                  mkdir -p ${ALLURE_RESULTS}
                  # Об’єднуємо всі allure-results з підпапок
                  find target/allure-results -mindepth 2 -type f -exec cp {} ${ALLURE_RESULTS} \\;
                """
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh "allure generate ${ALLURE_RESULTS} -o ${ALLURE_REPORT} --clean"
            }
            post {
                success {
                    echo "Allure report згенеровано"
                    publishHTML (target: [
                      allowMissing: false,
                      alwaysLinkToLastBuild: true,
                      keepAll: true,
                      reportDir: 'target/allure-report',
                      reportFiles: 'index.html',
                      reportName: 'Allure Report'
                    ])
                }
            }
        }
    }
}