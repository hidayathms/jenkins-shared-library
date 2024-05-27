
def lintchecks(component) {
    sh "echo ***** Starting Style checks for  ${component} *******"
    sh "mvn checkstyle:check || true"
    sh "echo **** Style checks are completed for ${component} *******"
}

def sonarChecks(component) {
    sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 -Dsonar.sources=. -Dsonar.projectKey=${component} -Dsonar.login=admin -Dsonar.password=pass"
    }

def call(component) {
pipeline { 
    agent any
    tools {
        maven 'maven-396' 
    }
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    lintchecks(component)
                }
            }
        }
        stage('Compiling Java code') {
            steps {
                sh "mvn clean compile"
                sh "ls -ltr target/"
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
                sh "bash gates.sh admin pass 172.31.41.5 ${component}"
                }
            }
    }
}

}