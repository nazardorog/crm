pipeline {
    agent any

    parameters {
        extendedChoice(
            name: 'TEST_CLASS',
            type: 'PT_CHECKBOX',
            multiSelectDelimiter: ',',
            defaultValue: '',
            description: 'Оберіть тести для запуску',
            value: 'web.expedite.ui.WEU001_LoadBoard,web.expedite.ui.WEU002_Broker,web.expedite.ui.WEU003_Truck,web.expedite.ui.WEU004_ExpediteFleet,web.expedite.smoke.broker.WES035_BrokerCreate, web.expedite.smoke.broker.WES001_LoadCreateBol'
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

        stage('Run Test in Docker') {
            steps {
                echo "Запускаємо тест: ${params.TEST_CLASS}"
                script {
                    def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')
                    sh """
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

        stage('Allure Report') {
            steps {
                allure([
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline завершено.'
        }
    }
}