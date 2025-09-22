pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Install dependencies') {
      steps {
        sh 'python3 -m venv venv'
        sh '. venv/bin/activate && pip install -r requirements.txt'
      }
    }

    stage('Test') {
      steps {
        sh '. venv/bin/activate && pytest --junitxml=reports/results.xml'
      }
      post {
        always {
          junit 'reports/results.xml'
        }
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}
