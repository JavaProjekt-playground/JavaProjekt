﻿using Project2MobileApp.Views;
using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using Project2MobileApp.Database;

namespace Project2MobileApp
{
    public partial class App : Application
    {
        public static DatabaseManager DB;

        public App()
        {
            InitializeComponent();

            DB = new DatabaseManager();

            MainPage = new MainPage();
        }

        protected override void OnStart()
        {
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
