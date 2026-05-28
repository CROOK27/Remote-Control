Если автоматически бд не создались при иницилизаций контейнера, открыть cmd и выполнить команды.
docker exec -it youtest-postgres bash
psql -U postgres -f docker-entrypoint-initdb.dinit-multiple-dbs.sh
exit