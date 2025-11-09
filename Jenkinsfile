pipeline {
    agent any

    tools {
        maven 'Maven_3.9'   // same name you configured in Jenkins
        jdk 'jdk11'         // or jdk17 / jdk21 if that's your setup
    }

    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Checking out code from GitHub...'
                git branch: 'main', url: 'https://github.com/Harpreet309/testNG_Framework.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo '🚀 Running TestNG Suite...'
                sh 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo '📊 Publishing HTML Extent Report...'
                publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/ExtentReports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo '✅ Build completed successfully!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}
