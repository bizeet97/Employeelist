pipeline{
    agent any
    stages{        
         
        stage ('Build and Test')
        {
            steps{
                    sh "mvn clean install"
            }
        }
         
        stage('Code Quality')
        {
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
             withCredentials([usernamePassword(credentialsId: 'sudipa_nexus', passwordVariable: 'pwd_2', usernameVariable: 'usr')]) {
                 sh label: '', script: 'curl -u $usr:$pwd_2 --upload-file target/my-car-shop-${BUILD_NUMBER}.war http://3.17.164.37:8081/nexus/content/repositories/devopstraining/Team1/my-car-shop-${BUILD_NUMBER}.war'
               }
          }
        }
        
      stage ('Deploying War File'){
             steps{
                  withCredentials([usernamePassword(credentialsId: 'devops-tomcat', passwordVariable: 'pass', usernameVariable: 'userId')]) {
        
                     sh 'curl -u  $userId:$pass  http://ec2-18-224-182-74.us-east-2.compute.amazonaws.com:8080/manager/text/undeploy?path=/superemp'
                     sh  'curl -u  $userId:$pass --upload-file target/my-car-shop-${BUILD_NUMBER}.war http://ec2-18-224-182-74.us-east-2.compute.amazonaws.com:8080/manager/text/deploy?config=file:/var/lib/tomcat8/my-car-shop-${BUILD_NUMBER}.war\\&path=/superemp'
                 }
             }
    
         }
    }
}

