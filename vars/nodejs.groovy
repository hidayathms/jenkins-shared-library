
def lintchecks('component') {
    sh "echo ***** Starting Style checks for ${component} *******"
    // sh "npm intstall jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Starting Style checks *******"
}

def call('component') {
pipeline { 
    agent any
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    nodejs.lintchecks()
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