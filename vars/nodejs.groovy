
def lintchecks() {
    sh "echo ***** Starting Style checks *******"
    sh "npm install -g jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Starting Style checks *******"
}