pipeline {
    agent any

    parameters {
        choice(name: 'TEST_SCOPE', choices: ['all', 'folder', 'selected_classes'], description: 'Виберіть область запуску тестів')
        string(name: 'TEST_FOLDER', defaultValue: '', description: 'Вкажіть шлях до папки з тестами (наприклад, web/expedite/ui). Залишіть пустим, якщо обрано "all" або "selected_classes".')
        extendedChoice(
            name: 'TEST_CLASSES_TO_RUN', // Змінив назву параметра, щоб не конфліктувало
            type: 'PT_CHECKBOX',
            multiSelectDelimiter: ',',
            defaultValue: '',
            description: 'Оберіть конкретні класи тестів для запуску (повне ім\'я класу, наприклад, web.expedite.ui.WEU001_LoadBoard). Залишіть пустим, якщо обрано "all" або "folder".',
            // Це значення буде динамічним, або може бути заповнене вручну для початку
            value: '''web/expedite/smoke/loadBoard/WES001_LoadCreateBol.java,
            web/expedite/smoke/loadBoard/WES002_LoadCreateRateConfirmation.java,
            web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java,
            web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java'''
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

        stage('Run Tests') {
            steps {
                script {
                    def hostWorkspace = env.WORKSPACE.replace('/var/jenkins_home', '/data/jenkins/jenkins_home')
                    def testsToRun = []

                    if (params.TEST_SCOPE == 'all') {
                        // Якщо обрано 'all', шукаємо всі класи тестів (або запускаємо mvn test без Dtest)
                        // Для Maven зазвичай достатньо mvn test, щоб запустити всі тести за угодою
                        // Якщо потрібно знайти конкретні файли, можна використовувати find командою
                        // Але простіше покластися на Maven convention
                        echo "Запускаємо всі тести..."
                        testsToRun << "all" // Це буде спеціальний маркер для запуску всіх тестів
                    } else if (params.TEST_SCOPE == 'folder') {
                        if (!params.TEST_FOLDER) {
                            error "Будь ласка, вкажіть TEST_FOLDER, якщо обрано 'folder'."
                        }
                        echo "Запускаємо тести в папці: ${params.TEST_FOLDER}"
                        // Для Maven, ми можемо використовувати TestNG suites або Failsafe plugin для запуску по папках
                        // Але простіше для селективного запуску через -Dtest вказати * шлях до класів
                        // Наприклад, web/expedite/ui/*Test
                        // Або використовувати find для генерації списку файлів, якщо тести - це окремі класи
                        // Припустимо, що TEST_FOLDER відповідає пакету Maven
                        testsToRun << "${params.TEST_FOLDER}.*"
                    } else if (params.TEST_SCOPE == 'selected_classes') {
                        if (!params.TEST_CLASSES_TO_RUN) {
                            error "Будь ласка, оберіть TEST_CLASSES_TO_RUN, якщо обрано 'selected_classes'."
                        }
                        echo "Запускаємо обрані тести: ${params.TEST_CLASSES_TO_RUN}"
                        testsToRun = params.TEST_CLASSES_TO_RUN.split(',')
                    } else {
                        error "Невірний TEST_SCOPE. Оберіть 'all', 'folder' або 'selected_classes'."
                    }

                    def parallelStages = [:]

                    if (testsToRun.contains("all")) {
                        // Спеціальний випадок для запуску всіх тестів без паралелізації для кожного класу
                        // Бо 'mvn test' сам по собі вже запускає всі знайдені тести
                        parallelStages['Run_All_Tests'] = {
                            sh """
                                docker run --rm \\
                                    --network shared_network \\
                                    -v "${hostWorkspace}":/app \\
                                    -w /app \\
                                    -e RUN_ENV=jenkins \\
                                    maven:3.8-openjdk-17 \\
                                    mvn clean test -DfailIfNoTests=false -Dsurefire.rerunFailingTestsCount=1
                            """
                        }
                    } else {
                        // Запуск обраних тестів паралельно
                        testsToRun.each { testClass ->
                            parallelStages["${testClass}"] = {
                                sh """
                                    docker run --rm \\
                                        --network shared_network \\
                                        -v "${hostWorkspace}":/app \\
                                        -w /app \\
                                        -e RUN_ENV=jenkins \\
                                        maven:3.8-openjdk-17 \\
                                        mvn clean test -Dtest=${testClass} -DfailIfNoTests=false -Dsurefire.rerunFailingTestsCount=1
                                """
                            }
                        }
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