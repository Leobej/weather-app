tools {
    jdk 'jdk23'
}

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t weather-backend .'
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
