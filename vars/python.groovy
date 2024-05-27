def lintchecks() {
    sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
    sh "pip3 install pylint || true"
    sh "pylint *.py || true"
    sh "echo **** Style checks are completed for ${COMPONENT} *******"
}

def sonarChecks() {
    sh "sonar-scanner -Dsonar.host.url=http://54.224.63.79:9000 -Dsonar.java.binaries=./target -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=pass"
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
                script {
                    sonarChecks()
                }
            }
        }
        stage('Get the sonar results') {
            steps {
                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
                sh "bash gates.sh admin pass 172.31.41.5 ${COMPONENT}"
                }
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