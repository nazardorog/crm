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
                script {
                    def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')
                    sh """
                        docker run --rm \
                            --network shared_network \
                            -v "${hostWorkspace}":/app \
                            -w /app \
                            -e RUN_ENV=jenkins \
                            maven:3.8-openjdk-17 \
                            mvn test -Dtest=${params.TEST_CLASS} -DfailIfNoTests=false
                    """
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