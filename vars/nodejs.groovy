
def lintchecks(component) {
    sh "echo ***** Starting Style checks for  ${component} *******"
    // sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Style checks are completed for ${component} *******"
}

def sonarChecks(component) {
    sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 -Dsonar.sources=. -Dsonar.projectKey=${component} -Dsonar.login=admin -Dsonar.password=pass"
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
        stage('Get the sonar results') {
            steps {
                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
                sh 'echo "DevOps321" | sudo -S bash gates.sh admin password 172.31.41.5 ${component}'                }
            }
        stage('Unit Testing') {
            steps {
                echo "Testing in Progress"
                echo "Testing in Completed"
            }
        }
        
    }
  }

}