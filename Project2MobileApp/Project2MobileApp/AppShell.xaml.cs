using Project2MobileApp.ViewModels;
using Project2MobileApp.Views;
using System;
using System.Collections.Generic;
using Xamarin.Forms;

namespace Project2MobileApp
{
    public partial class AppShell : Xamarin.Forms.Shell
    {
        public AppShell()
        {
            InitializeComponent();
            Routing.RegisterRoute(nameof(ItemDetailPage), typeof(ItemDetailPage));
            Routing.RegisterRoute(nameof(NewItemPage), typeof(NewItemPage));
        }

    }
}
