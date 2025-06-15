pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
//         stage('Docker Build') {
//             steps {
//                 sh 'docker build -t weather .'
//             }
//         }
//         stage('Run Container') {
//             steps {
//                 sh 'docker run -d -p 8085:8085 --name weather weather'
//             }
//         }
    }
}