# README #

### This is to demonstrate table copy operation in background using VoltDB's loopback exporter ###

### How do I get set up? ###

* Create Eclipse project using the source if you wish.
* Compile the CopyTables stored procedure.
* Create a jar from the compiled class file and name it classes.jar.
* Load DDL.SQL into the database.
* To Test, enter the command 'exec CopyTables 0' into sqlcmd.
* If you want to kill the test, run 'update flags set value = 1;'
