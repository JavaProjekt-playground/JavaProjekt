using System;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;
using Npgsql;

namespace Project2MobileApp.Database
{
    public class DatabaseManager
    {
        private const string _server = "ec2-54-228-9-90.eu-west-1.compute.amazonaws.com";
        private const int _port = 5432;
        private const string _database = "db3opa9o4csml6";
        private const string _user = "dczjntmaijrisv";
        private const string _password = "04d47a49ea1ae6e98887d7df0a74bd67d21a410c979ebd4e45570e018eb1d6b3";

        private readonly NpgsqlConnection _conn;

        private readonly SemaphoreSlim semaphore;

        private int requestCount;

        public DatabaseManager()
        {
            var sBuilder = new NpgsqlConnectionStringBuilder
            {
                Host = _server,
                Database = _database,
                Port = _port,
                Username = _user,
                Password = _password,
                TrustServerCertificate = true,
                SslMode = SslMode.Require
            };

            string connString = sBuilder.ConnectionString;
            Debug.WriteLine(connString);
            _conn = new NpgsqlConnection(connString);
            semaphore = new SemaphoreSlim(1, 1);
            requestCount = 0;
        }

        private async Task OpenConn()
        {
            requestCount++;
            await semaphore.WaitAsync();
            if(_conn.State == System.Data.ConnectionState.Closed) await _conn.OpenAsync();
        }

        private async Task CloseConn()
        {
            requestCount--;
            if (requestCount <= 0 && _conn.State == System.Data.ConnectionState.Open) await _conn.CloseAsync();
            semaphore.Release();
        }

        public async Task<InfoBundle> GetInfoBundle()
        {
            var res = new InfoBundle()
            {
                IsSet = false
            };

            await OpenConn();

            string sql = "SELECT * FROM get_info_bundle();";

            using(var com = new NpgsqlCommand(sql, _conn))
            {
                var r = await com.ExecuteReaderAsync();

                if(await r.ReadAsync())
                {
                    res.IsSet = true;
                    res.UserCount = r.GetInt32(0);
                    res.PlayfieldCount = r.GetInt32(1);
                    res.ImageCount = r.GetInt32(2);
                    res.MaxScore = r.GetDouble(3);
                    res.MinScore = r.GetDouble(4);
                    res.AvgScore = r.GetDouble(5);
                }
            }

            await CloseConn();

            return res;
        }

    }
}
