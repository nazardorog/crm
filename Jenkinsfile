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
            web/expedite/smoke/broker/WES035_BrokerCreate.java,
            web/expedite/smoke/broker/WES036_BrokerEdit.java,
            web/expedite/smoke/broker/WES037_BrokerDnuAdd.java,
            web/expedite/smoke/broker/WES038_BrokerDnuDell.java,
            web/expedite/smoke/owner/WES039_OwnerCreatePerson.java,
            web/expedite/smoke/owner/WES040_OwnerCreateCompany.java,
            web/expedite/smoke/owner/WES041_OwnerIsDriverNotChecked.java,
            web/expedite/smoke/owner/WES042_OwnerEdit.java,
            web/expedite/smoke/owner/WES043_OwnerDell.java,
            web/expedite/smoke/truck/WES044_TruckCreate.java,
            web/expedite/smoke/truck/WES045_TruckEdit.java,
            web/expedite/smoke/truck/WES046_TruckDell.java,
            web/expedite/smoke/truck/WES047_TruckReplacementListCreate.java,
            web/expedite/smoke/truck/WES048_TruckReplacementListView.java,
            web/expedite/smoke/truck/WES049_TruckReplacementListEdit.java,
            web/expedite/smoke/truck/WES050_TruckReplacementListDell.java,
            web/expedite/smoke/offers/WES051_FindTruckFilters.java,
            web/expedite/smoke/offers/WES052_OfferCreate.java,
            web/expedite/smoke/offers/WES053_OfferHold.java,
            web/expedite/smoke/driver/WES054_DriverCreate.java,
            web/expedite/smoke/driver/WES055_DriverEdit.java,
            web/expedite/smoke/driver/WES056_DriverDell.java,
            web/expedite/smoke/shipperReceiver/WES057_SRCreate.java,
            web/expedite/smoke/shipperReceiver/WES058_SRCreateLocationDropdown.java,
            web/expedite/smoke/shipperReceiver/WES059_SREdit.java,
            web/expedite/smoke/shipperReceiver/WES060_SRDell.java,
            web/expedite/smoke/loads/WES061_LoadsFilterOperation.java,
            web/expedite/smoke/loads/WES062_LoadsDispatchCheck.java,
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
            visibleItemCount: 30
        )
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
                    def testsToExecute = []
                    def overallStatus = 'SUCCESS'

                    if (params.TEST_SCOPE == 'all') {
                        echo "Запускаємо всі тести..."
                        testsToExecute << "all"
                    } else if (params.TEST_SCOPE == 'folder') {
                        if (!params.TEST_FOLDER) {
                            error "Будь ласка, вкажіть TEST_FOLDER, якщо обрано 'folder'."
                        }
                        echo "Запускаємо тести в папці: ${params.TEST_FOLDER}"
                        testsToExecute << "${params.TEST_FOLDER}.*"
                    } else if (params.TEST_SCOPE == 'selected_classes_or_groups') {
                        if (!params.TEST_CLASSES_TO_RUN) {
                            error "Будь ласка, оберіть TEST_CLASSES_TO_RUN, якщо обрано 'selected_classes_or_groups'."
                        }
                        def selected = params.TEST_CLASSES_TO_RUN.split(',')
                        selected.each { item ->
                            item = item.trim() // Видаляємо пробіли
                            if (item.startsWith('Group:')) {
                                def groupName = item.substring('Group:'.length())
                                echo "Розширюємо групу: ${groupName}"

                                if (groupName == 'SmokeExpedite') {
                                    def expediteTests = [
                                        'web/expedite/smoke/loadBoard/WES001_LoadCreateBol.java',
                                        'web/expedite/smoke/loadBoard/WES002_LoadCreateRateConfirmation.java',
                                        'web/expedite/smoke/loadBoard/WES003_LoadCreatePod.java',
                                        'web/expedite/smoke/loadBoard/WES004_LoadCreateOther.java',
                                        'web/expedite/smoke/loadBoard/WES005_ValidateRateBrokerOwner.java',
                                        'web/expedite/smoke/loadBoard/WES006_ValidateDateShipperReceiver.java',
                                        'web/expedite/smoke/loadBoard/WES007_ValidatePltWtPcs.java',
                                        'web/expedite/smoke/loadBoard/WES008_DispatchAddTruckByTruck.java',
                                        'web/expedite/smoke/loadBoard/WES009_DispatchAddTruckByDriver.java',
                                        'web/expedite/smoke/loadBoard/WES010_DispatchAddTruckByTeamDriver.java',
                                        'web/expedite/smoke/loadBoard/WES011_DispatchAssignUser.java',
                                        'web/expedite/smoke/loadBoard/WES012_DispatchCalculate.java',
                                        'web/expedite/smoke/loadBoard/WES013_FileOpenAll.java',
                                        'web/expedite/smoke/loadBoard/WES014_LoadEdit.java',
                                        'web/expedite/smoke/loadBoard/WES015_LoadAddSecondTruck.java',
                                        'web/expedite/smoke/loadBoard/WES016_LoadChangeTruck.java',
                                        'web/expedite/smoke/loadBoard/WES017_LoadEnRouteToDelivered.java',
                                        'web/expedite/smoke/loadBoard/WES018_PossibleClaim.java',
                                        'web/expedite/smoke/loadBoard/WES019_LoadMarkAsInvoiced.java',
                                        'web/expedite/smoke/loadBoard/WES020_LoadPaid.java',
                                        'web/expedite/smoke/loadBoard/WES021_LoadBounceToEnRoute.java',
                                        'web/expedite/smoke/loadBoard/WES022_ShowMyLoads.java',
                                        'web/expedite/smoke/loadBoard/WES023_LoadEditDispatch.java',
                                        'web/expedite/smoke/loadBoard/WES024_DispatchDriverAddDell.java',
                                        'web/expedite/smoke/loadBoard/WES025_DispatchWarehouseAddDell.java',
                                        'web/expedite/smoke/loadBoard/WES026_LoadExpensesAddDell.java',
                                        'web/expedite/smoke/loadBoard/WES027_LoadCheckCallAdd.java',
                                        'web/expedite/smoke/loadBoard/WES028_LoadCheckCallEdit.java',
                                        'web/expedite/smoke/loadBoard/WES029_LoadCheckCallDelete.java',
                                        'web/expedite/smoke/loadBoard/WES030_LoadGetConfirmation.java',
                                        'web/expedite/smoke/loadBoard/WES031_LoadGetBol.java',
                                        'web/expedite/smoke/loadBoard/WES032_LoadGetInvoice.java',
                                        'web/expedite/smoke/loadBoard/WES033_LoadRatingChanges.java',
                                        'web/expedite/smoke/loadBoard/WES034_LoadReportAdd.java',
                                        'web/expedite/smoke/broker/WES035_BrokerCreate.java',
                                        'web/expedite/smoke/broker/WES036_BrokerEdit.java',
                                        'web/expedite/smoke/broker/WES037_BrokerDnuAdd.java',
                                        'web/expedite/smoke/broker/WES038_BrokerDnuDell.java',
                                        'web/expedite/smoke/owner/WES039_OwnerCreatePerson.java',
                                        'web/expedite/smoke/owner/WES040_OwnerCreateCompany.java',
                                        'web/expedite/smoke/owner/WES041_OwnerIsDriverNotChecked.java',
                                        'web/expedite/smoke/owner/WES042_OwnerEdit.java',
                                        'web/expedite/smoke/owner/WES043_OwnerDell.java',
                                        'web/expedite/smoke/truck/WES044_TruckCreate.java',
                                        'web/expedite/smoke/truck/WES045_TruckEdit.java',
                                        'web/expedite/smoke/truck/WES046_TruckDell.java',
                                        'web/expedite/smoke/truck/WES047_TruckReplacementListCreate.java',
                                        'web/expedite/smoke/truck/WES048_TruckReplacementListView.java',
                                        'web/expedite/smoke/truck/WES049_TruckReplacementListEdit.java',
                                        'web/expedite/smoke/truck/WES050_TruckReplacementListDell.java',
                                        'web/expedite/smoke/offers/WES051_FindTruckFilters.java',
                                        'web/expedite/smoke/offers/WES052_OfferCreate.java',
                                        'web/expedite/smoke/offers/WES053_OfferHold.java',
                                        'web/expedite/smoke/driver/WES054_DriverCreate.java',
                                        'web/expedite/smoke/driver/WES055_DriverEdit.java',
                                        'web/expedite/smoke/driver/WES056_DriverDell.java',
                                        'web/expedite/smoke/shipperReceiver/WES057_SRCreate.java',
                                        'web/expedite/smoke/shipperReceiver/WES058_SRCreateLocationDropdown.java',
                                        'web/expedite/smoke/shipperReceiver/WES059_SREdit.java',
                                        'web/expedite/smoke/shipperReceiver/WES060_SRDell.java',
                                        'web/expedite/smoke/loads/WES061_LoadsFilterOperation.java',
                                        'web/expedite/smoke/loads/WES062_LoadsDispatchCheck.java'
                                    ]
                                    testsToExecute.addAll(expediteTests)
                                }

                                if (groupName == 'SmokeBigTruck') {
                                    def bigTruckTests = [
                                        'web/bigTruck/smoke/loadBoard/WBS001_LoadCreate.java',
                                        'web/bigTruck/smoke/loadBoard/WBS002_LoadEdit.java',
                                        'web/bigTruck/smoke/loadBoard/WBS003_LoadAvailableToEnRout.java',
                                        'web/bigTruck/smoke/loadBoard/WBS004_LoadEnRouteToDelivered.java',
                                        'web/bigTruck/smoke/loadBoard/WBS005_LoadDeliveredToInvoiced.java',
                                        'web/bigTruck/smoke/loadBoard/WBS006_LoadInvoicedToPaid.java',
                                        'web/bigTruck/smoke/loadBoard/WBS007_LoadDeliveredToEnRout.java',
                                        'web/bigTruck/smoke/loadBoard/WBS008_LoadDispatchEdit.java',
                                        'web/bigTruck/smoke/loadBoard/WBS009_LoadDriverAddDell.java',
                                        'web/bigTruck/smoke/loadBoard/WBS010_LoadWareHousesAddDell.java',
                                        'web/bigTruck/smoke/loadBoard/WBS011_LoadExpensesAddDell.java',
                                        'web/bigTruck/smoke/loadBoard/WBS012_LoadCheckCallAdd.java',
                                        'web/bigTruck/smoke/loadBoard/WBS013_LoadCheckCallEdit.java',
                                        'web/bigTruck/smoke/loadBoard/WBS014_LoadCheckCallDell.java',
                                        'web/bigTruck/smoke/loadBoard/WBS015_LoadGetConfirmation.java',
                                        'web/bigTruck/smoke/loadBoard/WBS016_LoadGetBol.java',
                                        'web/bigTruck/smoke/loadBoard/WBS017_LoadGetInvoice.java',
                                        'web/bigTruck/smoke/broker/WBS018_BrokerCreate.java',
                                        'web/bigTruck/smoke/broker/WBS019_BrokerEdit.java',
                                        'web/bigTruck/smoke/broker/WBS020_BrokerDnuAdd.java',
                                        'web/bigTruck/smoke/broker/WBS021_BrokerDnuDell.java',
                                        'web/bigTruck/smoke/owner/WBS022_OwnerCreate.java',
                                        'web/bigTruck/smoke/owner/WBS023_OwnerEdit.java',
                                        'web/bigTruck/smoke/owner/WBS024_OwnerDell.java',
                                        'web/bigTruck/smoke/truck/WBS025_TruckCreate.java',
                                        'web/bigTruck/smoke/truck/WBS026_TruckEdit.java',
                                        'web/bigTruck/smoke/truck/WBS027_TruckDell.java',
                                        'web/bigTruck/smoke/trailer/WBS028_TrailerCreateDryVan.java',
                                        'web/bigTruck/smoke/trailer/WBS029_TrailerCreateReefer.java',
                                        'web/bigTruck/smoke/trailer/WBS030_TrailerEdit.java',
                                        'web/bigTruck/smoke/trailer/WBS031_TrailerDell.java',
                                        'web/bigTruck/smoke/shipperReceiver/WBS032_ShipperReceiverCreate.java',
                                        'web/bigTruck/smoke/shipperReceiver/WBS033_ShipperReceiverEdit.java',
                                        'web/bigTruck/smoke/shipperReceiver/WBS034_ShipperReceiverDell.java'
                                    ]
                                    testsToExecute.addAll(bigTruckTests)
                                }
                            } else {
                                testsToExecute << item
                            }
                        }

                        testsToExecute = testsToExecute.unique()
                        echo "Остаточний список тестів для виконання: ${testsToExecute}"
                    } else {
                        error "Невірний TEST_SCOPE. Оберіть 'all', 'folder' або 'selected_classes_or_groups'."
                    }

                    def parallelStages = [:]
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
                                    echo "Тест ${testClassName} завершився з помилкою: ${e.message}"
                                    overallStatus = 'FAILURE'
                                }
                            }
                        }
                    }

                    // ВАЖЛИВО: Додаємо контроль паралельності
                    if (testsToExecute.size() > 1 && !testsToExecute.contains("all")) {
                        def parallelOptions = [
                            failFast: false,
                            maxConcurrency: maxConcurrentBuilds
                        ]
                        parallel(parallelStages + parallelOptions)
                    } else {
                        parallel parallelStages
                    }

                    // Встановлюємо статус білду відповідно до результатів
                    if (overallStatus == 'FAILURE') {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Allure results merge') {
            steps {
                sh """
                  mkdir -p target/allure-results
                  find target/allure-results -name "*.json" -o -name "*.txt" -o -name "*.properties" | head -100 | xargs -I {} cp {} target/allure-results/ 2>/dev/null || true
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
            echo "Тести завершено з статусом: ${currentBuild.result ?: 'SUCCESS'}"
        }
    }
}