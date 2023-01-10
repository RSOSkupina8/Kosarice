# RSO: Image metadata microservice

## Prerequisites

```bash
docker run -d --name pg-kosarice -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=kosarice -p 5433:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar kosarice-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/kosarice

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/kosarice
~~~~
## Docker commands
```bash 
docker build -t novaslika .   
docker images
docker run novaslika    
docker tag novaslika gregorzadnik/kosarice   
docker push gregorzadnik/kosarice
docker ps
```

```bash
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-kosarice -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=kosarice -p 5433:5432 --network rso postgres:13
docker inspect pg-kosarice
docker run -p 8081:8081 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-kosarice:5433/kosarice gregorzadnik/kosarice
```

## Consul
```bash
consul agent -dev
```
Available at: localhost:8500

Key: environments/dev/services/kosarice-service/1.0.0/config/rest-properties/maintenance-mode

Value: true or false

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f kosarice-deployment.yaml 
kubectl apply -f kosarice-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs kosarice-deployment-6f59c5d96c-rjz46
kubectl delete pod kosarice-deployment-6f59c5d96c-rjz46
```
Secrets: https://kubernetes.io/docs/concepts/configuration/secret/

