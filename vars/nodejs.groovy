
def lintchecks() {
    sh "echo ***** Starting Style checks for  *******"
    // sh "npm intstall jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Starting Style checks *******"
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