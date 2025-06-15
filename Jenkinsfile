pipeline {
    agent any

    tools {
        jdk 'jdk23'
    }

    environment {
        PATH = "${tool 'jdk23'}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }

    stage('Docker Build') {
        steps {
            script {
                def start = System.currentTimeMillis()
                bat 'docker build -t weather-backend .'
                echo "Docker Build took: " + ((System.currentTimeMillis() - start) / 1000) + " seconds"
            }
        }
    }

        stage('Run Container') {
            steps {
                bat 'docker stop weather || exit 0'
                bat 'docker rm weather || exit 0'
                bat 'docker run -d -p 8085:8085 --name weather weather-backend'
            }
        }
    }
}
