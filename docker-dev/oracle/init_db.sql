ALTER
SESSION
SET CONTAINER = FREEPDB1;
      CREATE
TABLESPACE APP DATAFILE 'app.dbf'
             SIZE 10m;

            ALTER
USER APP quota unlimited on APP:
                  CREATE
USER APP_APP identified by pass default tablespace APP;

                         ALTER
USER APP_APP quota unlimited on APP;
GRANT CONNECT, RESOURCE, DBA TO APP;
GRANT
UNLIMITED
TABLESPACE TO APP_APP;
                GRANT UNLIMITED
TABLESPACE TO APP;