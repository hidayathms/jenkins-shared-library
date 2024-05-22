
def lintchecks() {
    sh "echo ***** Starting Style checks *******"
    sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Starting Style checks *******"
}