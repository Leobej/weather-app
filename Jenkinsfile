pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }

        // Uncomment when Docker is ready
        // stage('Docker Build') {
        //     steps {
        //         bat 'docker build -t weather .'
        //     }
        // }

        // stage('Run Container') {
        //     steps {
        //         bat 'docker run -d -p 8085:8085 --name weather weather'
        //     }
        // }
    }
}
