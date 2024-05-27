
def lintchecks(component) {
    sh "echo ***** Starting Style checks for  ${component} *******"
    // sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Style checks are completed for ${component} *******"
}

def sonarChecks(){
    sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 -Dsonar.soruces=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=pass"
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