def sonarChecks() {
    sh " sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW} "
    }