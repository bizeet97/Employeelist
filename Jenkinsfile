pipeline{
    agent any
    stages{
        
         stage('Checkout'){
            steps{
                withCredentials([string(credentialsId: 'Git_1', variable: 'git')]) {
                echo "My password is '${git}'!"
                checkout([$class: 'GitSCM',
                branches: [[name: 'master']],
                extensions: [[$class: 'WipeWorkspace']],
                userRemoteConfigs: [[url: "${git}"]]
                ])
                       }
            }
            }
        stage ('Build and Test'){
            steps{
                    sh "mvn clean install"
            }
        }
         
        stage('Code Quality') {
             environment {
                scannerHome=tool 'sonar scanner'
            }
             steps{
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'Hemant_Sonar_Cred', usernameVariable: 'USER', passwordVariable: 'PASS']]){
                     sh "mvn $USER:$PASS -Dsonar.host.url=http://3.17.164.37:9000"
                 }
             }
         }
         
       
        stage ('Uploading Artifact to Nexus'){
            steps{
             withCredentials([usernamePassword(credentialsId: 'Hemant_Nexus_Cred', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                sh label: '', script: 'curl -u $USER:$PASS --upload-file target/my-car-shop-${BUILD_NUMBER}.war http://3.17.164.37:8081/nexus/content/repositories/devopstraining/Team1/my-car-shop-${BUILD_NUMBER}.war'
               }
          }
        }
        
      stage ('Deploying War File'){
             steps{
                  withCredentials([usernamePassword(credentialsId: 'devops-tomcat', passwordVariable: 'pass', usernameVariable: 'userId')]) {
        
                     sh 'curl -u  $userId:$pass  http://ec2-18-224-182-74.us-east-2.compute.amazonaws.com:8080/manager/text/undeploy?path=/superemp'
                     sh  'curl -u  $userId:$pass --upload-file target/my-car-shop-${BUILD_NUMBER}.war  http://ec2-18-224-182-74.us-east-2.compute.amazonaws.com:8080/manager/text/deploy?config=file:/var/lib/tomcat8/my-car-shop-${BUILD_NUMBER}.war\\&path=/superemp'
                 }
             }
    
         }
    }
}

