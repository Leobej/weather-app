pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    stages {
        stage('Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t weather .'
            }
        }

        stage('Run Container') {
            steps {
                // Stop old container if running (optional)
                bat 'docker rm -f weather || exit 0'

                // Run new container on port 8085
                bat 'docker run -d -p 8085:8085 --name weather weather'
            }
        }
    }
}