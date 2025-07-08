pipeline {
    agent any

//     parameters {
//         extendedChoice(
//             name: 'TEST_CLASS',
//             type: 'PT_CHECKBOX',
//             multiSelectDelimiter: ',',
//             defaultValue: '',
//             description: 'Оберіть тести для запуску',
//             value: 'web.expedite.ui.WEU001_LoadBoard,web.expedite.ui.WEU002_Broker,web.expedite.ui.WEU003_Truck,web.expedite.ui.WEU004_ExpediteFleet,web.expedite.smoke.broker.WES035_BrokerCreate,web.expedite.smoke.broker.WES037_BrokerDnuAdd'
//         )
//     }

    parameters {
        // Groovy script to зчитати список тестів
        [$class: 'CascadeChoiceParameter',
            choiceType: 'PT_CHECKBOX',
            filterLength: 1,
            filterable: true,
            name: 'TEST_CLASS',
            description: 'Оберіть тести або папки',
            referencedParameters: '',
            script: [
                $class: 'GroovyScript',
                sandbox: false,
                script: '''
                    def output = []
                    def proc = "bash scripts/list_tests.sh".execute()
                    proc.in.eachLine { output << it }
                    return output
                '''
            ]
        ]
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
                echo "Запускаємо тести: ${params.TEST_CLASS}"
                script {
                    def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')
                    sh 'rm -rf target/allure-results || true'

                    def tests = params.TEST_CLASS.tokenize(',')

                    def parallelStages = tests.collectEntries { testClass ->
                        def testArg = testClass.contains('*') ? "-Dtest=${testClass}" : "-Dtest=${testClass}"

                        ["${testClass}": {
                            sh """
                                echo "==> Запускаємо тест: ${testClass}"
                                docker run --rm \
                                    --network shared_network \
                                    -v "${hostWorkspace}":/app \
                                    -w /app \
                                    -e RUN_ENV=jenkins \
                                    maven:3.8-openjdk-17 \
                                    mvn test ${testArg} -DfailIfNoTests=false
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