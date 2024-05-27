def lintchecks() {
    sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
    sh "pip3 install pylint || true"
    sh "pylint *.py || true"
    sh "echo **** Style checks are completed for ${COMPONENT} *******"
}

def call() {
pipeline { 
    agent any
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    lintchecks()
                }
            }
        }
        stage('Static Code Analysis') {
            steps {
                sh "echo Starting Static code analysis"
            }
        }
    }
}

}