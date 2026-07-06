pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timeout(time: 60, unit: 'MINUTES')
    }

    parameters {
        choice(name: 'ENV', choices: ['qa', 'sit', 'uat'], description: 'Environment config to use')
        choice(name: 'SUITE', choices: ['smoke-suite', 'sit-suite', 'regression-suite', 'contract-suite', 'security-suite', 'parallel-suite', 'full-suite'], description: 'Maven profile / test suite')
        string(name: 'CUCUMBER_TAGS', defaultValue: '', description: 'Optional tag override, example: @smoke and @users')
        string(name: 'THREAD_COUNT', defaultValue: '4', description: 'Parallel thread count')
        string(name: 'BASE_URI_OVERRIDE', defaultValue: '', description: 'Optional API base URL override')
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Tool Versions') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Clean Workspace Reports') {
            steps {
                sh 'rm -rf target || true'
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    def tagArg = params.CUCUMBER_TAGS?.trim() ? "-Dcucumber.filter.tags=\"${params.CUCUMBER_TAGS}\"" : ''
                    def baseUriArg = params.BASE_URI_OVERRIDE?.trim() ? "-Dbase.uri=${params.BASE_URI_OVERRIDE}" : ''
                    sh """
                        mvn clean test \
                        -P${params.SUITE} \
                        -Denv=${params.ENV} \
                        -DthreadCount=${params.THREAD_COUNT} \
                        ${tagArg} \
                        ${baseUriArg}
                    """
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/cucumber-reports/cucumber.xml,target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/cucumber-reports/**,target/surefire-reports/**', allowEmptyArchive: true
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber.html',
                reportName: 'Cucumber API Report'
            ])
        }
        success {
            echo 'API automation pipeline passed.'
        }
        failure {
            echo 'API automation pipeline failed. Check Cucumber report, console logs, and application logs.'
        }
    }
}
