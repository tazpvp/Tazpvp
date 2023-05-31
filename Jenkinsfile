pipeline {
  agent any
  tools {
    maven 'maven3.9.2' 
  }
  stages {
    stage ('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
  }
}
