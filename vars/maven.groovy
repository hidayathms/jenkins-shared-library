
def lintchecks(component) {
    sh "echo ***** Starting Style checks for  ${component} *******"
    sh "mvn checkstyle:check || true"
    sh "echo **** Style checks are completed for ${component} *******"
}

def call(component) {
pipeline { 
    agent any
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    lintchecks(component)
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