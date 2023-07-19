# roles

Roles is a microservice API to manage a users role in a team where roles can be developers, tester, product owner, scrum master an so on.

To understand about process and architecture, there are this two diagrams:

- [Process flow](docs/PROCESS_FLOW_DIAGRAMS.md)
- [C4 Model](docs/C4_DIAGRAMS.md)

The project use Clean Architecture separating use cases in `core`, Rest API in `entrypoint` and database access in `dataprovider`.

The unit tests cover Rest API, database access and all components used in this project. 

## How to run locally

Run the follow command on folder `/local-env/`.

```shell
docker-compose up
```

When the application is available, the Swaager will be accessible on http://localhost:8080/swagger-ui/index.html.
It is possible to access via Postman, importing the files in folder `/docs/postman`.

## Suggestion

The microservices taams and users have the same features, ans each feature in theirs domains. Consequently they have the same things to improve.

Here, there is all topics to improve:
- Use of http status code using 404 instead of 200 when the resource requested does not exists on database.
- Pagination to let the request light.
- Hateaos to specify to frontend about related resources, and page infos and next/previous page if pagination is implemented.
- Route version to scale the API and show that the API will evolve according to the new features and improvements.
- Swagger or Postman as a API document to help developers who support or who consume the routes.

