# uber-codechallenge-email

# Problem description
Create a service that accepts the necessary information and sends emails. It should provide an abstraction between two different email service providers. If one of the services goes down, your services can quickly failover to a different provider without affecting your customers.

# Use Case
Name: Send email.
Description: As a client I would like to send an email with basic fields.
Preconditions: A valid email.
Standard flow: 
1. Customer fills the email as a JSON.
	2. Customer send a POST request using the RESTful API.
	3. Controller validates the input and transforms JSON to POJO.
	4. Controller uses the service façade to send the email.
	5. Façade uses a service provider to send the email. If it fails then it will try with the next service provider in the list.
	6. App returns a response message to the customer. It could be an error or a message id.

# Assumptions
1. Accepts the necessary information, for this app will be: from, to (single), subject and body.
2. No attachments supported on this release.
3. Use of two emails service providers from the list (https://goo.gl/XSgWp1) in specific we will use SendGrid and Amazon SES.
4. Two email address are whitelisted for testing.
5. It’s a client-server app.
6. It’s a synchronous system. Client thread is blocked until it gets a response.
7. No storage for upcoming emails.
8. No storage for responses by the service providers.
9. No queuing at this point.
10. No security mechanism implemented for the client (login). 

# Solution 
The App focuses on back-end and it has a RESTful API to call the service (Java).
Reasoning behind your technical choices, including architectural. Trade-offs you might have made, anything you left out, or what you might do differently if you were to spend additional time on the project.

# Design
As the App requires to handle clients that send emails, the kind of clients is open and we need an email service provider to handle this, so I choose a client-server architecture and synchronous behavior between the client (improve to a queue design?), controller (REST API) and service infrastructure. 

The service infrastructure (instance per thread) calls the third party APIs (email services Amazon SES and SendGrid). The list of third party services contains each service provider as a singleton (a pool of each provider in the future?) and this list is injected (Google Guice) on each service infrastructure instance. Also the service infrastructure implements a basic fail-over strategy (If service fails then it tries with the next one in a list).  

I left out due time constraints: security (OAuth2? datastore security?), attachments on emails, more coverage, include more service providers, simple UI, a more sophisticated fail-over algorithm, datastore for emails, and a queuing component though.

Parts of the code to get the clients for Amazon SES and SendGrid are based on the basic examples provided by Amazon and SendGrid.

# Link to public profile:

www.linkedin.com/in/cervantesivan

https://drive.google.com/file/d/0B-9ucw-8TsfzZGZUWnlLSTMtOUk/view?usp=sharing

# Link to to the hosted application

Jelastic

http://icervantes.whelastic.net/

curl -H "Content-Type: application/json" -X POST -d '{"from":"lord.icervantes@gmail.com","to":"ubancemail@gmail.com","subject":"Test uber email","body":"Listening Led Zeppelin"}' http://icervantes.whelastic.net/services/sendEmail
