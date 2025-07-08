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
            web/expedite/smoke/loadBoard/WES011_DispatchAssignUser.java,
            web/expedite/smoke/loadBoard/WES012_DispatchCalculate.java,
            web/expedite/smoke/loadBoard/WES013_FileOpenAll.java,
            web/expedite/smoke/loadBoard/WES014_LoadEdit.java,
            web/expedite/smoke/loadBoard/WES015_LoadAddSecondTruck.java,
            web/expedite/smoke/loadBoard/WES016_LoadChangeTruck.java,
            web/expedite/smoke/loadBoard/WES017_LoadEnRouteToDelivered.java,
            web/expedite/smoke/loadBoard/WES018_PossibleClaim.java,
            web/expedite/smoke/loadBoard/WES019_LoadMarkAsInvoiced.java,
            web/expedite/smoke/loadBoard/WES020_LoadPaid.java,
            web/expedite/smoke/loadBoard/WES021_LoadBounceToEnRoute.java,
            web/expedite/smoke/loadBoard/WES022_ShowMyLoads.java,
            web/expedite/smoke/loadBoard/WES023_LoadEditDispatch.java,
            web/expedite/smoke/loadBoard/WES024_DispatchDriverAddDell.java,
            web/expedite/smoke/loadBoard/WES025_DispatchWarehouseAddDell.java,
            web/expedite/smoke/loadBoard/WES026_LoadExpensesAddDell.java,
            web/expedite/smoke/loadBoard/WES027_LoadCheckCallAdd.java,
            web/expedite/smoke/loadBoard/WES028_LoadCheckCallEdit.java,
            web/expedite/smoke/loadBoard/WES029_LoadCheckCallDelete.java,
            web/expedite/smoke/loadBoard/WES030_LoadGetConfirmation.java,
            web/expedite/smoke/loadBoard/WES031_LoadGetBol.java,
            web/expedite/smoke/loadBoard/WES032_LoadGetInvoice.java,
            web/expedite/smoke/loadBoard/WES033_LoadRatingChanges.java,
            web/expedite/smoke/loadBoard/WES034_LoadReportAdd.java,
            Group:SmokeBigTruck,
            web/bigTruck/smoke/loadBoard/WBS001_LoadCreate.java,
            web/bigTruck/smoke/loadBoard/WBS002_LoadEdit.java,
            web/bigTruck/smoke/loadBoard/WBS003_LoadAvailableToEnRout.java,
            web/bigTruck/smoke/loadBoard/WBS004_LoadEnRouteToDelivered.java,
            web/bigTruck/smoke/loadBoard/WBS005_LoadDeliveredToInvoiced.java,
            web/bigTruck/smoke/loadBoard/WBS006_LoadInvoicedToPaid.java,
            web/bigTruck/smoke/loadBoard/WBS007_LoadDeliveredToEnRout.java,
            web/bigTruck/smoke/loadBoard/WBS008_LoadDispatchEdit.java,
            web/bigTruck/smoke/loadBoard/WBS009_LoadDriverAddDell.java,
            web/bigTruck/smoke/loadBoard/WBS010_LoadWareHousesAddDell.java,
            web/bigTruck/smoke/loadBoard/WBS011_LoadExpensesAddDell.java,
            web/bigTruck/smoke/loadBoard/WBS012_LoadCheckCallAdd.java,
            web/bigTruck/smoke/loadBoard/WBS013_LoadCheckCallEdit.java,
            web/bigTruck/smoke/loadBoard/WBS014_LoadCheckCallDell.java,
            web/bigTruck/smoke/loadBoard/WBS015_LoadGetConfirmation.java,
            web/bigTruck/smoke/loadBoard/WBS016_LoadGetBol.java,
            web/bigTruck/smoke/loadBoard/WBS017_LoadGetInvoice.java,
            web/bigTruck/smoke/broker/WBS018_BrokerCreate.java,
            web/bigTruck/smoke/broker/WBS019_BrokerEdit.java,
            web/bigTruck/smoke/broker/WBS020_BrokerDnuAdd.java,
            web/bigTruck/smoke/broker/WBS021_BrokerDnuDell.java,
            web/bigTruck/smoke/owner/WBS022_OwnerCreate.java,
            web/bigTruck/smoke/owner/WBS023_OwnerEdit.java,
            web/bigTruck/smoke/owner/WBS024_OwnerDell.java,
            web/bigTruck/smoke/truck/WBS025_TruckCreate.java,
            web/bigTruck/smoke/truck/WBS026_TruckEdit.java,
            web/bigTruck/smoke/truck/WBS027_TruckDell.java,
            web/bigTruck/smoke/trailer/WBS028_TrailerCreateDryVan.java,
            web/bigTruck/smoke/trailer/WBS029_TrailerCreateReefer.java,
            web/bigTruck/smoke/trailer/WBS030_TrailerEdit.java,
            web/bigTruck/smoke/trailer/WBS031_TrailerDell.java,
            web/bigTruck/smoke/shipperReceiver/WBS032_ShipperReceiverCreate.java,
            web/bigTruck/smoke/shipperReceiver/WBS033_ShipperReceiverEdit.java,
            web/bigTruck/smoke/shipperReceiver/WBS034_ShipperReceiverDell.java''',

            visibleItemCount: 30, // Відображати 30 елементів без прокрутки
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
                    def testsToExecute = [] // Остаточний список тестів для запуску

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
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES001_LoadCreateBol.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES002_LoadCreateRateConfirmation.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES005_ValidateRateBrokerOwner.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES006_ValidateDateShipperReceiver.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES007_ValidatePltWtPcs.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES008_DispatchAddTruckByTruck.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES009_DispatchAddTruckByDriver.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES010_DispatchAddTruckByTeamDriver.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES011_DispatchAssignUser.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES012_DispatchCalculate.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES013_FileOpenAll.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES014_LoadEdit.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES015_LoadAddSecondTruck.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES016_LoadChangeTruck.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES017_LoadEnRouteToDelivered.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES018_PossibleClaim.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES019_LoadMarkAsInvoiced.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES020_LoadPaid.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES021_LoadBounceToEnRoute.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES022_ShowMyLoads.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES023_LoadEditDispatch.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES024_DispatchDriverAddDell.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES025_DispatchWarehouseAddDell.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES026_LoadExpensesAddDell.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES027_LoadCheckCallAdd.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES028_LoadCheckCallEdit.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES029_LoadCheckCallDelete.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES030_LoadGetConfirmation.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES031_LoadGetBol.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES032_LoadGetInvoice.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES033_LoadRatingChanges.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES034_LoadReportAdd.java'
                                }
                                if (groupName == 'SmokeBigTruck') {
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java'
                                    testsToExecute << 'web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS001_LoadCreate.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS002_LoadEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS003_LoadAvailableToEnRout.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS004_LoadEnRouteToDelivered.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS005_LoadDeliveredToInvoiced.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS006_LoadInvoicedToPaid.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS007_LoadDeliveredToEnRout.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS008_LoadDispatchEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS009_LoadDriverAddDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS010_LoadWareHousesAddDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS011_LoadExpensesAddDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS012_LoadCheckCallAdd.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS013_LoadCheckCallEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS014_LoadCheckCallDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS015_LoadGetConfirmation.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS016_LoadGetBol.java'
                                    testsToExecute << 'web/bigTruck/smoke/loadBoard/WBS017_LoadGetInvoice.java'
                                    testsToExecute << 'web/bigTruck/smoke/broker/WBS018_BrokerCreate.java'
                                    testsToExecute << 'web/bigTruck/smoke/broker/WBS019_BrokerEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/broker/WBS020_BrokerDnuAdd.java'
                                    testsToExecute << 'web/bigTruck/smoke/broker/WBS021_BrokerDnuDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/owner/WBS022_OwnerCreate.java'
                                    testsToExecute << 'web/bigTruck/smoke/owner/WBS023_OwnerEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/owner/WBS024_OwnerDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/truck/WBS025_TruckCreate.java'
                                    testsToExecute << 'web/bigTruck/smoke/truck/WBS026_TruckEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/truck/WBS027_TruckDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/trailer/WBS028_TrailerCreateDryVan.java'
                                    testsToExecute << 'web/bigTruck/smoke/trailer/WBS029_TrailerCreateReefer.java'
                                    testsToExecute << 'web/bigTruck/smoke/trailer/WBS030_TrailerEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/trailer/WBS031_TrailerDell.java'
                                    testsToExecute << 'web/bigTruck/smoke/shipperReceiver/WBS032_ShipperReceiverCreate.java'
                                    testsToExecute << 'web/bigTruck/smoke/shipperReceiver/WBS033_ShipperReceiverEdit.java'
                                    testsToExecute << 'web/bigTruck/smoke/shipperReceiver/WBS034_ShipperReceiverDell.java'
                                }

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
                                sh """
                                    docker run --rm \\
                                        --network shared_network \\
                                        -v "${hostWorkspace}":/app \\
                                        -w /app \\
                                        -e RUN_ENV=jenkins \\
                                        maven:3.8-openjdk-17 \\
                                        mvn clean test -Dtest=${testClassName} -DfailIfNoTests=false -Dsurefire.rerunFailingTestsCount=1
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