pipeline {
    agent any

    parameters {
        choice(name: 'TEST_SCOPE', choices: ['all', 'folder', 'selected_classes_or_groups'], description: 'Виберіть область запуску тестів')
        string(name: 'TEST_FOLDER', defaultValue: '', description: 'Вкажіть шлях до папки з тестами (наприклад, web/expedite/ui). Залишіть пустим, якщо обрано "all" або "selected_classes_or_groups".')
        extendedChoice(
            name: 'TEST_CLASSES_TO_RUN',
            type: 'PT_CHECKBOX',
            multiSelectDelimiter: ',',
            defaultValue: '',
            description: 'Оберіть конкретні класи тестів для запуску (повне ім\'я класу, наприклад, web.expedite.ui.WEU001_LoadBoard). Залишіть пустим, якщо обрано "all" або "folder".',
            // Це значення буде динамічним, або може бути заповнене вручну для початку
            value: '''Group:SmokeExpedite,
            web/expedite/smoke/loadBoard/WES001_LoadCreateBol.java,
            web/expedite/smoke/loadBoard/WES002_LoadCreateRateConfirmation.java,
            web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java,
            web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java,
            web/expedite/smoke/loadBoard/WES005_ValidateRateBrokerOwner.java,
            web/expedite/smoke/loadBoard/WES006_ValidateDateShipperReceiver.java,
            web/expedite/smoke/loadBoard/WES007_ValidatePltWtPcs.java,
            web/expedite/smoke/loadBoard/WES008_DispatchAddTruckByTruck.java,
            web/expedite/smoke/loadBoard/WES009_DispatchAddTruckByDriver.java,
            web/expedite/smoke/loadBoard/WES010_DispatchAddTruckByTeamDriver.java,
            Group:SmokeBigTruck,
            web/bigTruck/smoke/loadBoard/WBS001_LoadCreate.java''',

            visibleItemCount: 30, // Відображати 30 елементів без прокрутки
        )
            // >>> кількість потоків <<<
            string(name: 'PARALLEL_THREADS', defaultValue: '4', description: 'Кількість одночасних потоків для запуску тестів. Введіть число.')
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
                    def testsToExecute = [] // Остаточний список тестів для запуску
                    def overallStatus = 'SUCCESS' // Змінна для відстеження загального статусу

                    if (params.TEST_SCOPE == 'all') {
                        echo "Запускаємо всі тести..."
                        // Для Maven запускаємо mvn test, який знайде всі тести
                        testsToExecute << "all"
                    } else if (params.TEST_SCOPE == 'folder') {
                        if (!params.TEST_FOLDER) {
                            error "Будь ласка, вкажіть TEST_FOLDER, якщо обрано 'folder'."
                        }
                        echo "Запускаємо тести в папці: ${params.TEST_FOLDER}"
                        // Для Maven: ${params.TEST_FOLDER}.* запустить всі класи в цьому пакеті
                        testsToExecute << "${params.TEST_FOLDER}.*"
                    } else if (params.TEST_SCOPE == 'selected_classes_or_groups') {
                        if (!params.TEST_CLASSES_TO_RUN) {
                            error "Будь ласка, оберіть TEST_CLASSES_TO_RUN, якщо обрано 'selected_classes_or_groups'."
                        }
                        def selected = params.TEST_CLASSES_TO_RUN.split(',')
                        selected.each { item ->
                            def actualValueToProcess // Змінна для зберігання фактичного значення тесту

                            if (item.startsWith('Group:')) {
                                def groupName = item.substring('Group:'.length())
                                echo "Розширюємо групу: ${groupName}"
                                // Тут потрібно прописати логіку розширення групи
                                if (groupName == 'SmokeExpedite') {
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES001_LoadCreateBol.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES002_LoadCreateRateConfirmation.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES005_ValidateRateBrokerOwner.java'

                                }
                                if (groupName == 'SmokeBigTruck') {
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS001_LoadCreate.java'

                                // Додавай інші групи тут за допомогою else if
                                // else if (groupName == 'AnotherGroup') { ... }
                            } else {
                                testsToExecute << item // Додаємо окремий тест
                            }
                        }
                        // Видаляємо дублікати на випадок, якщо тест обраний і як частина групи, і окремо
                        testsToExecute = testsToExecute.unique()
                        echo "Остаточний список тестів для виконання: ${testsToExecute}"
                    } else {
                        error "Невірний TEST_SCOPE. Оберіть 'all', 'folder' або 'selected_classes_or_groups'."
                    }

                    def parallelStages = [:]

                    // Перетворюємо PARALLEL_THREADS на ціле число
                    def maxConcurrentBuilds = params.PARALLEL_THREADS as Integer

                    if (testsToExecute.contains("all")) {
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
                        testsToExecute.each { testClassFile ->
                            // Перетворюємо шлях до файлу (web/expedite/...) на повне ім'я класу (web.expedite. ...)
                            def testClassName = testClassFile.replace('/', '.').replace('.java', '')
                            parallelStages["${testClassName}"] = {
                                try {
                                    sh """
                                        docker run --rm \\
                                            --network shared_network \\
                                            -v "${hostWorkspace}":/app \\
                                            -w /app \\
                                            -e RUN_ENV=jenkins \\
                                            maven:3.8-openjdk-17 \\
                                            mvn clean test -Dtest=${testClassName} -DfailIfNoTests=false -Dsurefire.rerunFailingTestsCount=1
                                    """
                                } catch (Exception e) {
                                    // Якщо тест впав, просто виводимо повідомлення, але не кидаємо помилку,
                                    // щоб дозволити іншим паралельним стейджам завершитися.
                                    echo "Тест ${testClassName} завершився з помилкою: ${e.message}"
                                    overallStatus = 'FAILURE' // Встановлюємо загальний статус на FAILURE
                                }
                            }
                        }
                    }
                    parallel parallelStages

//                     def parallelOptions = [
//                         failFast: false,
//                         maxConcurrency: maxConcurrentBuilds
//                     ]
//                     parallel(parallelStages, parallelOptions)
//
//                     if (overallStatus == 'FAILURE') {
//                         currentBuild.result = 'UNSTABLE'
//                     }
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