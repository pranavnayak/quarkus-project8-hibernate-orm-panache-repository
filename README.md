# quarkus-project8-hibernate-orm-panache-repository
 A simple MicroService application to show how to use PanacheRepository in Quarkus using PostgreSQL using the repository pattern, wich is different from the previous project numbered 7 which uses active record pattern in the manner we access the entities. In the project we will create a Movie Entity, a CRUD REST Endpoint to get, create and delete a Movie using the Hibernate-ORM PanacheRepository.

When using the repository pattern, you can define your entities as regular JPA entities, by just annotating them with @Entity but defining the public getters/setters.
If you don’t want to bother defining getters/setters for your entities, you can make them extend PanacheEntityBase and Quarkus will generate them for you. You can even extend PanacheEntity and take advantage of the default ID it provides.

Then when using Repositories, you get the exact same convenient methods as with the active record pattern, injected in your Repository, by making them implements PanacheRepository.

All the operations that are defined on PanacheEntityBase are available on your repository, so using it is exactly the same as using the active record pattern, except you need to inject it via @Inject in your resource.
