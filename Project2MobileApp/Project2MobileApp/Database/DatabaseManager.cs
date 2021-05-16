using System;
using Npgsql;

namespace Project2MobileApp.Database
{
    public class DatabaseManager
    {
        private const string _server = "ec2-54-228-9-90.eu-west-1.compute.amazonaws.com";
        private const string _port = "5432";
        private const string _database = "db3opa9o4csml6";
        private const string _user = "dczjntmaijrisv";
        private const string _password = "04d47a49ea1ae6e98887d7df0a74bd67d21a410c979ebd4e45570e018eb1d6b3";

        private NpgsqlConnection _conn;

        public DatabaseManager()
        {
            string connString = $"Host={_server}:{_port};Username={_user};Password={_password};Database={_database}";
            _conn = new NpgsqlConnection(connString);
        }

    }
}
