def sonarChecks() {
    stage ('Sonar Checks') {
    echo "Sonar checks in progress"
    // sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
    echo "Sonar checks in Completed"
    }
}

def lintchecks() {
    stage ('Lint checks') {
        if(env.APP_TYPE == "maven") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            sh "mvn checkstyle:check || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"  
        }
        else if (env.APP_TYPE =="nodejs") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "npm i jslint"
            sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
        else if (env.APP_TYPE == "python") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            sh "pip3 install pylint || true"
            sh "pylint *.py || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
        else {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "npm i jslint"
            // sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
    }
  
}


