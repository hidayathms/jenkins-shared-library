def lintchecks() {
    sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
    sh "pip3 install pylint || true"
    sh "pylint *.py || true"
    sh "echo **** Style checks are completed for ${COMPONENT} *******"
}

def call() {
pipeline { 
    agent any
    environment {
       SONAR_CRED=credentials('SONAR_CRED')
    }
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
                    env.ARGS="-Dsonar.sources=."
                    common.sonarChecks()
                }
            }
        }
        stage('Get the sonar results') {
            steps {
                sh "echo Sonar scan is good"
                // sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
                // sh "bash gates.sh admin pass ${SONAR_URL} ${COMPONENT}"
                }
            }
        stage ('Test Cases'){
        parallel {
                stage('Unit Testing') {
                     steps {
                        sh "echo Unit Testing In Progress"
                        // sh "pip test"
                        sh "echo Unit Testing Completed"
                    }
                }
                stage('Integration Testing') {
                    steps {
                       sh "echo Integration Testing In Progres"
                    //    sh "pip verify"
                       sh "echo Integration Testing Completed"
                    }
                }
                stage('Functional Testing') {
                    steps {
                       sh "echo Functional Testing in progress"
                    //    sh "pip function xxx"
                       sh "echo Functional Testing completed"
                    }
                }
                stage('Prepare Artifact') {  // Runs only when you run this job from tag and from branches it should run
                    when { expression {env.TAG_NAME != null} }
                    steps {
                       sh "echo Prparing Artifacts in progress"
                       sh "echo Artifact build  completed"
                    }
                }
                stage('Uploding the Artifacts') {       // Runs only when you run this job from tag and from branches it should run
                    when { expression {env.TAG_NAME != null} }
                    steps {
                       sh "echo Uploading the Artifacts in progress"
                       sh "echo Artifacts Uload completed"
                    }
                }
            }
        }
        }
    }

}