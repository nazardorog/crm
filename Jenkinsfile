pipeline {
    agent any

    parameters {
        string(name: 'TEST_CLASS', defaultValue: 'web.expedite.ui.WEU003_Truck', description: 'Повне ім’я класу тесту')
    }

    stages {
        stage('Debug1') {
            steps {
                sh '''
                    echo "=== Поточна директорія Jenkins ==="
                    pwd
                    echo "=== Вміст поточної директорії ==="
                    ls -la
                    echo "=== Пошук pom.xml ==="
                    find . -name "pom.xml" -type f
                    echo "=== Структура проекту ==="
                    tree -L 3 || ls -R | head -50
                '''
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'your-git-credentials-id',
                    url: 'https://github.com/nazardorog/crm.git/'
            }
        }

        stage('Debug2') {
            steps {
                sh '''
                    echo "=== Поточна директорія Jenkins ==="
                    pwd
                    echo "=== Вміст поточної директорії ==="
                    ls -la
                    echo "=== TEST_CLASS parameter ==="
                    echo "${TEST_CLASS}"
                '''
            }
        }

        stage('Run Test in Docker') {
            steps {
                echo "Запускаємо тест: ${params.TEST_CLASS}"
                def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')
                sh 'docker run --rm -v ${WORKSPACE}:/app -w /app maven:3.8-openjdk-17 mvn test -Dtest=${TEST_CLASS} -DfailIfNoTests=false'
            }
        }
    }

    post {
        always {
            echo 'Pipeline завершено.'
        }
    }
}