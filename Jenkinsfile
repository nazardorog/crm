pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stage('Run Tests Sequentially') {
            steps {
                script {
                    def tests = [
                        'web.expedite.smoke.broker.WES035_BrokerCreate',
                        'web.expedite.smoke.broker.WES037_BrokerDnuAdd'
                    ]

                    def startTime = System.currentTimeMillis()

                    for (testClass in tests) {
                        echo "Запускаємо тест: ${testClass}"
                        sh """
                            docker run --rm \
                                --network shared_network \
                                -v "${env.WORKSPACE}":/app \
                                -w /app \
                                -e RUN_ENV=jenkins \
                                maven:3.8-openjdk-17 \
                                mvn test -Dtest=${testClass} -DfailIfNoTests=false
                        """
                    }

                    def duration = (System.currentTimeMillis() - startTime) / 1000
                    echo "Всього часу на послідовне виконання: ${duration} секунд"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline завершено.'
        }
    }
}