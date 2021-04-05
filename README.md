# jaredmalkin-project1

## Description
A simple banking Java application, utilizing Spring framework, connected to an AWS RDS that contains a User and Account table. HTTP requests can be made to manage Users and Accounts. Application logs are aggregated with FluentD agents exporting to Loki and displayed in a Grafana Dashboard.

## Technologies Used
* Java
* Maven
* Spring Boot
* Spring Data
* PostgreSQL
* AWS
* Kubernetes
* Log4J
* FluentD
* Loki
* Grafana

## Features
* Login/Logout
* Create user
* Get all users
* Delete user
* Create account
* Get all acounts
* Delete account
* Deposit into account
* Withdraw from account
* Transfer between accounts

## How to Use
To login: Send a post request to /data/users/login with username and password in json body\
To logout: Send a get request to /data/users/{username}/logout\
To create user: Send a post request to /data/users with username, password in json body\
To get all users: Send a get request to /data/users\
To delete user: Send a delete request to /data/users/{id}\
  This can only be done when logged on as the user you want to delete\
To create account: Send a post request to /data/accounts with ownerId in json body\
To get all accounts: Send a get request to /data/accounts\
To delete account: Send a delete request to /data/accounts/{accountId}\
  This can only be done when logged on as the account owner of the account you want to delete\
To deposit into account: Send a get request to /data/accounts/{accountId}/deposit/{amount}\
  This can only be done when logged on as the account owner of the account you want to deposit to\
To withdraw from account: Send a get request to /data/accounts/{accountId}/withdraw/{amount}\
  This can only be done when logged on as the account owner of the account you want to withdraw from\
To transfer between accounts: Send a get request to /data/accounts/{sourceAccountId}/transfer/{destinationAccountId}/{amount}\
  This can only be done when logged on as the account owner of the source account the money is coming from

## To Do
Restrict users from accessing the money in other users' accounts.
