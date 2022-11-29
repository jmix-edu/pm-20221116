### LDAP example

#### How to configure OpenLDAP server.

1. Setup docker and docker compose
2. Use `docker-compose.yml` to start OpenLDAP service
3. Download Apache Directory Studio: https://directory.apache.org/studio/
4. Start OpenLDAP container
5. Setup new connection to ldap in Apache Directory Studio
    -  Note, that we need connection with authentication.  
       Default **username**: "cn=admin,dc=example,dc=org"  
       Default **password**: "admin"
6. Import LDAP users and groups from `ldap/ldap-configuration.ldif`.
    - Update existing entires on import 
    - Users have the same username and password.
   

#### How to configure KeyCloak

1. Setup docker and docker compose
2. Use `docker-compose.yml` from **keycloak** folder.
3. Keycloak is available on `localhost:8180`
   Helpful links:
    * https://hub.docker.com/r/jboss/keycloak#!
    * https://github.com/keycloak/keycloak-containers
