
def lintchecks() {
    sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
    sh "mvn checkstyle:check || true"
    sh "echo **** Style checks are completed for ${COMPONENT} *******"
}

def call() {
pipeline { 
    agent any
    environment {
       SONAR_CRED=credentials('SONAR_CRED')
    }
    tools {
        maven 'maven-396' 
    }
    stages {
        stage('Lint Checks') {
            steps {
                script {
                    lintchecks()
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
                    env.ARGS="-Dsonar.java.binaries=./target"
                    common.sonarChecks()
                }
            }
        }
        stage('Get the sonar results') {
            steps {
                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
                sh "bash gates.sh admin pass ${SONAR_URL} ${COMPONENT}"
                }
        }
        stage ('Test Cases'){
        parallel {
                stage('Unit Testing') {
                     steps {
                        sh "echo Unit Testing In Progress"
                        // sh "npm test"
                        sh "echo Unit Testing Completed"
                    }
                }
                stage('Integration Testing') {
                    steps {
                       sh "echo Integration Testing In Progres"
                    //    sh "npm verify"
                       sh "echo Integration Testing Completed"
                    }
                }
                stage('Functional Testing') {
                    steps {
                       sh "echo Functional Testing in progress"
                    //    sh "npm function xxx"
                       sh "echo Functional Testing completed"
                    }
                }
            }
        }
    }
}

}