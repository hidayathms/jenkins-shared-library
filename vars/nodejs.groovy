
def lintchecks() {
    sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
    // sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Style checks are completed for ${COMPONENT} *******"
}



def call() {
  pipeline { 
    agent any
    tools {
        nodejs 'NPM_NODEJS' 
    }
    environment {
       SONAR_CRED=credentials('SONAR_CRED')
       NEXUS_CRED=credentials('NEXUS_CRED')
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
            //     sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
            //     sh "bash gates.sh admin pass ${SONAR_URL} ${COMPONENT}"                }
            }   
        }
        stage ('Test Cases'){
        parallel {
                stage('Unit Testing') {
                     steps {
                        sh "env"
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
                stage('Prepare Artifact') {     // Runs only when you run this job from tag and from branches it should run
                    when { expression {env.TAG_NAME != null} }
                    steps {
                       sh "echo Prparing Artifacts in progress"
                       sh "npm install"
                       sh "ls -ltr"
                       sh "zip ${COMPONENT}-${TAG_NAME}.zip nodemodules server.js"
                       sh "ls -ltr"
                       sh "echo Artifact build  completed"
                    }
                }
                stage('Uploding the Artifacts') {       // Runs only when you run this job from tag and from branches it should run
                    when { expression {env.TAG_NAME != null} }
                    steps {
                       sh "echo Uploading the Artifacts in progress"
                       sh "curl -f -v -u ${NEXUS_CRED_USR}:${NEXUS_CRED_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.45.41:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                       sh "echo Artifacts upload completed"
                    }
                }
            }
        }
        
    }
  }

}