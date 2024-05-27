
def lintchecks(component) {
    sh "echo ***** Starting Style checks for  ${component} *******"
    // sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Style checks are completed for ${component} *******"
}

def sonarChecks(component) {
    sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 -Dsonar.soruces=. -Dsonar.projectKey=${component} -Dsonar.login=admin -Dsonar.password=pass"
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
                script {
                    sonarChecks(component)
                }
            }
        }
        stage('Static Code Analysis') {
            steps {
                echo "Testing in Progress"
                echo "Testing in Completed"
                }
        }
        
    }
}

